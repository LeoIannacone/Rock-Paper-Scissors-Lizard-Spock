package it.unibo.games.rpsls.game;

import it.unibo.games.rpsls.interfaces.IPlayer;

public class Player extends SimpleEntity implements IPlayer {

	private String name;
	private int score;
	
	public Player (String name) {
		this.name = name;
		this.score = 0;
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
	public void increaseScore() {
		score++;
	}

	@Override
	public void setScore(int score) {
		this.score = score;
	}

	@Override
	public int getScore() {
		return score;
	}

}
