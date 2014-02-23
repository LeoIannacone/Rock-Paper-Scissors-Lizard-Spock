package it.unibo.games.rpsls.interfaces;

public interface IMatch {
	
	public void setHomePlayer(IPlayer home);
	public IPlayer getHomePlayer();
	
	public void setGuestPlayer(IPlayer guest);
	public IPlayer getGuestPlayer();
	
	public void setHomeStatus(int n);
	public void increaseHomeStatus();
	public int getHomeStatus();
	
	public void setGuestStatus(int n);
	public void increaseGuestStatus();
	public int getGuestStatus();
	
}
