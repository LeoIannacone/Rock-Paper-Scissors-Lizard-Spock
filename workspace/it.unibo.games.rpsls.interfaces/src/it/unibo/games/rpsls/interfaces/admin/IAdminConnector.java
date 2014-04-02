package it.unibo.games.rpsls.interfaces.admin;

import it.unibo.games.rpsls.interfaces.IGame;

import java.util.List;

public interface IAdminConnector {
	
	// singleton
	public IAdminConnector getInstance();
	
	// inject ontology
	public void init();
	
	// remove everything but ontology
	public void clean();
	
	// remove everything
	public void reset();

	// games admin
	public List<IGame> getAllGames();
	public List<IGame> getAllGamesByStatus(String status);
	public void deleteGames(List<IGame> games);
	public boolean deleteGame(IGame game);
	
	// subscriptions
	public void watchForEndingGames(IAdminObserver observer);
	public void unwatchForEndingGames();
	
}
