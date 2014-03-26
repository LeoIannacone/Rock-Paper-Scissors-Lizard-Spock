package it.unibo.games.rpsls.connector.initiator;

import it.unibo.games.rpsls.connector.Config;
import it.unibo.games.rpsls.utils.Validate;

import java.io.ByteArrayOutputStream;
import java.io.InputStream;

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
	
	protected static String host;
	protected static int port;
	
	// set true to clear SIB
	protected static boolean clearSIB = false;
			
	
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
		
		if ( (args[0].equals("-i")) || (args[0].equals("--init")) ){
			clearSIB = false;
			if (args.length == 1){
				host = Config.SIB_HOST;
				port = Config.SIB_PORT;
				
				initiate();
			}
			else{
				if (args[1].contains(":")){
					
					try{
						host = Validate.ip(args[1].split(":")[0]);
					} catch (Exception e) {
						System.out.println("Invalid ip address\n");
						usage();
						System.exit(1);
					}
					
					try{
						port = Validate.port(args[1].split(":")[1]);
					} catch (Exception e) {
						System.out.println("Invalid port\n");
						usage();
						System.exit(1);
					}
					
					initiate();
				}
				else{
					System.out.println("Invalid argument! Check syntax host:port\n");
					usage();
					System.exit(1);
				}
			}
		}
		else if ( (args[0].equals("-c")) || (args[0].equals("--clear")) ){
			clearSIB = true;
			if (args.length == 1){
				host = Config.SIB_HOST;
				port = Config.SIB_PORT;
				
				initiate();
			}
			else{
				if (args[1].contains(":")){
					try{
						host = Validate.ip(args[1].split(":")[0]);
					} catch (Exception e) {
						System.out.println("Invalid ip address\n");
						usage();
						System.exit(1);
					}
					
					try{
						port = Validate.port(args[1].split(":")[1]);
					} catch (Exception e) {
						System.out.println("Invalid port\n");
						usage();
						System.exit(1);
					}
					
					initiate();
				}
				else{
					System.out.println("Invalid argument! Check syntax host:port\n");
					usage();
					System.exit(1);
				}
			}
		}
		else{
			System.out.println("Invalid arguments!\n");
			usage();
		}
	}
	
	private void usage(){
		System.out.println("RPSLS-Initiator help: ");
		System.out.println("\t -h\tprint this menu");
		System.out.println("\t -i, --init\t[hots-address:host-port] initialize SIB with RPSLS ontology");
		System.out.println("\t -c, --clear\t[hots-address:host-port] clear and initialize SIB with RPSLS ontology");
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
		
		System.out.println("Connecting to " + Config.SIB_NAME + " @ " + host + ":" + port);
		this.kp = new KPICore(host, port, Config.SIB_NAME);
		this.kp.join();
		
		if(clearSIB){
			System.out.println("Clearing SIB");
			this.kp.remove(null, null, null, "uri", "uri");
		}
		
		ByteArrayOutputStream baos = new ByteArrayOutputStream();
		
		model.write(baos);
		
		System.out.println("Writing " + this.ontologyFile + "to SIB");
		this.kp.insert_rdf_xml(baos.toString());
		
		System.out.println("SIB initialized correctly");
		System.exit(0);
	}

}
