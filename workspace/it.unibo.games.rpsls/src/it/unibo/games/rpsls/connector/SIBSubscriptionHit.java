package it.unibo.games.rpsls.connector;

import it.unibo.games.rpsls.game.Hit;
import it.unibo.games.rpsls.interfaces.IGame;
import it.unibo.games.rpsls.interfaces.IObserver;

import java.util.Vector;

import sofia_kp.KPICore;
import sofia_kp.SSAP_sparql_response;

public class SIBSubscriptionHit extends SIBSubscription {

	protected IObserver observer;

	protected static String SUBSCRIPTION_QUERY= "SELECT ?type WHERE { " +
			"<http://rpsls.games.unibo.it/Ontology.owl#%s> <http://rpsls.games.unibo.it/Ontology.owl#HasCommand> ?uri_command . " +
			"?uri_command <http://rpsls.games.unibo.it/Ontology.owl#HasIssuer> <http://rpsls.games.unibo.it/Ontology.owl#%s> . " +
			"?uri_command <http://rpsls.games.unibo.it/Ontology.owl#HasCommandType> ?type }";


	public SIBSubscriptionHit(IGame game, IObserver observer){
		String xml = "";
		this.observer = observer;
		kp = new KPICore(Config.SIB_HOST, Config.SIB_PORT, Config.SIB_NAME);
		kp.join();
		System.out.println(String.format(SUBSCRIPTION_QUERY, game.getCommandInterface().getURIToString(), game.getOpponent().getURIToString()));
		xml = kp.subscribeSPARQL(String.format(SUBSCRIPTION_QUERY, game.getCommandInterface(), game.getOpponent()), this);
		subID = null;
		if(xml_tools.isSubscriptionConfirmed(xml))
		{
			try{
				subID = xml_tools.getSubscriptionID(xml);
			}
			catch(Exception e){ e.printStackTrace(); }
		}
		else{
			System.out.println ("Error during subscription");
		}	
		SSAP_sparql_response resp = xml_tools.get_SPARQL_query_results(xml);//An object to manage the sparql response
		getNewObjectsFromResults(resp);
	}

	@Override
	public void getNewObjectsFromResults(SSAP_sparql_response resp) {
		Vector<String[]> values = resp.getResultsForVar("type");
		for (String[] val : values){
			String type = Utils.removePrefix(SSAP_sparql_response.getCellValue(val));
			if (observer != null)
				observer.updateHit(new Hit(type));
			else{
				Hit c = new Hit(type);
				System.out.println("Hit received:");
				System.out.println("  " + c.getCommandType());
			}
		}
	}
}

