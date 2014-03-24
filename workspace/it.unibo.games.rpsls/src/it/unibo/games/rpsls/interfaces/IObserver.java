package it.unibo.games.rpsls.interfaces;

import java.util.List;

public interface IObserver {

	public void updateWaitingGames(List<IGame> games);
	public void updateWaitingGames(IGame game);
	
	public void updateHit(ICommand hit);
	
	public void updateIncomingPlayer(IGame game);
	
	public void udpateGameEnded(IGame game);
	
}
