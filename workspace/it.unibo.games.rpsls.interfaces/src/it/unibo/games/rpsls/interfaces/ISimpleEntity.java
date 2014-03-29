package it.unibo.games.rpsls.interfaces;

import java.util.UUID;

public interface ISimpleEntity {

	public void setURI(UUID id);
	public void setURI(String uuid);
	public UUID getURI();
	public String getURIToString();
	
}
