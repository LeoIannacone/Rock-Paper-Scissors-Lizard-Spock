package it.unibo.games.rpsls.connector.initiator;

import it.unibo.games.rpsls.connector.Config;
import it.unibo.games.rpsls.utils.Validate;

import java.io.BufferedReader;
import java.io.ByteArrayOutputStream;
import java.io.InputStream;
import java.io.InputStreamReader;

import com.hp.hpl.jena.ontology.OntModel;
import com.hp.hpl.jena.rdf.model.ModelFactory;



import sofia_kp.KPICore;
import sofia_kp.SSAP_XMLTools;

public class SIBInitiator {
	
	protected String ontologyFile = "resources/GameOntology.owl";
	protected KPICore kp = null;
	protected SSAP_XMLTools ssap = null;
	protected String xml = null;
	protected boolean ack = false;
	
	// set true to reset SIB with RPSLS Ontology
	protected boolean loadOWL = false;
	// set true to remove RPSLS Ontology from SIB
	protected boolean removeOWL = false;
	// set true to delete all triples from SIB
	protected boolean clearSIB = false;
			
	
	public static void main(String[] argv)
	{
		new SIBInitiator(argv);
	}

	public SIBInitiator(String[] argv){
		parseArgs(argv);
	}

	private void parseArgs(String[] args) {
		//default: help
		if ( (args.length < 1) || (args[0].equals("-h")) || (args[0].equals("--help")) ) {
			usage();
			System.exit(0);
		}
		
		else if (args.length == 2){
			if (args[1].contains(":")){
				
				try{
					Config.SIB_HOST = Validate.ip(args[1].split(":")[0]);
				} catch (Exception e) {
					System.err.println("Invalid ip address\n");
					usage();
					System.exit(1);
				}
				
				try{
					Config.SIB_PORT = Validate.port(args[1].split(":")[1]);
				} catch (Exception e) {
					System.err.println("Invalid port\n");
					usage();
					System.exit(1);
				}
			}
			else
				System.err.println("Invalid arguments! Check syntax host:port");
		}

		if ( (args[0].equals("-i")) || (args[0].equals("--init")) )
			loadOWL = true;
		if ( (args[0].equals("-r")) || (args[0].equals("--reset"))){
			loadOWL = true;
			removeOWL = true;
		}
			
		if ( (args[0].equals("-rm")) || (args[0].equals("--remove")))
			removeOWL = true;
		
		//toggle comment to enable/disable clean SIB
//		if ( (args[0].equals("-C")) || (args[0].equals("--clear")) )
//			clearSIB = true;
		initiate();
	}
	
	private void usage(){
		System.out.println("RPSLS-Initiator help: ");
		System.out.println("\t -h\tprint this menu");
		System.out.println("\t -i, --init\t[hots-address:host-port]\tinitialize SIB with RPSLS ontology");
		System.out.println("\t -r, --reset\t[hots-address:host-port]\treinitialize SIB with RPSLS ontology");
		System.out.println("\t -rm, --remove\t[hots-address:host-port]\tremove all RPSLS triples from SIB");
//		System.out.println("\t -C, --clean\t[hots-address:host-port]\tremove all triples from SIB");
	}
	
	private void initiate(){
		System.out.println("Initializig SIB");
		OntModel model = ModelFactory.createOntologyModel();
		InputStream in = this.getClass().getResourceAsStream(this.ontologyFile);
		if (in == null) {
			throw new IllegalArgumentException(
				"File: " + this.ontologyFile + " not found"
			);
		}
		
		// read the RDF/XML file
		System.out.println("Reading " + this.ontologyFile);
		model.read(in, null);
		
		System.out.println("Connecting to " + Config.SIB_NAME + " @ " + Config.SIB_HOST + ":" + Config.SIB_PORT);
		this.kp = new KPICore(Config.SIB_HOST, Config.SIB_PORT, Config.SIB_NAME);
		this.kp.join();
		

		if(removeOWL){
			if(confirm()){
				System.out.println("Removing RPSLS triples from SIB");
				this.kp.update_sparql("DELETE { ?a ?b ?c } WHERE { ?a ?b ?c FILTER regex ( str( ?a ) , \"^http://rpsls.games.unibo.it/Ontology.owl\" ) }");
				System.out.println("RPSLS triples removed correctly");
			}
		}
		if(loadOWL){
			ByteArrayOutputStream baos = new ByteArrayOutputStream();
			model.write(baos);
				System.out.println("Writing " + this.ontologyFile + "to SIB");
			this.kp.insert_rdf_xml(baos.toString());
			System.out.println("RPSLS ontology wrote correctly");
		}
		if(clearSIB){
			if(confirm()){
				System.out.println("Removing all triples from SIB");
				this.kp.remove(null, null, null, "uri", "uri");
				System.out.println("SIB clear");
			}
		}
		System.exit(0);
	}

	private boolean confirm() {

		System.out.println("Removing all RPSLS triples from SIB. Are you sure? [yes|no] : ");
		 
		try{
		    BufferedReader bufferRead = new BufferedReader(new InputStreamReader(System.in));
		    String s = bufferRead.readLine();
		    if (s.toLowerCase().equals("yes") || s.toLowerCase().equals("y"))
		    	return true;
		    else
		    	return false;
		} catch (Exception e){
			e.printStackTrace();
			return false;
		}
	}
}
