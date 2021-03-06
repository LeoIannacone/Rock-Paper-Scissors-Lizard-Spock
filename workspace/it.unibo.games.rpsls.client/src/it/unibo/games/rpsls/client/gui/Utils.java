package it.unibo.games.rpsls.client.gui;

import it.unibo.games.rpsls.interfaces.ICommand;
import it.unibo.games.rpsls.interfaces.ISimpleEntity;

public class Utils {
		
	public static String getHitButtonIcon(ICommand hit) {
		return getIcon(hit.getCommandType(), 32);
	}
	
	public static String getHitPanelIcon(ICommand hit) {
		return getIcon(hit.getCommandType(), 96);
	}
	public static String getHitPanelIconBlank() {
		return getIcon("hit-blank", 96);
	}
	private static String getIcon(String name, int dimension) {
		return String.format("data/%s-%d.png", name, dimension);
	}

	public static String capitalize(String str) {
		if (str == null || str == "")
			return "";
		return Character.toUpperCase(str.charAt(0)) + str.substring(1);
	}
	
	public static String getSubID(ISimpleEntity entity) {
		String uri = entity.getURIToString();
		int len = uri.length();
		return uri.substring(len - 4);
	}
}
