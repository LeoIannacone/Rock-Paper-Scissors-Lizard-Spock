package it.unibo.games.rpsls.admin.prototypes;
import java.util.ArrayList;
import java.util.List;

import sofia_kp.KPICore;

import it.unibo.games.rpsls.admin.connector.SIBAdmin;
import it.unibo.games.rpsls.admin.interfaces.IAdminConnector;
import it.unibo.games.rpsls.admin.interfaces.IAdminObserver;
import it.unibo.games.rpsls.client.connector.Config;
import it.unibo.games.rpsls.game.Game;
import it.unibo.games.rpsls.interfaces.IGame;
import it.unibo.games.rpsls.utils.Debug;


public class SimpleAdminConnectorPrototype implements IAdminConnector {


	protected SimpleAdminConnectorPrototype instance;
	
	protected List<IGame> fakeGames;
	
	@Override
	public IAdminConnector getInstance() {
		if (instance == null)
			instance = new SimpleAdminConnectorPrototype();
		return instance;
	}
	
	private SimpleAdminConnectorPrototype() {
		System.out.println("Connecting to SIB");
		
		fakeGames = new ArrayList<>();
		for (int i = 0; i < 15 ; i++) {
			Game g = new Game(null, null);
			g.setStatus(Game.ENDED);
			fakeGames.add(g);
		}
	}
	
	@Override
	public void init() {
		System.out.println("Initializing database ....");
	}

	@Override
	public void clean() {
		System.out.println("Removing everything but OWL");
	}

	@Override
	public void reset() {
		System.out.println("Removing everything");
	}

	@Override
	public List<IGame> getAllGames() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<IGame> getAllGamesByStatus(String status) {
		return fakeGames;
	}

	@Override
	public void deleteGames(List<IGame> games) {
		for (IGame g : games)
			deleteGame(g);
	}

	@Override
	public void deleteGame(IGame game) {
		if (fakeGames.contains(game)) {
			System.out.println("Deleting " + game.getURIToString());
			fakeGames.remove(game);
		}
		
		else {
			System.err.println("Game not in list" + game.getURIToString());
		}
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
