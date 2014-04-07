package it.unibo.games.rpsls.connector;

import java.util.Vector;

import sofia_kp.KPICore;
import sofia_kp.SSAP_XMLTools;
import it.unibo.games.rpsls.connector.client.SIBCommandInterface;
import it.unibo.games.rpsls.connector.client.SIBClient;
import it.unibo.games.rpsls.game.Game;
import it.unibo.games.rpsls.game.Hit;
import it.unibo.games.rpsls.game.Player;
import it.unibo.games.rpsls.interfaces.*;
import it.unibo.games.rpsls.utils.Debug;

public class SIBFactory {
	
	protected static SIBFactory instance;
	protected SIBClient SIBC;
	protected KPICore  kp;
	protected SSAP_XMLTools xml_tools;
	
	public static SIBFactory getInstance(){
		if (instance == null)
			instance = new SIBFactory();
		return instance;
	}
	
	private SIBFactory(){
		Debug.print(2, this.getClass().getCanonicalName() + ":Connecting to " + Config.SIB_NAME + " @ " + Config.SIB_HOST + ":" + Config.SIB_PORT);
		kp = new KPICore(Config.SIB_HOST, Config.SIB_PORT, Config.SIB_NAME);
		kp.join();
		Debug.print(2, this.getClass().getCanonicalName() + ":Connected");
		xml_tools = new SSAP_XMLTools();
	}
	
	public IPlayer getPlayer(String PlayerURI){
		boolean ack;
		Player p = null;
		Vector<Vector<String>> triples;
		String xml = kp.queryRDF(Config.NAME_SPACE + PlayerURI, Config.NAME_SPACE + "hasName", null, "uri", "literal");
		ack = xml_tools.isQueryConfirmed(xml);
		if(!ack)
			System.err.println("Error during RDF-M3 query");
		else
		{
			triples = xml_tools.getQueryTriple(xml);
			for(Vector<String> v : triples) {
					p = new Player(v.get(2));
					p.setURI(Utils.removePrefix(v.get(0)));
			}
			return p;
		}
		return null;	
	}
	
	public IGame getGame(String GameURI){
		boolean ack;
		Game g = new Game(null, null);
		Vector<Vector<String>> triples;
		String xml = kp.queryRDF(Config.NAME_SPACE + GameURI, null , null, "uri", "uri");
		ack = xml_tools.isQueryConfirmed(xml);
		if(!ack)
			System.err.println ("Error during RDF-M3 query");
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
					ISimpleEntity commandInterface= new SIBCommandInterface();
					commandInterface.setURI(value);
					g.setCommandInterface(commandInterface);
				}
			}
			g.setScore(score);
			return g;
		}
		return null;
	}
	
	public ICommand getHit (String CommandURI) {
		boolean ack;
		ICommand c = new Hit("");
		Vector<Vector<String>> triples;
		String xml = kp.queryRDF(Config.NAME_SPACE + CommandURI, null, null, "uri", "uri");
		ack = xml_tools.isQueryConfirmed(xml);
		if(!ack)
			System.err.println ("Error during RDF-M3 query");
		else
		{
			c.setURI(CommandURI);
			triples = xml_tools.getQueryTriple(xml);
			for(Vector<String> v : triples) {
				String what = Utils.removePrefix(v.get(1));
				String value = Utils.removePrefix(v.get(2));
				
				if (what.equals("hasTime"))
					c.setTime(Long.parseLong(value));
				
				else if (what.equals("HasIssuer")) {
					c.setIssuer(SIBFactory.getInstance().getPlayer(value));
				}
				else if (what.equals("HasCommandType")) {
					c.setCommandType(value);
				}
			}
			return c;
		}
		return null;
	}
}
