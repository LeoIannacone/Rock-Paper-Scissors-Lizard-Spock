package it.unibo.games.rpsls.admin.gui;

import javax.swing.JPanel;

public abstract class ViewDefault extends JPanel {

	protected AdminMainWindow mainWindow;
	
	public void setMainWindow(AdminMainWindow window) {
		this.mainWindow = window;
	}
	
	public AdminMainWindow getMainWindow() {
		return mainWindow;
	}
	
}
