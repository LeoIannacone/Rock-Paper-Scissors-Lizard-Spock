package it.unibo.games.rpsls.tests.sib;

import it.unibo.games.rpsls.connector.SIBFactory;
import it.unibo.games.rpsls.interfaces.IPlayer;

public class FactoryTest {
	
	private static SIBFactory factory;
	
	public static void main(String []args){
		
		factory = SIBFactory.getInstance();

	}
	
	public void testGetPlayer(){
		IPlayer p = factory.getPlayer("Player_e981ab8d-1a53-47f1-b112-1387f2c00bbd");
		System.out.println(p.getName() + " has URI " + p.getURIToString());
	}
}
