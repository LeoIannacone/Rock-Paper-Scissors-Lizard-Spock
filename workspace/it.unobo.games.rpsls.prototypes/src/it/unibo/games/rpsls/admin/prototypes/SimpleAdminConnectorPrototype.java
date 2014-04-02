package it.unibo.games.rpsls.admin.prototypes;
import java.util.ArrayList;
import java.util.List;

import it.unibo.games.rpsls.game.Game;
import it.unibo.games.rpsls.interfaces.IGame;
import it.unibo.games.rpsls.interfaces.admin.IAdminConnector;
import it.unibo.games.rpsls.interfaces.admin.IAdminObserver;

public class SimpleAdminConnectorPrototype implements IAdminConnector {


	protected static SimpleAdminConnectorPrototype instance;
	
	protected List<IGame> fakeGames;
	
	public static IAdminConnector getInstance() {
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
	public boolean deleteGame(IGame game) {
		if (fakeGames.contains(game)) {
			System.out.println("Deleting " + game.getURIToString());
			fakeGames.remove(game);
			return true;
		}
		
		else {
			System.err.println("Game not in list" + game.getURIToString());
			return false;
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
