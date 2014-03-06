package it.unibo.games.rpsls.interfaces;

public interface IHit extends Comparable<IHit>, IConnectorEntity {

	public String getName();
	public void setName(String name);
	
	public void setPlayer(IPlayer player);
	public IPlayer getPlayer();
	
	public boolean equals(IHit i);
	
}
