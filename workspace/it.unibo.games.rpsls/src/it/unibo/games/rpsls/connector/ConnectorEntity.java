package it.unibo.games.rpsls.connector;

import java.util.UUID;

import it.unibo.games.rpsls.interfaces.IConnectorEntity;

public abstract class ConnectorEntity implements IConnectorEntity {

	protected UUID uri;
	
	@Override
	public void setURI(UUID id) {
		this.uri = id;
	}

	@Override
	public void setURI(String uuid) {
		String prefix = this.getClass().getSimpleName() + "_";
		if(uuid.contains(prefix)) {
			uuid = uuid.split(prefix)[1];
		}
		this.uri = UUID.fromString(uuid);
	}
	
	@Override
	public UUID getURI() {
		if (uri == null)
			uri = UUID.randomUUID();
		return uri;
	}

	@Override
	public String getURIToString() {
		return this.getClass().getSimpleName()  + "_" + getURI().toString();
	}

}
