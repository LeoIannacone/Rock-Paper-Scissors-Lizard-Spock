package it.unibo.games.rpsls.game;

import it.unibo.games.rpsls.connector.ConnectorEntity;
import it.unibo.games.rpsls.interfaces.ICommand;
import it.unibo.games.rpsls.interfaces.IPlayer;

public class Hit extends ConnectorEntity implements ICommand {

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
	public int compareTo(ICommand o) {
		return Integer.parseInt(Utils.compareHits(this, o)[0]);
	}

	@Override
	public void setCommandType(String name) {
		this.name = name;
	}
	@Override
	public String getCommandType() {
		return name;
	}
	@Override
	public String toString() {
		return name;
	}

	@Override
	public boolean equals(ICommand i) {
		return name.equals(i.getCommandType());
	}

	@Override
	public void setIssuer(IPlayer player) {
		this.player = player;
	}

	@Override
	public IPlayer getIssuer() {
		return player;
	}
}
