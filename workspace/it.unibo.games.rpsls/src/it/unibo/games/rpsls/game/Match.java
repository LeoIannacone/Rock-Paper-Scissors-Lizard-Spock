package it.unibo.games.rpsls.game;

import it.unibo.games.rpsls.interfaces.IMatch;
import it.unibo.games.rpsls.interfaces.IPlayer;

public class Match implements IMatch {

	private IPlayer home;
	private IPlayer guest;
	
	private int homeStatus;
	private int guestStatus;
	
	public void Match(IPlayer home, IPlayer guest) {
		this.home = home;
		this.guest = guest;
		homeStatus = 0;
		guestStatus = 0;
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
	public void setHomeStatus(int n) {
		homeStatus = n;
	}

	@Override
	public void increaseHomeStatus() {
		homeStatus++;
	}

	@Override
	public int getHomeStatus() {
		return homeStatus;
	}

	@Override
	public void setGuestStatus(int n) {
		guestStatus = n;
	}

	@Override
	public void increaseGuestStatus() {
		guestStatus++;
	}

	@Override
	public int getGuestStatus() {
		return guestStatus;
	}

}
