package it.unibo.games.rpsls.utils;

public class Debug {
	
	/**
	 * 0 - disable
	 * 1 - interactions
	 * 2 - SIB debug
	 */
	
	public static int LEVEL = 0;
	
	public static void print(int level, String message){
		if (level<=LEVEL){
			System.out.println(String.format(" debug [%d] - %s", level, message));
		}
	}
	
	public static void setLeve(int level){
		LEVEL = level;
	}
}
