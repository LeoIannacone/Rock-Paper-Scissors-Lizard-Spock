package it.unibo.games.rpsls.interfaces;

public interface ICommand extends Comparable<ICommand>, IConnectorEntity {

	public String getCommandType();
	public void setName(String name);
	
	public void setIssuer(IPlayer player);
	public IPlayer getIssuer();
	
	public boolean equals(ICommand i);
	
}
