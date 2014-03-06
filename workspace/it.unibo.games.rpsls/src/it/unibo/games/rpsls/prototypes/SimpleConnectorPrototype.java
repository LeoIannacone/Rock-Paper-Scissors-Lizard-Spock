//TODO: system.out di debug dei parametri passati

package it.unibo.games.rpsls.prototypes;

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
		debug("CREATE new GAME: " + game.getIdToString());
		return true;
	}

	@Override
	public List<IGame> getWaitingGames() {
		List<IGame> WaitingGames = new ArrayList<IGame>();
		
		WaitingGames.add(new Game(new Player("Pippo"), null));
		WaitingGames.add(new Game(new Player("Pluto"), null));
		
		debug("GET waiting GAMES ...");
		return WaitingGames;
	}

	@Override
	public boolean joinGame(IGame game, IPlayer player) {
		debug(String.format("JOIN: P: %s - G: %s", game.getIdToString(), player.getIdToString()));
		return true;
	}

	@Override
	public boolean leaveGame(IGame game, IPlayer player) {
		debug(String.format("LEAVE: P: %s - G: %s", game.getIdToString(), player.getIdToString()));
		return true;
	}

	@Override
	public boolean endGame(IGame game) {
		debug(String.format("END: " + game.getIdToString()));
		return true;
	}

	@Override
	public boolean deleteGame(IGame game) {
		debug("DELETE: GAME " + game.getIdToString());
		return true;
	}

	@Override
	public boolean updateGameStatus(IGame game, String status) {
		debug("UPDATE STATUS: G: " + game.getIdToString() + " status: " + status);
		return true;
	}

	@Override
	public String getGameStatus(IGame game) {
		debug("GET STATUS G: " + game.getIdToString());
		return "Game Status";
	}

	@Override
	public boolean createNewPlayer(IPlayer player) {
		debug("CREATE new PLAYER " + player.getIdToString());
		return true;
	}

	@Override
	public boolean sendHit(IGame game, IPlayer player, IHit hit) {
		debug(String.format("SEND HIT: %s %s %s", game.getIdToString(), player.getIdToString(), hit.getIdToString()));
		return true;
	}

	@Override
	public IHit getHit(IGame game, IPlayer player) {
		String[] hits = {Hit.ROCK, Hit.PAPER, Hit.SCISSORS, Hit.LIZARD, Hit.SPOCK};
		int i = (int) Math.random() % hits.length;
		debug(String.format("GET HIT %s from %s", hits[i], player.getIdToString()));
		return new Hit(hits[i]);
	}

	@Override
	public IPlayer getIncomingPlayer(IGame game) {
		try {
			Thread.sleep(3000);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		Player player = new Player("Carlo");
		game.setGuestPlayer(player);
		debug(String.format("GET incoming P: %s", player.getIdToString()));	
		return player;
	}

	@Override
	public void init() {
		// TODO Auto-generated method stub
		
	}
}
