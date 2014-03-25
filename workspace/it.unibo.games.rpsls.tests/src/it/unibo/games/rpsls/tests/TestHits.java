package it.unibo.games.rpsls.tests;

import it.unibo.games.rpsls.game.Hit;
import it.unibo.games.rpsls.game.Utils;

public class TestHits {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		
//		compare();
		
		idTest();
		
	}
	
	public static void idTest() {
		Hit f = new Hit(Hit.PAPER);
		System.out.println("ID_RANDOM: " + f.getURIToString());
		
		f.setURI("Hit_6d8de31e-690f-45d0-9693-8c2a4125dd1e");
		System.out.println("ID_setWithClassName: " + f.getURIToString());
		
		f.setURI("6d8de31e-690f-45d0-9693-8c2a4125dd1e");
		System.out.println("ID_noClassName: " + f.getURIToString());
		
	}
	
	public static void compare() {
		Hit f = new Hit(Hit.PAPER);
		Hit s = new Hit(Hit.SPOCK);
		
		if (f.compareTo(s) < 0) {
			System.out.println(String.format("loose: %s %s %s", s, Utils.compareHits(s,f)[1], f));
		}
		else {
			System.out.println(String.format("WIN: %s %s %s", f, Utils.compareHits(f,s)[1], s));
		}
	}

}
