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

public class SubscriptionTest implements iKPIC_subscribeHandler{
	
	protected KPICore kp;
	protected SSAP_XMLTools xml_tools = new SSAP_XMLTools();
	
	public SubscriptionTest(){
		
		System.out.println("creating new subscription");
		String xml = "";
	
		kp = new KPICore(Config.SIB_HOST, Config.SIB_PORT, Config.SIB_NAME);
		
		kp.setEventHandler(this);
		
		xml = kp.subscribeSPARQL("SELECT ?a WHERE { ?a <" + SIBConnector.RDF + "type> <" + SIBConnector.NAME_SPACE + "Person> }");// SPARQL subscription to all triples
		
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
		
		kp.setEventHandler(this);
		
		xml = kp.subscribeSPARQL(subscription);// SPARQL subscription to all triples
		
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

		System.out.println(resp.print_as_string());//the representation of variables and corresponding values in human readable format

		System.out.println("Created new subscription with query:\n      " + subscription);
		
	}
	
	
	public static void main(String [] args){
//		new SubscriptionTest();
		new SubscriptionTest(Utils.createSimpleSPARQLQuerySelectWhere(null, SIBConnector.NAME_SPACE + "hasName", null));
	}

	@Override
	public void kpic_SIBEventHandler(String xml_received) {
		String xml2 = xml_received;
		Thread t = new Thread(new ThreadHandler(xml2));
		t.start();
	}

}


class ThreadHandler implements Runnable {

	private String xml; 
	private SSAP_XMLTools xml_tools;
	
	public ThreadHandler(String xml) {
		this.xml = xml;
	}
		
	@Override
	public void run() {
		xml_tools = new SSAP_XMLTools();
		System.out.println("new triple received from subscription: " + xml_tools.getSubscriptionID(xml));
		
		/**
		 * Print the variables of the SPARQL-query subscribed
		 * 			we need to find a way to get this values!!
		 */
		
		SSAP_sparql_response inserted_row = xml_tools.get_SPARQL_indication_new_results(xml);
		SSAP_sparql_response deleted_row = xml_tools.get_SPARQL_indication_obsolete_results(xml);
		if (inserted_row != null)
		{
			
			/**
			 * For each row:
			 * 
			 * 		Vector<Vector<String[0]>> = variable
			 * 		Vector<Vector<String[1]>> = variable type (uri or literal)
			 * 		Vector<Vector<String[2]>> = value
			 */
			
			Vector<Vector<String[]>> results = inserted_row.getResults();
			for (Vector<String[]> result : results){
				for (String [] res : result){
					 if(res[0].equals("subject")){
						String uri = Utils.removePrefix(res[2]);
						IPlayer p = SIBFactory.getInstance().getPlayer(uri);
						System.out.println("name: " + p.getName());
						System.out.println("score: " + p.getScore());
					 }
				}
			}
			
			
		}
		if (deleted_row != null)
		{
			System.out.println("obsolete: \n " + deleted_row.print_as_string());
		}
	}
	
}