package it.unibo.games.rpsls.connector.admin;

import java.io.ByteArrayOutputStream;
import java.io.InputStream;
import java.util.List;

import com.hp.hpl.jena.ontology.OntModel;
import com.hp.hpl.jena.rdf.model.ModelFactory;

import sofia_kp.KPICore;
import sofia_kp.SSAP_XMLTools;

import it.unibo.games.rpsls.connector.Config;
import it.unibo.games.rpsls.interfaces.IGame;
import it.unibo.games.rpsls.interfaces.admin.IAdminConnector;
import it.unibo.games.rpsls.interfaces.admin.IAdminObserver;
import it.unibo.games.rpsls.utils.Debug;

public class SIBAdmin implements IAdminConnector {
	
	protected SIBAdmin instance;
	protected KPICore kp;
	protected String ontologyFile = "resources/GameOntology.owl";
	protected SIBSubscriptionEndGames subscriptionEndGames;
	private SSAP_XMLTools xml_tools;  // utility methods to compose messages and manage responses
	private String xml =""; //conventionally used for storing the messages from the SIB
	private boolean ack = false; // Conventionally used for checking SIB response

	
	@Override
	public IAdminConnector getInstance() {
		if (instance == null)
			instance = new SIBAdmin();
		return instance;
	}
	
	private SIBAdmin() {
		this.kp = new KPICore(Config.SIB_HOST, Config.SIB_PORT, Config.SIB_NAME);
		this.kp.join();
		Debug.print(2, this.getClass().getCanonicalName()+":costructor: SIB connected");
	}
	

	@Override
	public void init() {
		Debug.print(2, this.getClass().getCanonicalName() + ":init: injecting owl");
		OntModel model = ModelFactory.createOntologyModel();
		InputStream in = this.getClass().getResourceAsStream(this.ontologyFile);
		if (in == null) {
			throw new IllegalArgumentException(
				"File: " + this.ontologyFile + " not found"
			);
		}
		model.read(in, null);
		ByteArrayOutputStream baos = new ByteArrayOutputStream();
		model.write(baos);
		this.kp.insert_rdf_xml(baos.toString());
	}

	@Override
	public void clean() {
		Debug.print(2, this.getClass().getCanonicalName() + ":clean: cleaning SIB");
		this.kp.update_sparql("DELETE { ?a ?b ?c } WHERE { ?a ?b ?c FILTER regex ( str( ?a ) , \"^http://rpsls.games.unibo.it/Ontology.owl\" ) }");
	}

	@Override
	public void reset() {
		Debug.print(2, this.getClass().getCanonicalName() + ":reset: resetting SIB");
		this.kp.remove(null, null, null, "uri", "uri");
	}

	@Override
	public List<IGame> getAllGames() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<IGame> getAllGamesByStatus(String status) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void deleteGames(List<IGame> games) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public boolean deleteGame(IGame game) {
		String DELETE_GAMESESSION ="";
		DELETE_GAMESESSION = "DELETE { " +
				Config.NAME_SPACE + "RPSLS <http://rpsls.games.unibo.it/Ontology.owl#HasGameSession> <" +Config.NAME_SPACE + game.getURIToString() + "> . " +
				"<" +Config.NAME_SPACE + game.getURIToString() + "> ?prop_game ?val_game . " +
				"<" +Config.NAME_SPACE + game.getURIToString() + "> <http://rpsls.games.unibo.it/Ontology.owl#HasCommandInterface> ?cmd_interface . " +
				"?cmd_interface <http://rpsls.games.unibo.it/Ontology.owl#HasCommand> ?cmd . " +
				"?cmd ?prop_cmd ?val_cmd " +
				"} WHERE { " +
				"?interactive_game <http://rpsls.games.unibo.it/Ontology.owl#HasGameSession> <" +Config.NAME_SPACE + game.getURIToString() + "> . " +
				"<" +Config.NAME_SPACE + game.getURIToString() + "> ?prop_game ?val_game . " +
				"<" +Config.NAME_SPACE + game.getURIToString() + "> <http://rpsls.games.unibo.it/Ontology.owl#HasCommandInterface> ?cmd_interface . " +
				"?cmd_interface <http://rpsls.games.unibo.it/Ontology.owl#HasCommand> ?cmd . " +
				"?cmd ?prop_cmd ?val_cmd " +
				"}";
		xml = kp.update_sparql(DELETE_GAMESESSION);
		ack = xml_tools.isUpdateConfirmed(xml);
		if (ack)
			Debug.print(2, this.getClass().getCanonicalName() + ": endGame: " +  game.getURIToString() + " ended");
		else{
			System.out.println("This is an API Error!:");
			System.err.println("Error ending game");
		}
		return ack;
	}

	@Override
	public void watchForEndingGames(IAdminObserver observer) {
		subscriptionEndGames = new SIBSubscriptionEndGames(observer);
	}

	@Override
	public void unwatchForEndingGames() {
		if (subscriptionEndGames != null && subscriptionEndGames.getSubID() != null)
			subscriptionEndGames.kpic_UnsubscribeEventHandler(subscriptionEndGames.getSubID());
	}
	
}
