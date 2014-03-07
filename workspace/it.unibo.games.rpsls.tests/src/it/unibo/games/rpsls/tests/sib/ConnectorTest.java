package it.unibo.games.rpsls.tests.sib;

import it.unibo.games.rpsls.connector.SIBConnector;
import it.unibo.games.rpsls.game.Player;
import it.unibo.games.rpsls.interfaces.IConnector;

public class ConnectorTest {
	public static void main(String args[]){
		IConnector SIBC = SIBConnector.getInstance();
		
		//trying connection to SIB
		SIBC.connect();
		
		//create new player
		Player p = new Player("Pippo");
		p.getURIToString();
		
		System.out.println("created new player with name " + p.getName() + " and uri " + p.getURIToString());
		
		//trying to insert new player into the SIB
		
		if(SIBC.createNewPlayer(p)){
			System.out.println("player added correctly");
		}
	}
}
