package it.unibo.games.rpsls.tests.sib;

import it.unibo.games.rpsls.connector.Config;
import sofia_kp.KPICore;
import sofia_kp.SSAP_XMLTools;
import sofia_kp.SSAP_sparql_response;
import sofia_kp.iKPIC_subscribeHandler;

public class SubscriptionTest implements iKPIC_subscribeHandler{
	
	private static KPICore kp;
	private static SSAP_XMLTools xml_tools;
	
	
	public static void main(String [] args){
		String xml = "";
		
		kp = new KPICore(Config.SIB_HOST, Config.SIB_PORT, Config.SIB_NAME);
		
		xml = kp.subscribeSPARQL("Select ?a ?b ?c where { ?a ?b ?c }");// SPARQL subscription to all triples
		String subID = null;		
		if(xml_tools.isSubscriptionConfirmed(xml))
		{
			try
			{
				subID = xml_tools.getSubscriptionID(xml);
			}
			catch(Exception e)
			{

			}
		}
		else
		{
			System.out.println ("Error during subscription");
		}
		SSAP_sparql_response resp = xml_tools.get_SPARQL_query_results(xml);//An object to manage the sparql response

		System.out.println(resp.print_as_string());//the representation of variables and corresponding values in human readable format
		
	}


	@Override
	public void kpic_SIBEventHandler(String xml_received) {
		final String xml = xml_received;
		new Thread(
				new Runnable() {
					public void run() {
						System.out.println("added new triple");
					}
				}
		);
		
	}

}
