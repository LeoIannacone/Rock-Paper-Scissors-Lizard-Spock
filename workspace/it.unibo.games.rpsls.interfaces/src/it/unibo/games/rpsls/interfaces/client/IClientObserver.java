package it.unibo.games.rpsls.interfaces.client;

import it.unibo.games.rpsls.interfaces.ICommand;
import it.unibo.games.rpsls.interfaces.IGame;

import java.util.List;

public interface IClientObserver {

	public void updateWaitingGames(List<IGame> games);
	public void updateWaitingGames(IGame game);
	
	public void updateHit(ICommand hit);
	
	public void updateIncomingPlayer(IGame game);
	
	public void udpateGameEnded(IGame game);
	
}
