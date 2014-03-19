package it.unibo.games.rpsls.tests.sib;

import com.hp.hpl.jena.shared.wg.TestInputStreamFactory;

import it.unibo.games.rpsls.connector.SIBConnector;
import it.unibo.games.rpsls.connector.SIBFactory;
import it.unibo.games.rpsls.connector.SIBSubscriptionHit;
import it.unibo.games.rpsls.connector.SIBSubscriptionWaitingGames;
import it.unibo.games.rpsls.connector.SIBSubscriptionWaitingIncomingPlayer;
import it.unibo.games.rpsls.game.Game;
import it.unibo.games.rpsls.game.Hit;
import it.unibo.games.rpsls.game.Player;
import it.unibo.games.rpsls.interfaces.IConnector;
import it.unibo.games.rpsls.interfaces.IGame;
import it.unibo.games.rpsls.interfaces.IPlayer;

public class ConnectorTest {
	
	protected static SIBConnector SIBC;
	
	public static void main(String args[]){
		SIBC = SIBConnector.getInstance();
		
		//trying connection to SIB
		SIBC.connect();
		
//		insertPlayer("");
		
//		insertGame();
		
//		testChangeStats();
		
//		testJoin();
		
//		testDeleteGame();
		
//		testSendHit("Game_f14c8b73-23db-4613-9b9b-e214dab30022");
//		testSubscriptionWaitingGames();
//		testSubscriptionJoinGame();
		
		testSubsctriptionHit();
	}
	
	private static void testSubsctriptionHit() {
		IPlayer home = new Player("homeSubscriptionHit");
		System.out.println("Home: " + home.getURIToString());
		SIBC.createNewPlayer(home);
		IPlayer guest = new Player("guestSubscriptionHit");
		System.out.println("Guest: " + guest.getURIToString());
		SIBC.createNewPlayer(guest);
		IGame game = new Game(home, guest);
		SIBC.createNewGame(game);
		System.out.println("Game: " + game.getURIToString());
		System.out.println("waiting new hit on commandInterface: " + game.getCommandInterface().getURIToString());
		new SIBSubscriptionHit(game, null);
		try {
			Thread.sleep(20000);
			testSendHit(game.getURIToString());
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	private static void testSubscriptionWaitingGames() {
		new SIBSubscriptionWaitingGames(null);
	}
	
//	private static void testSubscriptionJoinGame(){
//		IGame game = SIBFactory.getInstance().getGame("Game_aa14919d-00b6-4843-b329-863292896122");
//		new SIBSubscriptionWaitingIncomingPlayer(null, game);
//	}

	private static IGame insertGame() {
		//create players
		IPlayer p1 = insertPlayer("Pippo");
		IPlayer p2 = insertPlayer("Pluto");
		
		//create new games
		Game game = new Game(p1, p2);
		System.out.println("created new game with uri " + game.getURIToString());
		//insert game 1 in sib
		if(SIBC.createNewGame(game))
			System.out.println("game added correctly");
		return game;
		
	}

	public static IPlayer insertPlayer(String s){
		
		if (s == null || s == "")
			s = "test_insertPlayer";
		//create new player
		Player p = new Player(s);
		p.getURIToString();
		
		System.out.println("created new player with name " + p.getName() + " and uri " + p.getURIToString());
		
		//trying to insert new player into the SIB
		
		if(SIBC.createNewPlayer(p))
			System.out.println("player added correctly");
		
		return p;
	}
	
	public static void testChangeStats(){
		SIBFactory factory = SIBFactory.getInstance();
		IGame g = factory.getGame("Game_6ca50475-6ea3-4f36-94eb-bdff61c4bf3e");
		System.out.println("OLD STATUS: " + g.getStatus());
		if (SIBC.updateGameStatus(g, Game.ACTIVE)){
			g = factory.getGame(g.getURIToString());
			System.out.println("NEW STATUS: " + g.getStatus());
		}
	}
	
	public static void testJoin(){
		Player home = new Player("testJoinHOME");
		SIBC.createNewPlayer(home);
		IGame g = new Game(home, null);
		SIBC.createNewGame(g);
		System.out.println("JUST_CREATED: " + g.toString());
		Player guest = new Player("testJoinGUEST");
		SIBC.createNewPlayer(guest);
		SIBC.joinGame(g, guest);
		
		g = SIBFactory.getInstance().getGame(g.getURIToString());
		System.out.println("JOINED: " + g.toString());
	}
	
	public static void testDeleteGame(){
		Player home = new Player("testDeleteHOME");
		SIBC.createNewPlayer(home);
		IGame g = new Game(home, null);
		SIBC.createNewGame(g);
		System.out.println("JUST_CREATED: " + g.toString());
		SIBC.deleteGame(g);
		System.out.println("Game Deleted");
	}
	
	public static void testSendHit(){
		IGame g = insertGame();
		Hit hit = new Hit("Paper");
		SIBC.sendHit(g, g.getHomePlayer(), hit);
		System.out.println("Hit sended: " + hit.toString());
	}
	
	public static void testSendHit(String uriGame){
		IGame g = SIBFactory.getInstance().getGame(uriGame);
		Hit hit = new Hit(Hit.PAPER);
		SIBC.sendHit(g, g.getGuestPlayer(), hit);
		System.out.println("Hit sended: " + hit.toString());
	}
}
