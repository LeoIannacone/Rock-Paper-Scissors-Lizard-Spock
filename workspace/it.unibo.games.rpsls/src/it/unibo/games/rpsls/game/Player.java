package it.unibo.games.rpsls.game;

import it.unibo.games.rpsls.interfaces.IPlayer;

public class Player implements IPlayer {

	private String name;
	
	public Player (String name) {
		this.name = name;
	}
	
	@Override
	public void setName(String name) {
		this.name = name;
	}

	@Override
	public String getName() {
		return name;
	}

}
