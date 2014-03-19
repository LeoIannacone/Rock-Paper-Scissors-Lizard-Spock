package it.unibo.games.rpsls.connector;

import it.unibo.games.rpsls.gui.ViewJoinGame;
import it.unibo.games.rpsls.interfaces.IGame;

import java.util.ArrayList;
import java.util.List;
import java.util.Vector;

import sofia_kp.KPICore;
import sofia_kp.SSAP_sparql_response;

public class SIBSubscriptionWaitingGames extends SIBSubscription {

	protected List<IGame> waitingGames = null;
	protected ViewJoinGame viewJoinGame;

	protected final static String SUBSCRIPTION_QUERY= "SELECT ?game WHERE "+
			"{ ?game <http://www.w3.org/1999/02/22-rdf-syntax-ns#type> " +
			"<http://rpsls.games.unibo.it/Ontology.owl#GameSession> . " +
			"?game <http://rpsls.games.unibo.it/Ontology.owl#HasStatus> " +
			"<http://rpsls.games.unibo.it/Ontology.owl#waiting>}";


	public SIBSubscriptionWaitingGames(ViewJoinGame view){
		String xml = "";
		viewJoinGame = view;
		kp = new KPICore(Config.SIB_HOST, Config.SIB_PORT, Config.SIB_NAME);
		kp.join();
		xml = kp.subscribeSPARQL(SUBSCRIPTION_QUERY, this);
		String subID = null;
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
		waitingGames = new ArrayList<IGame>();
		Vector<String[]> values = resp.getResultsForVar("game");
		for (String[] val : values){
			String uri = Utils.removePrefix(SSAP_sparql_response.getCellValue(val));
			if (viewJoinGame != null){
				System.out.println("new waiting game: " + SIBFactory.getInstance().getGame(uri).getHomePlayer().toString());

				viewJoinGame.appendWaitingGames(SIBFactory.getInstance().getGame(uri));
			}
			else{
				IGame g = SIBFactory.getInstance().getGame(uri);
				System.out.println("Waiting games:");
				System.out.println("  " + g.toString());
				System.out.println("  " + g.getHomePlayer().toString());
			}
		}
	}
}

