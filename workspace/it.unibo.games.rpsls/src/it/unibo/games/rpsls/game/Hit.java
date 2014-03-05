package it.unibo.games.rpsls.game;

import it.unibo.games.rpsls.connector.ConnectorEntity;
import it.unibo.games.rpsls.interfaces.IHit;

public class Hit extends ConnectorEntity implements IHit {

	private String name;
	
	public Hit(String name) {
		this.name = name;
	}
	
	@Override
	public int compareTo(IHit o) {
		return Integer.parseInt(Utils.compareHits(this, o)[0]);
	}

	@Override
	public String getName() {
		return name;
	}
	@Override
	public String toString() {
		return name;
	}

	@Override
	public boolean equals(IHit i) {
		return name.equals(i.getName());
	}
}
