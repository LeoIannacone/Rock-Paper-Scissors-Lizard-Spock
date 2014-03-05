package it.unibo.games.rpsls.interfaces;

import java.util.UUID;

public interface IConnectorEntity {

	public void setId(UUID id);
	public UUID getId();
	public String getIdToString();
	
}
