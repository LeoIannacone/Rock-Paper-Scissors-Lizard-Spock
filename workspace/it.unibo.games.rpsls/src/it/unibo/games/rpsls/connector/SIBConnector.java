package it.unibo.games.rpsls.connector;

import java.util.List;
import java.util.UUID;
import java.util.Vector;

import it.unibo.games.rpsls.interfaces.IConnector;
import it.unibo.games.rpsls.interfaces.IGame;
import it.unibo.games.rpsls.interfaces.IHit;
import it.unibo.games.rpsls.interfaces.IPlayer;

public class SIBConnector implements IConnector {

	private static IConnector instance;
	
	public static IConnector getInstance() {
		if (instance == null)
			instance = new SIBConnector();
		return instance;
	}
	
	private SIBConnector() {
		
	}

	@Override
	public void connect() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void disconnect() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public boolean createNewGame(IGame game) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public List<IGame> getWaitingGames() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public boolean joinGame(IGame game, IPlayer player) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean leaveGame(IGame game, IPlayer player) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean endGame(IGame game) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean deleteGame(IGame game) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean updateGameStatus(IGame game, String status) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public String getGameStatus(IGame game) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public boolean createNewPlayer(IPlayer player) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean sendHit(IGame game, IPlayer player, IHit hit) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public IHit getHit(IGame game, IPlayer player) {
		// TODO Auto-generated method stub
		return null;
	}

}
