package it.unibo.games.rpsls.connector;

import it.unibo.games.rpsls.interfaces.IGame;
import it.unibo.games.rpsls.interfaces.IObserver;
import it.unibo.games.rpsls.interfaces.IPlayer;

import java.util.Vector;

import sofia_kp.KPICore;
import sofia_kp.SSAP_sparql_response;

public class SIBSubscriptionWaitingIncomingPlayer extends SIBSubscription {
	protected IObserver observer;
	protected String uriGame="";
	protected IGame game;

	protected static String SUBSCRIPTION_QUERY= "SELECT ?uri_player WHERE { <http://rpsls.games.unibo.it/Ontology.owl#%s> " +
			"<http://rpsls.games.unibo.it/Ontology.owl#HasGuest> ?uri_player }";


	public SIBSubscriptionWaitingIncomingPlayer(IGame game, IObserver observer){
		String xml = "";
		this.observer = observer;
		
		this.game = game;
		uriGame = this.game.getURIToString();
		
		kp = new KPICore(Config.SIB_HOST, Config.SIB_PORT, Config.SIB_NAME);
		kp.join();
		
		xml = kp.subscribeSPARQL(String.format(SUBSCRIPTION_QUERY, uriGame), this);
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
		Vector<String[]> values = resp.getResultsForVar("uri_player");
		IPlayer p = null;
		for (String[] val : values){
			String uri = Utils.removePrefix(SSAP_sparql_response.getCellValue(val));
			if (observer != null){
				p = SIBFactory.getInstance().getPlayer(uri);
				game.setGuestPlayer(p);
				observer.updateIncomingPlayer(game);
			}
			else{
				p = SIBFactory.getInstance().getPlayer(uri);
				game.setGuestPlayer(p);
				System.out.println("Player joined:");
				System.out.println("  " + p.toString());
			}
			
			System.out.println(game.getGuestPlayer().getName() + " is joined");
		}
	}
}
