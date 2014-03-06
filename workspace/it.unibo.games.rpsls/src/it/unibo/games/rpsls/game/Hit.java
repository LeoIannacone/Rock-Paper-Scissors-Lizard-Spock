package it.unibo.games.rpsls.game;

import it.unibo.games.rpsls.connector.ConnectorEntity;
import it.unibo.games.rpsls.interfaces.IHit;
import it.unibo.games.rpsls.interfaces.IPlayer;

public class Hit extends ConnectorEntity implements IHit {

	public static String ROCK = "rock";
	public static String PAPER = "paper";
	public static String SCISSORS = "scissors";
	public static String LIZARD = "lizard";
	public static String SPOCK = "spock";
	
	private String name;
	private IPlayer player;
	
	public Hit(String name) {
		this.name = name;
	}
	
	@Override
	public int compareTo(IHit o) {
		return Integer.parseInt(Utils.compareHits(this, o)[0]);
	}

	@Override
	public void setName(String name) {
		this.name = name;
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

	@Override
	public void setPlayer(IPlayer player) {
		this.player = player;
	}

	@Override
	public IPlayer getPlayer() {
		return player;
	}
}
