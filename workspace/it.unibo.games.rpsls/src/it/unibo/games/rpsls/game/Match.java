package it.unibo.games.rpsls.game;

import it.unibo.games.rpsls.interfaces.IMatch;
import it.unibo.games.rpsls.interfaces.IPlayer;

public class Match implements IMatch {

	private IPlayer home;
	private IPlayer guest;
	private String status;
	
	public Match(IPlayer home, IPlayer guest) {
		this.home = home;
		this.guest = guest;
		this.status = DefaultValues.WAITING; 
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
		home.setScore(n);
	}

	@Override
	public void increaseHomeScore() {
		home.increaseScore();
	}

	@Override
	public int getHomeScore() {
		return home.getScore();
	}

	@Override
	public void setGuestScore(int n) {
		guest.setScore(n);
	}

	@Override
	public void increaseGuestScore() {
		guest.increaseScore();
	}

	@Override
	public int getGuestScore() {
		return guest.getScore();
	}

	@Override
	public void setStatus(String status) {
	  this.status = status;	
	}

	@Override
	public String getStatus() {
    return status;
	}

}
