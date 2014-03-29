package it.unibo.games.rpsls.admin.connector;

import java.io.ByteArrayOutputStream;
import java.io.InputStream;
import java.util.List;

import com.hp.hpl.jena.ontology.OntModel;
import com.hp.hpl.jena.rdf.model.ModelFactory;

import sofia_kp.KPICore;

import it.unibo.games.rpsls.admin.interfaces.IAdminConnector;
import it.unibo.games.rpsls.admin.interfaces.IAdminObserver;
import it.unibo.games.rpsls.client.connector.Config;
import it.unibo.games.rpsls.interfaces.IGame;
import it.unibo.games.rpsls.utils.Debug;

public class SIBAdmin implements IAdminConnector {
	
	protected SIBAdmin instance;
	protected KPICore kp;
	protected String ontologyFile = "resources/GameOntology.owl";
	
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
	public void deleteGame(IGame game) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void watchForEndingGames(IAdminObserver observer) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void unwatchForEndingGames() {
		// TODO Auto-generated method stub
		
	}
	
}
