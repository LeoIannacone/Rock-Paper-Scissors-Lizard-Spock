package it.unibo.games.rpsls.connector;

import java.util.Vector;

import sofia_kp.KPICore;
import sofia_kp.SSAP_XMLTools;
import it.unibo.games.rpsls.game.Game;
import it.unibo.games.rpsls.game.Player;
import it.unibo.games.rpsls.interfaces.*;

public class SIBFactory {
	
	protected static SIBFactory instance;
	protected SIBConnector SIBC;
	protected KPICore  kp;
	protected SSAP_XMLTools xml_tools;
	
	/**
	 * 		WORKAROUND: SSAP_XMLTools.ANYURI returns a string that contains "null"
	 */
	protected static String ANYURI ="http://www.nokia.com/NRC/M3/sib#any";
	
	public static SIBFactory getInstance(){
		if (instance == null)
			instance = new SIBFactory();
		return instance;
	}
	
	private SIBFactory(){
		SIBC = SIBConnector.getInstance();
		kp = SIBC.getKP();
		xml_tools = SIBC.getXMLTools();
	}
	
	public IPlayer getPlayer(String PlayerURI){
		boolean ack;
		Player p = null;
		Vector<Vector<String>> triples;
		String xml = kp.queryRDF(SIBConnector.NAME_SPACE + PlayerURI, SIBConnector.NAME_SPACE + "hasName", ANYURI, "uri", "literal");
		ack = xml_tools.isQueryConfirmed(xml);
		if(!ack)
			System.out.println ("Error during RDF-M3 query");
		else
		{
			triples = xml_tools.getQueryTriple(xml);
			for(Vector<String> v : triples) {
					p = new Player(v.get(2));
					p.setURI(Utils.removePrefix(v.get(0)));
			}
			
		}
		return p;	
	}
	
	public IGame getGame(String GameURI){
		boolean ack;
		Game g = new Game(null, null);
		Vector<Vector<String>> triples;
		String xml = kp.queryRDF(SIBConnector.NAME_SPACE + GameURI, ANYURI , ANYURI, "uri", "uri");
		ack = xml_tools.isQueryConfirmed(xml);
		if(!ack)
			System.out.println ("Error during RDF-M3 query");
		else
		{
			g.setURI(GameURI);
			String score = ""; // workaround if players are not yed defined
			triples = xml_tools.getQueryTriple(xml);
			for(Vector<String> v : triples) {
				String what = Utils.removePrefix(v.get(1));
				String value = Utils.removePrefix(v.get(2));
				if (what.equals("HasStatus")) {
					g.setStatus(value);
				}
				else if (what.equals("hasScore")){
					score = value;
				}
				else if (what.equals("HasHome")) {
					IPlayer home = getPlayer(value);
					g.setHomePlayer(home);
				}
				else if (what.equals("HasGuest")) {
					IPlayer guest = getPlayer(value);
					g.setGuestPlayer(guest);
				}
				else if (what.equals("HasCommandInterface")){
					IConnectorEntity commandInterface= new SIBCommandInterface();
					commandInterface.setURI(value);
					g.setCommandInterface(commandInterface);
				}
			}
			g.setScore(score);
		}
		return g;
	}

}
