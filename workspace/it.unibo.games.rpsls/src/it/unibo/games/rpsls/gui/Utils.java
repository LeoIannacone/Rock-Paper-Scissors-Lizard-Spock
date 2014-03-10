package it.unibo.games.rpsls.gui;

import java.io.File;

import it.unibo.games.rpsls.interfaces.ICommand;

public class Utils {
	
	public static File getHitButtonIcon(ICommand hit) {
		return getIcon(hit.getCommandType(), 32);
	}
	
	public static File getHitPanelIcon(ICommand hit) {
		return getIcon(hit.getCommandType(), 96);
	}
	public static File getHitPanelIconBlank() {
		return getIcon("hit-blank", 96);
	}
	private static File getIcon(String name, int dimension) {
		return new File(String.format("data/%s-%d.png", name, dimension));
	}

	public static String capitalize(String str) {
		if (str == null || str == "")
			return "";
		return Character.toUpperCase(str.charAt(0)) + str.substring(1);
	}
	
}
