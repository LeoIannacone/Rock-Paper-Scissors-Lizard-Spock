package it.unibo.games.rpsls.connector.client;

import it.unibo.games.rpsls.game.SimpleEntity;

import java.util.List;


public class SIBCommandInterface extends SimpleEntity {
	
	List<SIBCommand> commands;
	
	public SIBCommandInterface(){
		
	}
	
	public void setCommand(SIBCommand c){
		commands.add(c);
	}
	
	public List<SIBCommand> getCommands(){
		return commands;
	}
}
