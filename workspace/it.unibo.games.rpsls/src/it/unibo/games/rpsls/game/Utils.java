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
		
		if (f.equals(L)) {
			if(s.equals(Sp))	r = "poisons";
			else if (s.equals(P)) r = "eats";
		}
		
		else if (f.equals(Sc)) {
			if (s.equals(L)) r = "decapitates";
			else if (s.equals(P)) r = "cuts";
		}
		
		else if (f.equals(Sp)) {
			if (s.equals(Sc)) r = "smashes";
			else if (s.equals(R)) r = "vaporizes";
		}
		
		else if (f.equals(P)) {
			if (s.equals(R)) r = "covers";
			else if (s.equals(Sp)) r = "diproves";
		}
		
		else if (f.equals(R)) {
			if (s.equals(Sc)) r = "crushes";
			else if (s.equals(L)) r = "crushes";
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
