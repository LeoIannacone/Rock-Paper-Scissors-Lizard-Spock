package it.unibo.games.rpsls.connector;

import java.util.List;
import java.util.UUID;
import java.util.Vector;

import sofia_kp.KPICore;
import sofia_kp.SSAP_XMLTools;
import sun.security.ssl.krb5.Krb5ProxyImpl;

import it.unibo.games.rpsls.interfaces.IConnector;
import it.unibo.games.rpsls.interfaces.IGame;
import it.unibo.games.rpsls.interfaces.IHit;
import it.unibo.games.rpsls.interfaces.IPlayer;

public class SIBConnector implements IConnector {

	private static IConnector instance;
	
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
	
	/**
	 * Declaration of SIB constants to be opportunely modified statically or at run-time in order to interact with the SIB
	 */
	private String SIB_HOST = "127.0.0.1";
	private int SIB_PORT = 10010;
	private String SIB_NAME = "X";
	
	public static IConnector getInstance() {
		if (instance == null)
			instance = new SIBConnector();
		return instance;
	}
	
	private SIBConnector() {
		kp = new KPICore(SIB_HOST, SIB_PORT, SIB_NAME);
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
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public List<IGame> getWaitingGames() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public boolean joinGame(IGame game, IPlayer player) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean leaveGame(IGame game, IPlayer player) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean endGame(IGame game) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean deleteGame(IGame game) {
		// TODO Auto-generated method stub
		return false;
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

}
