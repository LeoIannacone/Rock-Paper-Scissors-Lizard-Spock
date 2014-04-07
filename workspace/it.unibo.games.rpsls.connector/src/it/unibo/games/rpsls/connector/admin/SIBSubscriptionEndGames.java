package it.unibo.games.rpsls.connector.admin;

import java.util.ArrayList;
import java.util.List;
import java.util.Vector;

import sofia_kp.KPICore;
import sofia_kp.SSAP_sparql_response;
import it.unibo.games.rpsls.connector.Config;
import it.unibo.games.rpsls.connector.SIBFactory;
import it.unibo.games.rpsls.connector.SIBSubscription;
import it.unibo.games.rpsls.connector.Utils;
import it.unibo.games.rpsls.game.Game;
import it.unibo.games.rpsls.interfaces.IGame;
import it.unibo.games.rpsls.interfaces.admin.IAdminObserver;
import it.unibo.games.rpsls.utils.Debug;

public class SIBSubscriptionEndGames extends SIBSubscription {
	protected List<IGame> waitingGames = null;
	protected IAdminObserver observer;

	protected final static String SUBSCRIPTION_QUERY= "SELECT ?game WHERE "+
			"{ ?game <http://www.w3.org/1999/02/22-rdf-syntax-ns#type> " +
			"<http://rpsls.games.unibo.it/Ontology.owl#GameSession> . " +
			"?game <http://rpsls.games.unibo.it/Ontology.owl#HasStatus> " +
			"<http://rpsls.games.unibo.it/Ontology.owl#"+Game.ENDED+">}";


	public SIBSubscriptionEndGames(IAdminObserver observer){
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
				Debug.print(2, this.getClass().getCanonicalName() + ": getNewObjectFromResults: New game is ended: " + uri);
				observer.updateGameEnded(SIBFactory.getInstance().getGame(uri));
			}
			else{
				System.out.println("New game is ended: " + uri);
			}
		}
	}
	
	@Override
	public void removeObsoleteObject(SSAP_sparql_response resp){
		Vector<String[]> values = resp.getResultsForVar("game");
		for (String[] val : values){
			String uri = Utils.removePrefix(SSAP_sparql_response.getCellValue(val));
			if (observer != null){
				Debug.print(2, this.getClass().getCanonicalName() +  ":removeObsoleteObject: Game " + uri + "is not longer in SIB");
				observer.deleteGameEnded(SIBFactory.getInstance().getGame(uri));
			}
			else{
				System.out.println("Game " + uri + "is not longer in SIB");
			}
		}
	}

}
