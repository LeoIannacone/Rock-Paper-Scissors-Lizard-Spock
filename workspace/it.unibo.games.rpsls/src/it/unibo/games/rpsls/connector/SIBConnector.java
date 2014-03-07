package it.unibo.games.rpsls.connector;

import java.util.List;
import java.util.Vector;

import sofia_kp.KPICore;
import sofia_kp.SSAP_XMLTools;
import sofia_kp.iKPIC_subscribeHandler;

import it.unibo.games.rpsls.game.Game;
import it.unibo.games.rpsls.interfaces.IConnector;
import it.unibo.games.rpsls.interfaces.IGame;
import it.unibo.games.rpsls.interfaces.IHit;
import it.unibo.games.rpsls.interfaces.IPlayer;

public class SIBConnector implements IConnector, iKPIC_subscribeHandler {

	private static SIBConnector instance;
	
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
		kp = new KPICore(Config.SIB_HOST, Config.SIB_PORT, Config.SIB_NAME);
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
	}

	@Override
	public void disconnect() {
		xml = kp.leave();
		ack = xml_tools.isLeaveConfirmed(xml);
		if(!ack)
		{
			System.err.println ("Error during LEAVE");
		}   
	}

	@Override
	public boolean createNewGame(IGame game) {

		Vector<Vector<String>> triples = new Vector<Vector<String>>();
		
		Vector<String> v;
		String uri = game.getURIToString();
		
		v = xml_tools.newTriple(NAME_SPACE + uri, RDF + "type", NAME_SPACE + "GameSession", "URI", "URI");
		triples.add(v);
		
		if(game.getHomePlayer() != null){
			v = xml_tools.newTriple(NAME_SPACE + uri, NAME_SPACE + "HasHome", NAME_SPACE + game.getHomePlayer().getURIToString(), "URI", "URI");
			triples.add(v);
		}
		
		if(game.getGuestPlayer() != null){
			v = xml_tools.newTriple(NAME_SPACE + uri, NAME_SPACE + "HasGuest", NAME_SPACE + game.getGuestPlayer().getURIToString(), "URI", "URI");
			triples.add(v);
		}
		
		v = xml_tools.newTriple(NAME_SPACE + uri, NAME_SPACE + "hasScore", game.getScore(), "URI", "literal");
		triples.add(v);
		
		v = xml_tools.newTriple(NAME_SPACE + uri, NAME_SPACE + "HasStatus", NAME_SPACE + game.getStatus(), "URI", "URI");
		triples.add(v);
		
		
		xml = kp.insert(triples);
		
		ack = xml_tools.isInsertConfirmed(xml);
		if(!ack)
		{
			System.err.println ("Error Inserting new Game in the SIB");
		}
		
		return ack;
	}

	@Override
	public List<IGame> getWaitingGames() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public boolean joinGame(IGame game, IPlayer player) {
		boolean ack;
		String xml = "";
		ack = changeGameStatus(game, Game.ACTIVE);
		if (ack) xml = kp.insert(NAME_SPACE + game.getURIToString(), NAME_SPACE + "HasGuest", NAME_SPACE + player.getURIToString(), "URI", "URI");
		ack = ack && xml_tools.isInsertConfirmed(xml);
		if (ack){
			game.setGuestPlayer(player);
			ack = updateGameScore(game);
		}
		return ack;
	}

	@Override
	public boolean leaveGame(IGame game, IPlayer player) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean endGame(IGame game) {
		return changeGameStatus(game, Game.ENDED);
	}

	@Override
	public boolean deleteGame(IGame game) {
		String xml = kp.remove(NAME_SPACE + game.getURIToString(), null, null, "URI", "URI");
		return xml_tools.isRemoveConfirmed(xml);
	}

	@Override
	public boolean updateGameStatus(IGame game, String status) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public String getGameStatus(IGame game) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public boolean createNewPlayer(IPlayer player) {
		
		Vector<Vector<String>> triples = new Vector<Vector<String>>();
		
		Vector<String> v;
		String uri = player.getURIToString();
		
		v = xml_tools.newTriple(NAME_SPACE + uri, RDF + "type", NAME_SPACE + "Person", "URI", "URI");
		triples.add(v);
		
		v = xml_tools.newTriple(NAME_SPACE + uri, NAME_SPACE + "hasName", player.getName(), "URI", "literal");
		triples.add(v);
		
		xml = kp.insert(triples);
		
		ack = xml_tools.isInsertConfirmed(xml);
		if(!ack)
		{
			System.err.println ("Error Inserting new Player in the SIB");
		}
		
		return ack;
	}

	@Override
	public boolean sendHit(IGame game, IPlayer player, IHit hit) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public IHit getHit(IGame game, IPlayer player) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public IPlayer getIncomingPlayer(IGame game) {
		// TODO Auto-generated method stub
		return null;
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
	
	public boolean changeGameStatus(IGame game, String status){
		boolean ack;
		String xml = kp.remove(NAME_SPACE + game.getURIToString(), NAME_SPACE + "HasStatus", null, "URI", "URI");
		ack = xml_tools.isRemoveConfirmed(xml);
		if(ack){
			xml = kp.insert(NAME_SPACE + game.getURIToString(), NAME_SPACE + "HasStatus", NAME_SPACE + status, "URI", "URI");
			ack = xml_tools.isInsertConfirmed(xml);
			game.setStatus(status);
		}
		return ack;
	}

	@Override
	public boolean updateGameScore(IGame game) {
		boolean ack;
		String xml = kp.remove(NAME_SPACE + game.getURIToString(), NAME_SPACE + "hasScore", null, "URI", "literal");
		ack = xml_tools.isRemoveConfirmed(xml);
		if(ack){
			xml = kp.insert(NAME_SPACE + game.getURIToString(), NAME_SPACE + "hasScore", game.getScore(), "URI", "literal");
			ack = xml_tools.isInsertConfirmed(xml);
		}
		return ack;
	}
}
