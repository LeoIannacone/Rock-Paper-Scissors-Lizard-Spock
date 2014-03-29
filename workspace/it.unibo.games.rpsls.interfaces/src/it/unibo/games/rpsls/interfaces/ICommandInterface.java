package it.unibo.games.rpsls.interfaces;


import java.util.List;

public interface ICommandInterface extends ISimpleEntity{
	public void setCommand(ICommand command);
	public List<ICommand> getCommand();
}
