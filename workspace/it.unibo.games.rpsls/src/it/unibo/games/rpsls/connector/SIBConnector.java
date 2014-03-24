package it.unibo.games.rpsls.connector;

import java.util.Date;
import java.util.Vector;

import sofia_kp.KPICore;
import sofia_kp.SSAP_XMLTools;
import sofia_kp.iKPIC_subscribeHandler;

import it.unibo.games.rpsls.game.Game;
import it.unibo.games.rpsls.interfaces.IConnector;
import it.unibo.games.rpsls.interfaces.IGame;
import it.unibo.games.rpsls.interfaces.ICommand;
import it.unibo.games.rpsls.interfaces.IObserver;
import it.unibo.games.rpsls.interfaces.IPlayer;
import it.unibo.games.rpsls.utils.Debug;

public class SIBConnector implements IConnector, iKPIC_subscribeHandler {

	private static SIBConnector instance;
	private SIBSubscriptionWaitingGames waitingGamesSubscription;
	private SIBSubscriptionWaitingIncomingPlayer incomingPlayerSubscription;
	private SIBSubscriptionHit hitSubscription;
	private SIBSubscriptionLeaveGame leaveSubscription;
	
	public static String RDF = "http://www.w3.org/1999/02/22-rdf-syntax-ns#";
	public static String RDFS = "http://www.w3.org/2000/01/rdf-schema#";
	public static String NAME_SPACE = "http://rpsls.games.unibo.it/Ontology.owl#";
	
	/**
	 * Declaration of SIB
	 */	
	private KPICore kp;  //direct interface with the SIB
	private SSAP_XMLTools xml_tools;  // utility methods to compose messages and manage responses
	private String xml =""; //conventionally used for storing the messages from the SIB
	private boolean ack = false; // Conventionally used for checking SIB response
	
	public static SIBConnector getInstance() {
		if (instance == null)
			instance = new SIBConnector();
		return instance;
	}
	
	private SIBConnector() {
		Debug.print(2, this.getClass().getCanonicalName() + ": SIBConnector: " + "Connecting to " + Config.SIB_NAME + " @ " + Config.SIB_HOST + ":" + Config.SIB_PORT);
		kp = new KPICore(Config.SIB_HOST, Config.SIB_PORT, Config.SIB_NAME);
		Debug.print(2, this.getClass().getCanonicalName() + ": SIBConnector: " + "Connected");
		xml_tools = new SSAP_XMLTools();
	}
	
	@Override
	public void init() {

	}

	@Override
	public void connect() {	
		//Trying to join SIB
		xml = kp.join();
		ack = xml_tools.isJoinConfirmed(xml);
		if (!ack)
			System.err.println("Error: unable to join the SIB");
		else
			Debug.print(2, this.getClass().getCanonicalName() + ": connect: " + "SIB joined");

	}

	@Override
	public void disconnect() {
		xml = kp.leave();
		ack = xml_tools.isLeaveConfirmed(xml);
		if(!ack){
			System.err.println ("Error during LEAVE");
		}   
		else
			Debug.print(2, this.getClass().getCanonicalName() + ": disconnect: " + "SIB leaved");
	}

	@Override
	public boolean createNewGame(IGame game) {
		
		/**
		 * we need to add for each game session identified by URI:
		 * 
		 * 		- an HomePlayer
		 * 		- a GuestPlayer (if not null)
		 * 		- a GameStatus (initially set to WAITING)
		 * 		- a Score (data property)
		 * 		- a CommandInterface (a new URI that we must initialize)
		 * 		- a GameDescription
		 */
		
		Vector<Vector<String>> triples = new Vector<Vector<String>>();
		
		Vector<String> v;
		String uri = game.getURIToString();
		
		//insert in SIB a new URI with type GameSession
		v = xml_tools.newTriple(NAME_SPACE + uri, RDF + "type", NAME_SPACE + "GameSession", "URI", "URI");
		triples.add(v);
		
		//HomePlayer
		if(game.getHomePlayer() != null){
			v = xml_tools.newTriple(NAME_SPACE + uri, NAME_SPACE + "HasHome", NAME_SPACE + game.getHomePlayer().getURIToString(), "URI", "URI");
			triples.add(v);
		}
		
		//GuestPlayer
		if(game.getGuestPlayer() != null){
			v = xml_tools.newTriple(NAME_SPACE + uri, NAME_SPACE + "HasGuest", NAME_SPACE + game.getGuestPlayer().getURIToString(), "URI", "URI");
			triples.add(v);
		}
		
		//Score
		v = xml_tools.newTriple(NAME_SPACE + uri, NAME_SPACE + "hasScore", game.getScore(), "URI", "literal");
		triples.add(v);
		
		//Status
		v = xml_tools.newTriple(NAME_SPACE + uri, NAME_SPACE + "HasStatus", NAME_SPACE + game.getStatus(), "URI", "URI");
		triples.add(v);
		
		//CommandInterface
		if( game.getCommandInterface() == null ){
			game.setCommandInterface(new SIBCommandInterface());
		}
		v = xml_tools.newTriple(NAME_SPACE + uri, NAME_SPACE + "HasCommandInterface", NAME_SPACE + game.getCommandInterface().getURIToString() , "URI", "URI");
		triples.add(v);
		
		// TODO: GameDescription
		
		xml = kp.insert(triples);
		
		ack = xml_tools.isInsertConfirmed(xml);
		if(!ack){
			System.err.println ("Error Inserting new Game in the SIB");
		}
		else
			Debug.print(2, this.getClass().getCanonicalName() + ": createNewGame: " + "Created new game:\n     " + game.toString());
		return ack;
	}

