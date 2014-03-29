package it.unibo.games.rpsls.game;

import it.unibo.games.rpsls.interfaces.ISimpleEntity;
import it.unibo.games.rpsls.interfaces.IGame;
import it.unibo.games.rpsls.interfaces.IPlayer;

public class Game extends SimpleEntity implements IGame {

	public static String ACTIVE = "active";
	public static String ENDED = "ended";
	public static String WAITING = "waiting";
	public static String PAUSED = "paused";
	
	protected ISimpleEntity commandInterface;
	
	private IPlayer home;
	private IPlayer guest;
	private String status;
	private boolean homeAsOpponent;
	
	public Game(IPlayer home, IPlayer guest) {
		this.home = home;
		this.guest = guest;
		this.status = WAITING;
		this.homeAsOpponent = false;
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

	@Override
	public void setScore(String s) {
		try{
			String [] a = s.split("-");
			home.setScore(Integer.parseInt(a[0]));
			guest.setScore(Integer.parseInt(a[1]));
		} catch(Exception e) {}
	}

	@Override
	public String getScore() {
		try{
			return home.getScore() + "-" + guest.getScore();
		}catch(Exception e){
			return "";
		}
	}

	
	public String toString() {
		try {
			return String.format("%s [%s] - %s vs %s - score: %s", getURIToString(), getStatus(), getHomePlayer().getName(), getGuestPlayer().getName(), getScore());
		} catch (Exception e) {
			try {
				return String.format("%s [%s] - %s", getURIToString(), getStatus(), getHomePlayer().getName());
			} catch (Exception e1) {
				return String.format("%s [%s]", getURIToString(), getStatus());
			}
		}
	}

	@Override
	public void setCommandInterface(ISimpleEntity commandInterface) {
		this.commandInterface = commandInterface;
	}

	@Override
	public ISimpleEntity getCommandInterface() {
		return this.commandInterface;
	}

	@Override
	public void setHomeAsOpponent(boolean isOpponent) {
		this.homeAsOpponent = isOpponent;
	}

	@Override
	public IPlayer getOpponent() {
		if (this.homeAsOpponent)
			return home;
		return guest;
	}

	@Override
	public IPlayer getMe() {
		if (! this.homeAsOpponent)
			return home;
		return guest;
	}
	
	// useful method for lists.contains(game)
	public boolean equals(IGame o) {
		return this.getURIToString().equals(o.getURIToString());
	}
}
