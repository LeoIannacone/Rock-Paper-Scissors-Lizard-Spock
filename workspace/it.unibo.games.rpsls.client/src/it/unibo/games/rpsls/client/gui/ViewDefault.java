package it.unibo.games.rpsls.client.gui;

import javax.swing.JPanel;

public abstract class ViewDefault extends JPanel {

	protected ClientMainWindow mainWindow;
	
	public void setMainWindow(ClientMainWindow window) {
		this.mainWindow = window;
	}
	
	public ClientMainWindow getMainWindow() {
		return mainWindow;
	}
	
}
