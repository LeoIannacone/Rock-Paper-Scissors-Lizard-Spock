package it.unibo.games.rpsls.connector;

import java.util.List;


public class SIBCommandInterface extends ConnectorEntity {
	
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
