package it.unibo.games.rpsls.connector;

import java.util.UUID;

import it.unibo.games.rpsls.interfaces.IConnectorEntity;

public abstract class ConnectorEntity implements IConnectorEntity {

	protected UUID id;
	
	@Override
	public void setId(UUID id) {
		this.id = id;
	}

	@Override
	public UUID getId() {
		if (id == null)
			id = UUID.randomUUID();
		return id;
	}

	@Override
	public String getIdToString() {
		return this.getClass().getName() + "_" + getId().toString();
	}

}
