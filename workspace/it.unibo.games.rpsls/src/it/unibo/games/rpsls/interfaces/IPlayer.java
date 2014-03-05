package it.unibo.games.rpsls.interfaces;

public interface IPlayer extends IConnectorEntity {

	public void setName(String name);
	public String getName();
	
	public void increaseScore();
	public void setScore(int score);
	public int getScore();
	
}
