package it.unibo.games.rpsls.interfaces;

public interface IConnector {
	
	public void connect();
	public void disconnect();
	
	public void startGame();
	public void getGame();
	public void endGame();
	
	public void sendHit();
	public void getHit();
	
	public void getResult();
	
}
