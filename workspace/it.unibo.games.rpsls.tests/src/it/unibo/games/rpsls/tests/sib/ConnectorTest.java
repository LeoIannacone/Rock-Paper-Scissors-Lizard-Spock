package it.unibo.games.rpsls.tests.sib;

import it.unibo.games.rpsls.connector.SIBConnector;
import it.unibo.games.rpsls.connector.SIBFactory;
import it.unibo.games.rpsls.game.Game;
import it.unibo.games.rpsls.game.Player;
import it.unibo.games.rpsls.interfaces.IConnector;
import it.unibo.games.rpsls.interfaces.IGame;

public class ConnectorTest {
	
	protected static SIBConnector SIBC;
	
	public static void main(String args[]){
		SIBC = SIBConnector.getInstance();
		
		//trying connection to SIB
		SIBC.connect();
		
//		insertGame();
		
//		testChangeStats();
		
//		testJoin();
		
		testDeleteGame();
	}
	
	private static void insertGame() {
		//create players
		Player p1 = insertPlayer("Pippo");
		Player p2 = insertPlayer("Pluto");
		Player p3 = insertPlayer("xxx");
		
		//create new games
		Game g1 = new Game(p1, null);
		System.out.println("created new game with uri " + g1.getURIToString());
		//insert game 1 in sib
		if(SIBC.createNewGame(g1))
			System.out.println("game added correctly");

		Game g2 = new Game(p2, p3);
		System.out.println("created new game with uri " + g1.getURIToString());
		//insert game 1 in sib
		if(SIBC.createNewGame(g2))
			System.out.println("game added correctly");
		
	}

	public static Player insertPlayer(String s){
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
}
