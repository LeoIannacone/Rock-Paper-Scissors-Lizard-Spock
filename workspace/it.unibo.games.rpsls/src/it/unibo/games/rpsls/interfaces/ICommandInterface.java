package it.unibo.games.rpsls.interfaces;

import java.util.List;

public interface ICommandInterface extends IConnectorEntity{
	public void setCommand(ICommand command);
	public List<ICommand> getCommand();
}
