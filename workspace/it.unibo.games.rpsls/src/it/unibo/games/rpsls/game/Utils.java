package it.unibo.games.rpsls.game;

import it.unibo.games.rpsls.interfaces.ICommand;

public class Utils {

	public static String[] compareHits(ICommand first, ICommand second) {
		String[] result = new String[2];
	
		String f = first.getCommandType();
		String s = second.getCommandType();
		
		if (f.equals(s)) {
			result[0] = "0";
			result[1] = "fair";
			return result;
		}
		
		result[0] = "1";
		String r = "";
		
		String Sc = Hit.SCISSORS;
		String P = Hit.PAPER;
		String L = Hit.LIZARD;
		String Sp = Hit.SPOCK;
		String R = Hit.ROCK;
		
		if (f == L) {
			if(s == Sp)	r = "poisons";
			else if (s == P) r = "eats";
		}
		
		else if (f == Sc) {
			if (s == L) r = "decapitates";
			else if (s == P) r = "cuts";
		}
		
		else if (f == Sp) {
			if (s == Sc) r = "smashes";
			else if (s == R) r = "vaporizes";
		}
		
		else if (f == P) {
			if (s == R) r = "covers";
			else if (s == Sp) r = "diproves";
		}
		
		else if (f == R) {
			if (s == Sc) r = "crushes";
			else if (s == L) r = "crushes";
		}
		
		else {
			new Exception("UTILS: first hit is not valid: " + f).printStackTrace();
		}
		
		if (r != "") {
			result[1] = r;
		}
		else {
			result[0] = "-1";
			result[1] = compareHits(second, first)[1];
		}
		
		return result;
	}
}
