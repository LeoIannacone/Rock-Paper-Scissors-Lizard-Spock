package it.unibo.games.rpsls.client.interfaces;

import java.util.UUID;

public interface IConnectorEntity {

	public void setURI(UUID id);
	public void setURI(String uuid);
	public UUID getURI();
	public String getURIToString();
	
}
