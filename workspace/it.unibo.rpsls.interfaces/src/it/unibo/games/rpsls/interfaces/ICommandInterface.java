package it.unibo.games.rpsls.interfaces;

import it.unibo.games.rpsls.client.interfaces.IConnectorEntity;

import java.util.List;

public interface ICommandInterface extends IConnectorEntity{
	public void setCommand(ICommand command);
	public List<ICommand> getCommand();
}
