package it.unibo.games.rpsls.connector;

public class Utils {
	
	public static String removePrefix(String uri){
		if (uri.contains("#"))
			return uri.split("#")[1];
		return uri;
	}
	
}
