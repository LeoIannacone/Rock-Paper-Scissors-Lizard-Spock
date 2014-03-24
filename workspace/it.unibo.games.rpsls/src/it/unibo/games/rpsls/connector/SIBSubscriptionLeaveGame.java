package it.unibo.games.rpsls.connector;

import it.unibo.games.rpsls.interfaces.IGame;
import it.unibo.games.rpsls.interfaces.IObserver;
import it.unibo.games.rpsls.utils.Debug;


import sofia_kp.KPICore;
import sofia_kp.SSAP_sparql_response;

public class SIBSubscriptionLeaveGame extends SIBSubscription {

	protected IObserver observer;
	protected IGame game;
	
	protected static String SUBSCRIPTION_QUERY= "SELECT ?uri_game WHERE {  " +
			"%s <http://rpsls.games.unibo.it/Ontology.owl#HasStatus> <http://rpsls.games.unibo.it/Ontology.owl#ended> }";


	public SIBSubscriptionLeaveGame(IGame game, IObserver observer){
		String xml = "";
		this.observer = observer;
		this.game = game;
		kp = new KPICore(Config.SIB_HOST, Config.SIB_PORT, Config.SIB_NAME);
		kp.join();
		xml = kp.subscribeSPARQL(String.format(SUBSCRIPTION_QUERY, game.getURIToString()), this);
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
		if (observer != null){
			Debug.print(2, this.getClass().getCanonicalName() +":getNewObjectFromResults: " + game.getURIToString() + " ended"  );
			observer.udpateGameEnded(game);
		}
		else{
			System.out.println("Game Ended:");
			System.out.println("  " + game.getURIToString());
		}
	}
}