	@Override
	public boolean joinGame(IGame game, IPlayer player) {
		/**
		 * when a player joins a game previously registered  
		 * we need to update:
		 * 		- guest player
		 * 		- status
		 * 		- score (in a game in waiting is null)
		 */
		
		boolean ack;
		String xml = "";
		ack = updateGameStatus(game, Game.ACTIVE);
		if (ack) xml = kp.insert(NAME_SPACE + game.getURIToString(), NAME_SPACE + "HasGuest", NAME_SPACE + player.getURIToString(), "URI", "URI");
		ack = ack && xml_tools.isInsertConfirmed(xml);
		if (ack){
			game.setGuestPlayer(player);
			ack = updateGameScore(game);
			Debug.print(2, this.getClass().getCanonicalName() + ": joinGame: " + player.getURIToString() + " joined " + game.getURIToString());
		}
		else 
			System.err.println("Error joining game");
		return ack;
	}

	@Override
	public boolean leaveGame(IGame game, IPlayer player) {
//		Debug.print(2, player.getURIToString() + " leaved " + game.getURIToString());
		return false;
	}

	@Override
	public boolean endGame(IGame game) {
		
		/**
		 * simply change the game status
		 */
		ack = updateGameStatus(game, Game.ENDED);
		if (ack)
			Debug.print(2, this.getClass().getCanonicalName() + ": endGame: " + game.getURIToString() + " ended");
		else
			System.err.println("Error ending game");
		return ack;
	}

	@Override
	public boolean deleteGame(IGame game) {
		
		/**
		 * remove game from SIB
		 */
		
		String xml = kp.remove(NAME_SPACE + game.getURIToString(), null, null, "URI", "URI");
		if(xml_tools.isRemoveConfirmed(xml))
			Debug.print(2, this.getClass().getCanonicalName() + ": deleteGame: " + game.getURIToString() + " deleted");
		else
			System.err.println("Error removing game");
		return xml_tools.isRemoveConfirmed(xml);
	}

	@Override
	public boolean updateGameStatus(IGame game, String status) {
		
		/**
		 * remove old-status triple, then, insert a triple that
		 * describe the new status
		 */
		
		boolean ack;
		String xml = kp.remove(NAME_SPACE + game.getURIToString(), NAME_SPACE + "HasStatus", null, "URI", "URI");
		ack = xml_tools.isRemoveConfirmed(xml);
		if(ack){
			xml = kp.insert(NAME_SPACE + game.getURIToString(), NAME_SPACE + "HasStatus", NAME_SPACE + status, "URI", "URI");
			ack = xml_tools.isInsertConfirmed(xml);
			if (ack){
				game.setStatus(status);
				Debug.print(2, this.getClass().getCanonicalName() + ": updateGameStatus: " + game.getURIToString() + " has been updated:\n    " + game.toString());
			}
			else
				System.err.println("Error updating game status");
		}
		else
			System.err.println("Error removing old game status");
		return ack;
	}

	@Override
	public String getGameStatus(IGame game) {
		return SIBFactory.getInstance().getGame(game.getURIToString()).getStatus();
	}

	@Override
	public boolean createNewPlayer(IPlayer player) {
		
		/**
		 * we need to add to SIB the name of each player, identified by URI:
		 */
		
		Vector<Vector<String>> triples = new Vector<Vector<String>>();
		
		Vector<String> v;
		String uri = player.getURIToString();
		
		v = xml_tools.newTriple(NAME_SPACE + uri, RDF + "type", NAME_SPACE + "Person", "URI", "URI");
		triples.add(v);
		
		v = xml_tools.newTriple(NAME_SPACE + uri, NAME_SPACE + "hasName", player.getName(), "URI", "literal");
		triples.add(v);
		
		xml = kp.insert(triples);
		
		ack = xml_tools.isInsertConfirmed(xml);
		if(!ack){
			System.err.println ("Error Inserting new Player in the SIB");
		}
		else
			Debug.print(2, this.getClass().getCanonicalName() + ": createNewPlayer: " + "Created " + player.getURIToString() + " with name: " + player.getName());
		return ack;
	}

