package it.unibo.games.rpsls.tests.sib;

import java.util.Vector;

import it.unibo.games.rpsls.connector.Config;
import it.unibo.games.rpsls.connector.SIBConnector;
import it.unibo.games.rpsls.connector.SIBFactory;
import it.unibo.games.rpsls.connector.Utils;
import it.unibo.games.rpsls.interfaces.IPlayer;
import sofia_kp.KPICore;
import sofia_kp.SSAP_XMLTools;
import sofia_kp.SSAP_sparql_response;
import sofia_kp.iKPIC_subscribeHandler;
import sofia_kp.iKPIC_subscribeHandler2;

public class SubscriptionTest implements iKPIC_subscribeHandler2 {
	
	protected KPICore kp;
	protected SSAP_XMLTools xml_tools = new SSAP_XMLTools();
	
	public SubscriptionTest(){
		
		System.out.println("creating new subscription");
		String xml = "";
	
		kp = new KPICore(Config.SIB_HOST, Config.SIB_PORT, Config.SIB_NAME);
		
		xml = kp.subscribeSPARQL("SELECT ?a WHERE { ?a <" + SIBConnector.RDF + "type> <" + SIBConnector.NAME_SPACE + "Person> }", this);// SPARQL subscription to all triples
		
		String subID = null;
		
		if(xml_tools.isSubscriptionConfirmed(xml))
		{
			try{
				subID = xml_tools.getSubscriptionID(xml);
				System.out.println("Subscription ID: " + subID);
			}
			catch(Exception e){ e.printStackTrace(); }
		}
		else{
			System.out.println ("Error during subscription");
		}
		
		SSAP_sparql_response resp = xml_tools.get_SPARQL_query_results(xml);//An object to manage the sparql response

		System.out.println(resp.print_as_string());//the representation of variables and corresponding values in human readable format

	}
	
	
	public SubscriptionTest(String subscription){
		String xml = "";
		
		kp = new KPICore(Config.SIB_HOST, Config.SIB_PORT, Config.SIB_NAME);
		kp.join();
		
		xml = kp.subscribeSPARQL(subscription, this);
		
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

		System.out.println("PLAYERS in SIB: \n");
		Vector<Vector<String[]>> results = resp.getResults();
		for (Vector<String[]> result : results){
			for (String [] res : result){
				 if(res[0].equals("subject")){
					String uri = Utils.removePrefix(res[2]);
					IPlayer p = SIBFactory.getInstance().getPlayer(uri);
					System.out.println("name: " + p.getName());
				 }
			}
		}
		
		
//		System.out.println("Created new subscription with query:\n      " + subscription);
		
	}
	
	
	public static void main(String [] args){
		new SubscriptionTest(Utils.createSimpleSPARQLQuerySelectWhere(null, SIBConnector.NAME_SPACE + "hasName", null));
	}


	@Override
	public void kpic_RDFEventHandler(Vector<Vector<String>> newTriples,
			Vector<Vector<String>> oldTriples, String indSequence, String subID) {
		// TODO Auto-generated method stub
		
	}


	@Override
	public void kpic_SPARQLEventHandler(SSAP_sparql_response newResults,
			SSAP_sparql_response oldResults, String indSequence, String subID) {
		Thread t = new Thread(new ThreadHandler(newResults, oldResults, indSequence, subID));
		t.start();
	}


	@Override
	public void kpic_UnsubscribeEventHandler(String sub_ID) {
		// TODO Auto-generated method stub
		
	}


	@Override
	public void kpic_ExceptionEventHandler(Throwable SocketException) {
		// TODO Auto-generated method stub
		
	}

}


class ThreadHandler implements Runnable {

	private String xml; 
	private SSAP_XMLTools xml_tools;
	private SSAP_sparql_response new_results;
	private SSAP_sparql_response old_results;
	private String subID;
	private String indSequence;
	
	
	public ThreadHandler(String xml) {
		this.xml = xml;
		xml_tools = new SSAP_XMLTools();
		new_results = xml_tools.get_SPARQL_indication_new_results(xml);
		old_results = xml_tools.get_SPARQL_indication_obsolete_results(xml);
		subID = xml_tools.getSubscriptionID(xml);
		indSequence = xml_tools.getSSAPmsgIndicationSequence(xml);
	}
	
	public ThreadHandler(SSAP_sparql_response newResults,
			SSAP_sparql_response oldResults, String indSequence, String subID) {
		this.new_results = newResults;
		this.old_results = oldResults;
		this.indSequence = indSequence;
		this.subID = subID;
	}
		
	@Override
	public void run() {
		
		/**
		 * Print the variables of the SPARQL-query subscribed
		 * 			we need to find a way to get this values!!
		 */
		
		if (new_results != null)
		{
			
			/**
			 * For each row:
			 * 
			 * 		Vector<Vector<String[0]>> = variable
			 * 		Vector<Vector<String[1]>> = variable type (uri or literal)
			 * 		Vector<Vector<String[2]>> = value
			 */
			
			/**
			 * different methods for printing informations
			 */
			
			Vector<String[]> values = new_results.getResultsForVar("subject");
			for (String[] val : values){
					System.out.println("subject has value " + SSAP_sparql_response.getCellValue(val));
			}
			
		}
		if (old_results != null)
		{
			System.out.println("obsolete: \n " + old_results.print_as_string());
		}
	}	
}