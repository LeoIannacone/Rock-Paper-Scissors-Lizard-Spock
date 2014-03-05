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
	public void setId(String uuid) {
		String prefix = this.getClass().getSimpleName() + "_";
		if(uuid.contains(prefix)) {
			uuid = uuid.split(prefix)[1];
		}
		this.id = UUID.fromString(uuid);
	}
	
	@Override
	public UUID getId() {
		if (id == null)
			id = UUID.randomUUID();
		return id;
	}

	@Override
	public String getIdToString() {
		return this.getClass().getSimpleName()  + "_" + getId().toString();
	}

}
