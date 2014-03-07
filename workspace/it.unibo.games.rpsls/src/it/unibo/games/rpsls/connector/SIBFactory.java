package it.unibo.games.rpsls.connector;

import java.util.Vector;

import sofia_kp.KPICore;
import sofia_kp.SSAP_XMLTools;
import it.unibo.games.rpsls.game.Player;
import it.unibo.games.rpsls.interfaces.*;

public class SIBFactory {
	
	protected static SIBFactory instance;
	protected SIBConnector SIBC;
	protected KPICore  kp;
	protected SSAP_XMLTools xml_tools;
	
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
		String xml = kp.queryRDF(SIBConnector.NAME_SPACE + PlayerURI, SIBConnector.NAME_SPACE + "hasName", null, "uri", "literal");
		ack = xml_tools.isQueryConfirmed(xml);
		if(!ack)
			System.out.println ("Error during RDF-M3 query");
		else
		{
			triples = xml_tools.getQueryTriple(xml);
			for(Vector<String> v : triples) {
					p = new Player(v.get(2));
					p.setURI(v.get(0).split("#")[1]);
			}
			
		}
		return p;	
	}
}
