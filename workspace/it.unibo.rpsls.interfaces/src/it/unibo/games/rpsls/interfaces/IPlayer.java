package it.unibo.games.rpsls.interfaces;

import it.unibo.games.rpsls.client.interfaces.IConnectorEntity;

public interface IPlayer extends IConnectorEntity {

	public void setName(String name);
	public String getName();
	
	public void increaseScore();
	public void setScore(int score);
	public int getScore();
	
}
