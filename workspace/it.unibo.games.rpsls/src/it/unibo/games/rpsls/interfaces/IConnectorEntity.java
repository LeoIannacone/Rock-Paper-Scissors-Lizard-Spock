package it.unibo.games.rpsls.interfaces;

import java.util.UUID;

public interface IConnectorEntity {

	public void setId(UUID id);
	public void setId(String uuid);
	public UUID getId();
	public String getIdToString();
	
}
