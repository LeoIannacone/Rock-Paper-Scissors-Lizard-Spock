package it.unibo.games.rpsls.tests.sib;

import it.unibo.games.rpsls.connector.client.SIBFactory;
import it.unibo.games.rpsls.interfaces.*;

public class FactoryTest {
	
	private static SIBFactory factory;
	
	public static void main(String []args){
		
		factory = SIBFactory.getInstance();
		testGetGame();
		
	}
	
	public static void testGetPlayer(){
		IPlayer p = factory.getPlayer("Player_e981ab8d-1a53-47f1-b112-1387f2c00bbd");
		System.out.println(p.getName() + " has URI " + p.getURIToString());
	}
	
	public static void testGetGame(){
		IGame g = factory.getGame("Game_6ca50475-6ea3-4f36-94eb-bdff61c4bf3e");
		System.out.println(g.toString());
		
		g = factory.getGame("Game_44a16d1d-2258-4e66-be70-94add0254952");
		System.out.println(g.toString());
		
		
	}
}
