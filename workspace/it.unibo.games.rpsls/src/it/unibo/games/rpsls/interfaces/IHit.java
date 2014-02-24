package it.unibo.games.rpsls.interfaces;

public interface IHit extends Comparable<IHit> {

	public String getName();
	
	public boolean equals(IHit i);
	
}
