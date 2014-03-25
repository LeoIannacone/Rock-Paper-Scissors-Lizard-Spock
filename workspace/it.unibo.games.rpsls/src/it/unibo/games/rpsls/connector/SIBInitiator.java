package it.unibo.games.rpsls.connector;

import java.io.ByteArrayOutputStream;
import java.io.InputStream;

import com.hp.hpl.jena.ontology.OntModel;
import com.hp.hpl.jena.rdf.model.ModelFactory;
import com.hp.hpl.jena.util.FileManager;



import sofia_kp.KPICore;
import sofia_kp.SSAP_XMLTools;

public class SIBInitiator {
	
	protected String ontologyFile= "resources/GameOntology.owl";
	protected KPICore kp = null;
	protected SSAP_XMLTools ssap = null;
	protected String xml = null;
	protected boolean ack = false;
	
	
	// set true to clear SIB
	protected boolean clearSIB = true;
	
	public static void main(String[] argv)
	{
		System.out.println("Initializig SIB");
		SIBInitiator initiator = new SIBInitiator();
		OntModel model = ModelFactory.createOntologyModel();
		InputStream in = FileManager.get().open( initiator.ontologyFile );
		if (in == null) {
			throw new IllegalArgumentException(
				"File: " + initiator.ontologyFile + " not found"
			);
		}
		
		// read the RDF/XML file
		System.out.println("Reading " + initiator.ontologyFile);
		model.read(in, null);
		
		System.out.println("Connecting to " + Config.SIB_NAME + " @ " + Config.SIB_HOST + ":" + Config.SIB_PORT);
		initiator.kp = new KPICore(Config.SIB_HOST, Config.SIB_PORT, Config.SIB_NAME);
		initiator.kp.join();
		
		if(initiator.clearSIB){
			System.out.println("Clearing SIB");
			initiator.kp.remove(null, null, null, "uri", "uri");
		}
		
		ByteArrayOutputStream baos = new ByteArrayOutputStream();
		
		model.write(baos);
		
		System.out.println("Writing " + initiator.ontologyFile + "to SIB");
		initiator.kp.insert_rdf_xml(baos.toString());
		
		System.out.println("SIB initialized correctly");
	}

}
