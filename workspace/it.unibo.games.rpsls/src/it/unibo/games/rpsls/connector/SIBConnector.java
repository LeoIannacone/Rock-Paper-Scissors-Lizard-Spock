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
	
	/**
	 * Declaration of SIB
	 */	
	public KPICore kp;  //direct interface with the SIB
	public SSAP_XMLTools xml_tools;  // utility methods to compose messages and manage responses
	public String xml =""; //conventionally used for storing the messages from the SIB
	public boolean ack = false; // Conventionally used for checking SIB response
	
	/**
	 * Declaration of SIB constants to be opportunely modified statically or at run-time in order to interact with the SIB
	 */
	public String SIB_HOST = "127.0.0.1";
	public int SIB_PORT = 10010;
	public String SIB_NAME = "X";
	public String SIB_PREFIX = "http://smarmM3Lab/Ontology.owl#";
	
	/**
	 * Declaration of vector of strings for triples useful for the SIB interaction
	 */
	Vector<Vector<String>> triples = new Vector<Vector<String>>();
	Vector<Vector<String>> triples_ins = new Vector<Vector<String>>();  //Structure that can be useful in many programs
	Vector<Vector<String>> triples_rem = new Vector<Vector<String>>();  //Structure that can be useful in many programs
	Vector<String> triple = new Vector<String>();//Structure that can be useful in many programs
	
	
	public static IConnector getInstance() {
		if (instance == null)
			instance = new SIBConnector();
		return instance;
	}
	
	private SIBConnector() {
		
	}
	
	@Override
	public void init() {

	}

	@Override
	public void connect() {
		//Definition of connection
		kp = new KPICore(SIB_HOST, SIB_PORT, SIB_NAME);
		xml_tools = new SSAP_XMLTools();
		//Trying to join SIB
		xml = kp.join();
		ack = xml_tools.isJoinConfirmed(xml);
		if (!ack)
			System.err.println("Error: unable to join the SIB");
		else
			System.out.println("SIB joined correctly");
	}

	@Override
	public void disconnect() {
		// TODO Auto-generated method stub
		
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
		// TODO Auto-generated method stub
		return false;
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
