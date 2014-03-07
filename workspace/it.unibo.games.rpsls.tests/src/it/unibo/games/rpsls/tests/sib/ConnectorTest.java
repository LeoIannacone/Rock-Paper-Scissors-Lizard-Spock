package it.unibo.games.rpsls.tests.sib;

import it.unibo.games.rpsls.connector.SIBConnector;
import it.unibo.games.rpsls.game.Game;
import it.unibo.games.rpsls.game.Player;
import it.unibo.games.rpsls.interfaces.IConnector;

public class ConnectorTest {
	
	protected static IConnector SIBC;
	
	public static void main(String args[]){
		SIBC = SIBConnector.getInstance();
		
		//trying connection to SIB
		SIBC.connect();
		
		insertGame();
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
}
