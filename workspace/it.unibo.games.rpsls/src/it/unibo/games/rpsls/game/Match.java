package it.unibo.games.rpsls.game;

import it.unibo.games.rpsls.interfaces.IMatch;
import it.unibo.games.rpsls.interfaces.IPlayer;

public class Match implements IMatch {

	private IPlayer home;
	private IPlayer guest;
	
	private int homeScore;
	private int guestScore;
	
	public Match(IPlayer home, IPlayer guest) {
		this.home = home;
		this.guest = guest;
		homeScore = 0;
		guestScore = 0;
	}
	
	@Override
	public void setHomePlayer(IPlayer home) {
		this.home = home;
	}

	@Override
	public IPlayer getHomePlayer() {
		return home;
	}

	@Override
	public void setGuestPlayer(IPlayer guest) {
		this.guest = guest;
	}

	@Override
	public IPlayer getGuestPlayer() {
		return guest;
	}

	@Override
	public void setHomeScore(int n) {
		homeScore = n;
	}

	@Override
	public void increaseHomeScore() {
		homeScore++;
	}

	@Override
	public int getHomeScore() {
		return homeScore;
	}

	@Override
	public void setGuestScore(int n) {
		guestScore = n;
	}

	@Override
	public void increaseGuestScore() {
		guestScore++;
	}

	@Override
	public int getGuestScore() {
		return guestScore;
	}

}
