package it.unibo.games.rpsls.interfaces;

import it.unibo.games.rpsls.client.interfaces.IConnectorEntity;

public interface ICommand extends Comparable<ICommand>, IConnectorEntity {

	public String getCommandType();
	public void setCommandType(String name);
	
	public void setIssuer(IPlayer player);
	public IPlayer getIssuer();
	
	public boolean equals(ICommand i);
	
	public void setTime(long time);
	public long getTime();
}
