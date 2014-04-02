package it.unibo.games.rpsls.admin.gui;

import javax.swing.JPanel;

public abstract class ViewDefault extends JPanel {

	protected MainWindow mainWindow;
	
	public void setMainWindow(MainWindow window) {
		this.mainWindow = window;
	}
	
	public MainWindow getMainWindow() {
		return mainWindow;
	}
	
}
