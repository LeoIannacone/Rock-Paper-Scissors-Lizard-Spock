package it.unibo.games.rpsls.connector;

import it.unibo.games.rpsls.interfaces.IConnectorEntity;

public abstract class ConnectorEntity implements IConnectorEntity {

	protected int id;
	
	@Override
	public void setId(int id) {
		this.id = id;
	}

	@Override
	public int getId() {
		return id;
	}

	@Override
	public String getIdToString() {
		return this.getClass().getName() + "_" + id;
	}

}
