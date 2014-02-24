package it.unibo.games.rpsls.gui;

import java.io.File;

import it.unibo.games.rpsls.interfaces.IHit;

public class Utils {
	
	public static File getHitButtonIcon(IHit hit) {
		return getIcon(hit.getName(), 32);
	}
	
	public static File getHitPanelIcon(IHit hit) {
		return getIcon(hit.getName(), 96);
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
