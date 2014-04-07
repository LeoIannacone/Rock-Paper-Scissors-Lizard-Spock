package it.unibo.games.rpsls.tests.sib;

import it.unibo.games.rpsls.connector.admin.SIBAdmin;
import it.unibo.games.rpsls.connector.client.SIBClient;
import it.unibo.games.rpsls.game.Game;
import it.unibo.games.rpsls.game.Hit;
import it.unibo.games.rpsls.game.Player;
import it.unibo.games.rpsls.interfaces.IGame;
import it.unibo.games.rpsls.interfaces.IPlayer;
import it.unibo.games.rpsls.interfaces.admin.IAdminConnector;

public class AdminTest {
	
	protected static IAdminConnector admin;
	protected static SIBClient sibConnector;
	
	public static void main(String[] args){
		sibConnector = SIBClient.getInstance();
		admin = SIBAdmin.getInstance();
		admin.watchForEndingGames(null);
		testDeleteGame();
	}

	private static void testDeleteGame(){
		
		//insert player and game in sib
		System.out.println("Creatint new home player...");
		IPlayer home = new Player("deleteGameHome");
		sibConnector.createNewPlayer(home);
		System.out.println("Creatint new guest player...");
		IPlayer guest = new Player("deleteGameGuest");
		sibConnector.createNewPlayer(guest);
		System.out.println("Creatint new game...");
		IGame game = new Game(home, guest);
		sibConnector.createNewGame(game);
		System.out.println(game.toString());
		
//		//add some hit for each player
		System.out.println("Adding some hit for each player...");
		sibConnector.sendHit(game, home, new Hit(Hit.ROCK));
		sibConnector.sendHit(game, guest, new Hit(Hit.PAPER));
		sibConnector.sendHit(game, home, new Hit(Hit.ROCK));
		sibConnector.sendHit(game, guest, new Hit(Hit.PAPER));
		sibConnector.sendHit(game, home, new Hit(Hit.ROCK));
		sibConnector.sendHit(game, guest, new Hit(Hit.PAPER));
		
		//set game to ended
		System.out.println("Ending game...");
		sibConnector.updateGameStatus(game, Game.ENDED);
		try {
			Thread.sleep(3000);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		admin.deleteGame(game);
		
	}
}