	@Override
	public boolean sendHit(IGame game, IPlayer player, ICommand hit) {
		
		/**
		 * we need to add for each hit identified by URI, with type COMMAND:
		 * 
		 * 		- the type of command (Rock, Paper, Scissors, Lizard, Spock)
		 * 		- the issuers (player that hit the command)
		 * 		- the command interface (connected to the game session)
		 * 		- the time of hit
		 */
		
		Vector<Vector<String>> triples = new Vector<Vector<String>>();
		
		Vector<String> v;
		String uri = hit.getURIToString();
		
		v = xml_tools.newTriple(NAME_SPACE + uri, RDF + "type", NAME_SPACE + "Command", "URI", "URI");
		triples.add(v);
		
		v = xml_tools.newTriple(NAME_SPACE + uri, NAME_SPACE + "HasCommandType", NAME_SPACE + hit.getCommandType(), "URI", "URI");
		triples.add(v);
		
		v = xml_tools.newTriple(NAME_SPACE + uri, NAME_SPACE + "HasIssuer", NAME_SPACE + player.getURIToString(), "URI", "URI");
		triples.add(v);
		
		v = xml_tools.newTriple(NAME_SPACE + game.getCommandInterface().getURIToString(), NAME_SPACE + "HasCommand", NAME_SPACE + uri , "URI", "URI");
		triples.add(v);
		
		v = xml_tools.newTriple(NAME_SPACE + uri, NAME_SPACE + "hasTime", "" + new Date().getTime(), "URI", "literal");
		triples.add(v);
		
		xml = kp.insert(triples);
		
		ack = xml_tools.isInsertConfirmed(xml);
		if(!ack){
			System.err.println ("Error Inserting new Hit in the SIB");
		}
		else
			Debug.print(2, this.getClass().getCanonicalName() + ": sendHit: " + player.getName() + " has played " + hit.getCommandType() + ": " + hit.getURIToString());
		return ack;
	}


	public KPICore getKP(){
		return kp;
	}
	
	public SSAP_XMLTools getXMLTools(){
		return xml_tools;
	}

	@Override
	public void kpic_SIBEventHandler(String xml) {
		
		
	}
	
	@Override
	public boolean updateGameScore(IGame game) {
		
		/**
		 * remove old-score triple, then, insert a triple that
		 * describe the new score
		 */
		
		boolean ack;
		String xml = kp.remove(NAME_SPACE + game.getURIToString(), NAME_SPACE + "hasScore", null, "URI", "literal");
		ack = xml_tools.isRemoveConfirmed(xml);
		if(ack){
			xml = kp.insert(NAME_SPACE + game.getURIToString(), NAME_SPACE + "hasScore", game.getScore(), "URI", "literal");
			ack = xml_tools.isInsertConfirmed(xml);
			Debug.print(2, this.getClass().getCanonicalName() + ": updateGameScore: " + "The score in " + game.getURIToString() + " is now " + game.getScore());
		}
		else{
			System.err.println("Error updatig score");
		}
		return ack;
	}
	
	@Override
	public void watchForWaitingGames(IObserver observer) {
		waitingGamesSubscription = new SIBSubscriptionWaitingGames(observer);
	}

	@Override
	public void unwatchForWaitingGames() {
		if (waitingGamesSubscription != null && waitingGamesSubscription.getSubID() != null){
			waitingGamesSubscription.kpic_UnsubscribeEventHandler(waitingGamesSubscription.getSubID());
		}
	}

	@Override
	public void watchForIncomingPlayer(IGame game, IObserver observer) {
		incomingPlayerSubscription = new SIBSubscriptionWaitingIncomingPlayer(game, observer);
	}

	@Override
	public void unwatchForIncomingPlayer() {
		if (incomingPlayerSubscription != null && incomingPlayerSubscription.getSubID() != null){
			incomingPlayerSubscription.kpic_UnsubscribeEventHandler(incomingPlayerSubscription.getSubID());
		}		
	}

	@Override
	public void watchForHit(IGame game, IPlayer player, IObserver observer) {
		hitSubscription = new SIBSubscriptionHit(game, observer);
	}

	@Override
	public void unwatchForHit() {
		if (hitSubscription != null && hitSubscription.getSubID() != null)
			hitSubscription.kpic_UnsubscribeEventHandler(hitSubscription.getSubID());
	}

	@Override
	public void watchForGameEnded(IGame game, IObserver observer) {
		leaveSubscription = new SIBSubscriptionLeaveGame(game, observer);
	}

	@Override
	public void unwatchForGameEnded() {
		if (leaveSubscription != null && leaveSubscription.getSubID() != null)
			leaveSubscription.kpic_UnsubscribeEventHandler(leaveSubscription.getSubID());
	}

}
