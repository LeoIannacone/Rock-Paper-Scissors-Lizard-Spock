package it.unibo.games.rpsls.tests;

import java.util.ArrayList;
import java.util.List;

import it.unibo.games.rpsls.game.Game;
import it.unibo.games.rpsls.game.Hit;
import it.unibo.games.rpsls.game.Player;
import it.unibo.games.rpsls.interfaces.IConnector;
import it.unibo.games.rpsls.interfaces.IGame;
import it.unibo.games.rpsls.interfaces.IHit;
import it.unibo.games.rpsls.interfaces.IPlayer;

public class SimpleConnectorPrototype implements IConnector {
	
	private static IConnector instance;
	
	public static IConnector getInstance(){
		if (instance==null)
			instance = new SimpleConnectorPrototype();
		return instance;
	}

	private SimpleConnectorPrototype(){
		
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
		return true;
	}

	@Override
	public List<IGame> getWaitingGames() {
		List<IGame> WaitingGames = new ArrayList<IGame>();
		
		WaitingGames.add(new Game(new Player("Pippo"), null));
		WaitingGames.add(new Game(new Player("Pluto"), null));
		
		return WaitingGames;
	}

	@Override
	public boolean joinGame(IGame game, IPlayer player) {
		// TODO Auto-generated method stub
		return true;
	}

	@Override
	public boolean leaveGame(IGame game, IPlayer player) {
		// TODO Auto-generated method stub
		return true;
	}

	@Override
	public boolean endGame(IGame game) {
		// TODO Auto-generated method stub
		return true;
	}

	@Override
	public boolean deleteGame(IGame game) {
		// TODO Auto-generated method stub
		return true;
	}

	@Override
	public boolean updateGameStatus(String status) {
		// TODO Auto-generated method stub
		return true;
	}

	@Override
	public String getGameStatus(IGame game) {
		// TODO Auto-generated method stub
		return "Game Status";
	}

	@Override
	public boolean createNewPlayer(IPlayer player) {
		// TODO Auto-generated method stub
		return true;
	}

	@Override
	public boolean sendHit(IGame game, IPlayer player, IHit hit) {
		// TODO Auto-generated method stub
		return true;
	}

	@Override
	public IHit getHit(IGame game, IPlayer player) {
		// TODO Auto-generated method stub
			IHit hit = new Hit("RPSLS");
		return hit;
	}
}
