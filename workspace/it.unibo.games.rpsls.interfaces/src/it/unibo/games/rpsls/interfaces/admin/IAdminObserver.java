package it.unibo.games.rpsls.interfaces.admin;

import it.unibo.games.rpsls.interfaces.IGame;

import java.util.List;

public interface IAdminObserver {
	
	// from watchForEndingGames
	public void updateGamesEnded(List<IGame> games);
	public void updateGameEnded(IGame game);
	public void deleteGamesEnded(List<IGame> games);
	public void deleteGameEnded(IGame game);

}
