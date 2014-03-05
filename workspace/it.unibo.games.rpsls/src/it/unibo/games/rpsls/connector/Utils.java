package it.unibo.games.rpsls.connector;

import java.util.Vector;

public class Utils {
	public static Vector<String> toVector(String subject, String predicate, String object) {
		Vector<String> result = new Vector<String>();
		result.add(subject);
		result.add(predicate);
		result.add(object);
		return result;
	}
}
