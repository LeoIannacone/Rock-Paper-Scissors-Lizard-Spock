//TODO: system.out di debug dei parametri passati

package it.unibo.games.rpsls.client.prototypes;

import java.util.ArrayList;
import java.util.List;

import it.unibo.games.rpsls.game.Game;
import it.unibo.games.rpsls.game.Hit;
import it.unibo.games.rpsls.game.Player;
import it.unibo.games.rpsls.interfaces.IGame;
import it.unibo.games.rpsls.interfaces.ICommand;
import it.unibo.games.rpsls.interfaces.IPlayer;
import it.unibo.games.rpsls.interfaces.client.IClientConnector;
import it.unibo.games.rpsls.interfaces.client.IClientObserver;

public class SimpleConnectorPrototype implements IClientConnector {
	
	private static IClientConnector instance;
	
	public static IClientConnector getInstance(){
		if (instance==null)
			instance = new SimpleConnectorPrototype();
		return instance;
	}

	private SimpleConnectorPrototype(){
		
	}
	
	private void debug(String s) {
		System.out.println(s);
	}
	
	@Override
	public void connect() {
		debug("CONNECT ...");
	}

	@Override
	public void disconnect() {
		debug("DISCONNECT ...");
	}

	@Override
	public boolean createNewGame(IGame game) {
		debug("CREATE new GAME: " + game.getURIToString());
		return true;
	}

	@Override
	public boolean joinGame(IGame game, IPlayer player) {
		debug(String.format("JOIN: P: %s - G: %s", game.getURIToString(), player.getURIToString()));
		return true;
	}

	@Override
	public boolean leaveGame(IGame game, IPlayer player) {
		debug(String.format("LEAVE: P: %s - G: %s", game.getURIToString(), player.getURIToString()));
		return true;
	}

	@Override
	public boolean endGame(IGame game) {
		debug(String.format("END: " + game.getURIToString()));
		return true;
	}

	@Override
	public boolean deleteGame(IGame game) {
		debug("DELETE: GAME " + game.getURIToString());
		return true;
	}

	@Override
	public boolean updateGameStatus(IGame game, String status) {
		debug("UPDATE STATUS: G: " + game.getURIToString() + " status: " + status);
		return true;
	}

	@Override
	public String getGameStatus(IGame game) {
		debug("GET STATUS G: " + game.getURIToString());
		return "Game Status";
	}

	@Override
	public boolean createNewPlayer(IPlayer player) {
		debug("CREATE new PLAYER " + player.getURIToString());
		return true;
	}

	@Override
	public boolean sendHit(IGame game, IPlayer player, ICommand hit) {
		debug(String.format("SEND HIT: %s %s %s", game.getURIToString(), player.getURIToString(), hit.getURIToString()));
		return true;
	}

	@Override
	public void init() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public boolean updateGameScore(IGame game) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public void watchForWaitingGames(IClientObserver observer) {
		List<IGame> WaitingGames = new ArrayList<IGame>();
		
		WaitingGames.add(new Game(new Player("Pippo"), null));
		WaitingGames.add(new Game(new Player("Pluto"), null));
		
		debug("GET waiting GAMES ...");
		observer.updateWaitingGames(WaitingGames);
	}

	@Override
	public void unwatchForWaitingGames() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void watchForIncomingPlayer(IGame game, IClientObserver observer) {
		try {
			Thread.sleep(3000);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		Player player = new Player("Carlo");
		game.setGuestPlayer(player);
		debug(String.format("GET incoming P: %s", player.getURIToString()));	
		observer.updateIncomingPlayer(game);	
	}

	@Override
	public void unwatchForIncomingPlayer() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void watchForHit(IGame game, IPlayer player, IClientObserver observer) {
		String[] hits = {Hit.ROCK, Hit.PAPER, Hit.SCISSORS, Hit.LIZARD, Hit.SPOCK};
		int i = (int) Math.random() % hits.length;
		debug(String.format("GET HIT %s from %s", hits[i], player.getURIToString()));
		observer.updateHit(new Hit(hits[i]));
	}

	@Override
	public void unwatchForHit() {
		// TODO Auto-generated method stub
		
	}


	@Override
	public void unwatchForGameEnded() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void watchForGameEnded(IGame game, IClientObserver observer) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void unwatchAll() {
		// TODO Auto-generated method stub
		
	}
}
