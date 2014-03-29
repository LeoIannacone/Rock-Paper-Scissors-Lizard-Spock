package it.unibo.games.rpsls.admin.interfaces;

import it.unibo.games.rpsls.interfaces.IGame;

import java.util.List;

public interface IAdminObserver {
	
	// from watchForEndingGames
	public void updateGamesEnded(List<IGame> games);
	public void updateGameEnded(IGame games);

}
