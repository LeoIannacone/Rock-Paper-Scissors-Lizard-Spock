package it.unibo.games.rpsls.interfaces;

import java.util.List;

public interface IConnector {
	
	// Will be a Singleton
	
	// Generic
	public void connect();
	public void disconnect();
	public void init();
	
	// Games
	public boolean createNewGame(IGame game);
	public List<IGame> getWaitingGames();
	public boolean joinGame(IGame game, IPlayer player);
	public boolean leaveGame(IGame game, IPlayer player);
	public boolean endGame(IGame game);
	public boolean deleteGame(IGame game);
	public boolean updateGameStatus(IGame game, String status);
	public String getGameStatus(IGame game);
	
	// Player
	public boolean createNewPlayer(IPlayer player);
	public IPlayer getIncomingPlayer(IGame game);
	
	// Hits
	public boolean sendHit(IGame game, IPlayer player, IHit hit);
	public IHit getHit(IGame game, IPlayer player);
	
	
}
