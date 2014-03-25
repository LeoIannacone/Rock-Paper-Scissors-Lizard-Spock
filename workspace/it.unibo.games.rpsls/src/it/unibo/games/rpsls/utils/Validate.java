package it.unibo.games.rpsls.utils;

import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.regex.PatternSyntaxException;

public class Validate {
	private static final String IPADDRESS_PATTERN = 
			"^([01]?\\d\\d?|2[0-4]\\d|25[0-5])\\." +
			"([01]?\\d\\d?|2[0-4]\\d|25[0-5])\\." +
			"([01]?\\d\\d?|2[0-4]\\d|25[0-5])\\." +
			"([01]?\\d\\d?|2[0-4]\\d|25[0-5])$";
	
	static public int port (String portString) {
		int port = Integer.parseInt(portString);
		if (port <= 0 || port > 65535) 
			throw new NumberFormatException();
		return port;
	}
	
	static public String ip (String ipAddress) {
		Pattern pattern = Pattern.compile(IPADDRESS_PATTERN);
		Matcher matcher = pattern.matcher(ipAddress);
		if (! matcher.matches())
			throw new PatternSyntaxException("", "", 0);
		return ipAddress;	    	    
	}
}