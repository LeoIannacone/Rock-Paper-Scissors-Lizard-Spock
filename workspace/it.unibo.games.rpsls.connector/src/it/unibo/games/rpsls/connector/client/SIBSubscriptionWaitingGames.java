package it.unibo.games.rpsls.connector.client;

import it.unibo.games.rpsls.connector.Config;
import it.unibo.games.rpsls.connector.SIBFactory;
import it.unibo.games.rpsls.connector.SIBSubscription;
import it.unibo.games.rpsls.connector.Utils;
import it.unibo.games.rpsls.interfaces.IGame;
import it.unibo.games.rpsls.interfaces.client.IObserver;
import it.unibo.games.rpsls.utils.Debug;

import java.util.ArrayList;
import java.util.List;
import java.util.Vector;

import sofia_kp.KPICore;
import sofia_kp.SSAP_sparql_response;

public class SIBSubscriptionWaitingGames extends SIBSubscription {

	protected List<IGame> waitingGames = null;
	protected IObserver observer;

	protected final static String SUBSCRIPTION_QUERY= "SELECT ?game WHERE "+
			"{ ?game <http://www.w3.org/1999/02/22-rdf-syntax-ns#type> " +
			"<http://rpsls.games.unibo.it/Ontology.owl#GameSession> . " +
			"?game <http://rpsls.games.unibo.it/Ontology.owl#HasStatus> " +
			"<http://rpsls.games.unibo.it/Ontology.owl#waiting>}";


	public SIBSubscriptionWaitingGames(IObserver observer){
		String xml = "";
		this.observer = observer;
		kp = new KPICore(Config.SIB_HOST, Config.SIB_PORT, Config.SIB_NAME);
		kp.join();
		xml = kp.subscribeSPARQL(SUBSCRIPTION_QUERY, this);
		subID = null;
		if(xml_tools.isSubscriptionConfirmed(xml))
		{
			try{
				subID = xml_tools.getSubscriptionID(xml);
			}
			catch(Exception e){ e.printStackTrace(); }
		}
		else{
			System.err.println ("Error during subscription");
		}	
		Debug.print(2, this.getClass().getCanonicalName() + ": Subscription confirmed to query\n    " + SUBSCRIPTION_QUERY);
		SSAP_sparql_response resp = xml_tools.get_SPARQL_query_results(xml);//An object to manage the sparql response
		getNewObjectsFromResults(resp);
	}

	@Override
	public void getNewObjectsFromResults(SSAP_sparql_response resp) {
		waitingGames = new ArrayList<IGame>();
		Vector<String[]> values = resp.getResultsForVar("game");
		for (String[] val : values){
			String uri = Utils.removePrefix(SSAP_sparql_response.getCellValue(val));
			if (observer != null){
				Debug.print(2, this.getClass().getCanonicalName() + ": getNewObjectFromResults: New game is waiting for incoming player: " + uri);
				observer.updateWaitingGames(SIBFactory.getInstance().getGame(uri));
			}
			else{
				IGame g = SIBFactory.getInstance().getGame(uri);
				System.out.println("New game is waiting for incoming player: " + uri);
				System.out.println("  " + g.toString());
			}
		}
	}
	
	@Override
	public void removeObsoleteObject(SSAP_sparql_response resp){
		Vector<String[]> values = resp.getResultsForVar("game");
		for (String[] val : values){
			String uri = Utils.removePrefix(SSAP_sparql_response.getCellValue(val));
			if (observer != null){
				Debug.print(2, this.getClass().getCanonicalName() +  ":removeObsoleteObject: Game " + uri + "is not longer waiting");
				observer.updateWaitingGames(SIBFactory.getInstance().getGame(uri));
			}
			else{
				IGame g = SIBFactory.getInstance().getGame(uri);
				System.out.println("Game " + uri + "is not longer waiting");
				System.out.println("  " + g.toString());
			}
		}
	}
}

