package it.unibo.games.rpsls.connector;

import it.unibo.games.rpsls.game.Game;
import it.unibo.games.rpsls.interfaces.IGame;
import it.unibo.games.rpsls.interfaces.IObserver;
import it.unibo.games.rpsls.utils.Debug;

import java.util.Vector;


import sofia_kp.KPICore;
import sofia_kp.SSAP_sparql_response;

public class SIBSubscriptionLeaveGame extends SIBSubscription {

	protected IObserver observer;
	protected IGame game;
	
	protected static String SUBSCRIPTION_QUERY= "SELECT ?status WHERE {  " +
			"<http://rpsls.games.unibo.it/Ontology.owl#%s> <http://rpsls.games.unibo.it/Ontology.owl#HasStatus> ?status }";


	public SIBSubscriptionLeaveGame(IGame game, IObserver observer){
		String xml = "";
		this.observer = observer;
		this.game = game;
		kp = new KPICore(Config.SIB_HOST, Config.SIB_PORT, Config.SIB_NAME);
		kp.join();
		xml = kp.subscribeSPARQL(String.format(SUBSCRIPTION_QUERY, game.getURIToString()), this);
		Debug.print(2, String.format(SUBSCRIPTION_QUERY, game.getURIToString()));
		subID = null;
		if(xml_tools.isSubscriptionConfirmed(xml)){
			try{
				subID = xml_tools.getSubscriptionID(xml);
				Debug.print(2, this.getClass().getCanonicalName() + "Subscription confirmed with ID: " + subID);
				Debug.print(2, this.getClass().getCanonicalName() + "\n    SPARQLquery = " + String.format(SUBSCRIPTION_QUERY, game.getURIToString()));
			}
			catch(Exception e){ e.printStackTrace(); }
		}
		else{
			System.err.println ("Error during subscription");
		}	
		SSAP_sparql_response resp = xml_tools.get_SPARQL_query_results(xml);//An object to manage the sparql response
		getNewObjectsFromResults(resp);
	}

	@Override
	public void getNewObjectsFromResults(SSAP_sparql_response resp) {
		Vector<String[]> values = resp.getResultsForVar("status");
		Debug.print(2, this.getClass().getCanonicalName() + ":getNewObjectFromResults: Received " + values.size() + " new values");
		int counter = 0;
		for (String[] val : values){
			String status = Utils.removePrefix(SSAP_sparql_response.getCellValue(val));
			Debug.print(2, this.getClass().getCanonicalName() + ":getNewObjectFromResults: value " + counter++);
			if (observer != null){
				if(Utils.removePrefix(status).equals(Game.ENDED)){
					Debug.print(2, this.getClass().getCanonicalName() +":getNewObjectFromResults: " + game.getURIToString() + ": ended");
					observer.udpateGameEnded(game);
				}
			}
			else{
				System.out.println("Game ended:");
				System.out.println(game.getURIToString() + ": ended");
			}
		}
	}
}

