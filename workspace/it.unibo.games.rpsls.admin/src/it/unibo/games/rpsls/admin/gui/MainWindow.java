package it.unibo.games.rpsls.admin.gui;

import it.unibo.games.rpsls.admin.interfaces.IAdminConnector;
import it.unibo.games.rpsls.admin.interfaces.IAdminObserver;
import it.unibo.games.rpsls.interfaces.IGame;
import it.unibo.games.rpsls.utils.Debug;

import java.awt.Color;
import java.awt.EventQueue;
import java.util.List;

import javax.swing.JFrame;

public class MainWindow implements IAdminObserver {

	public static Color LIGHT_COLOR = Color.GRAY;
	
	
	private JFrame frame;
	
	private IAdminConnector connector;
	
	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					Debug.setLevel(2);
					MainWindow window = new MainWindow();
					window.frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the application.
	 */
	public MainWindow() {		
		initialize();
	}
	
	private void initialize() {
		
	}

	@Override
	public void updateGamesEnded(List<IGame> games) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void updateGameEnded(IGame games) {
		// TODO Auto-generated method stub
		
	}
	
}
