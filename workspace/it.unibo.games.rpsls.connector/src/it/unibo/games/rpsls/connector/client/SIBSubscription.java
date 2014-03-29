package it.unibo.games.rpsls.connector.client;


import it.unibo.games.rpsls.connector.Config;
import it.unibo.games.rpsls.utils.Debug;

import java.util.Vector;

import sofia_kp.KPICore;
import sofia_kp.SSAP_XMLTools;
import sofia_kp.SSAP_sparql_response;
import sofia_kp.iKPIC_subscribeHandler2;

public class SIBSubscription implements iKPIC_subscribeHandler2 {
	
	protected KPICore kp;
	protected SSAP_XMLTools xml_tools = new SSAP_XMLTools();
	protected SSAP_sparql_response resp;
	protected String subID;
	
	public SIBSubscription(){
		
	}
	
	public SIBSubscription(String query){
		String xml = "";
		kp = new KPICore(Config.SIB_HOST, Config.SIB_PORT, Config.SIB_NAME);
		kp.join();
		xml = kp.subscribeSPARQL(query, this);
		subID = null;
		if(xml_tools.isSubscriptionConfirmed(xml))
		{
			try{
				subID = xml_tools.getSubscriptionID(xml);
			}
			catch(Exception e){ e.printStackTrace(); }
		}
		else{
			System.err.println("Error during subscription");
		}	
		Debug.print(2, this.getClass().getCanonicalName() + ": Subscription confirmed to query\n    " + query);
		SSAP_sparql_response resp = xml_tools.get_SPARQL_query_results(xml);//An object to manage the sparql response
		getNewObjectsFromResults(resp);
	}

	public void kpic_SPARQLEventHandler(SSAP_sparql_response newResults,
			SSAP_sparql_response oldResults, String indSequence, String subID) {
		Thread t = new Thread(new ThreadHandler(newResults, oldResults, indSequence, subID));
		t.start();
		try {
			t.join();
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	public void getNewObjectsFromResults(SSAP_sparql_response resp) {
		// here we only print, override for each subclasses with management of object
		//remember:

		resp.print_as_string();
	}

	
	
	@Override
	public void kpic_RDFEventHandler(Vector<Vector<String>> newTriples,
			Vector<Vector<String>> oldTriples, String indSequence, String subID) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void kpic_UnsubscribeEventHandler(String sub_ID) {
		if(sub_ID != null){
			kp.unsubscribe(sub_ID);
			Debug.print(2, this.getClass().getCanonicalName() + ": unsubscribing: " +sub_ID);
			this.subID = null;
		}
	}

	@Override
	public void kpic_ExceptionEventHandler(Throwable SocketException) {
		// TODO Auto-generated method stub
		
	}
	
	class ThreadHandler implements Runnable {

		String xml; 
		SSAP_XMLTools xml_tools;
		SSAP_sparql_response new_results;
		SSAP_sparql_response old_results;
		String subID;
		String indSequence;
		
		
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
			 */
			
			if (new_results != null){
				getNewObjectsFromResults(new_results);
			}
			if (old_results != null){
				removeObsoleteObject(old_results);
			}
		}
	}
	
	public String getSubID(){
		return subID;
	}

	public void removeObsoleteObject(SSAP_sparql_response resp) {
		resp.print_as_string();
		
	}
}
