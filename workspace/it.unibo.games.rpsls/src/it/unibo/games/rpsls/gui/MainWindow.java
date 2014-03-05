package it.unibo.games.rpsls.gui;

import it.unibo.games.rpsls.connector.SIBConnector;
import it.unibo.games.rpsls.game.Game;
import it.unibo.games.rpsls.game.Player;
import it.unibo.games.rpsls.interfaces.IConnector;

import java.awt.Color;
import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;

public class MainWindow {

	public static Color LIGHT_COLOR = Color.GRAY;
	
	
	private JFrame frame;
	
	private ViewWelcome viewWelcome;
	private ViewMatch viewMatch;
	private ViewJoinGame viewJoinGame;
	
	private IConnector connector;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
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
		connector = SIBConnector.getInstance();
	}

	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize() {
		frame = new JFrame();
		frame.setBounds(100, 100, 450, 300);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setTitle("RPSLS");
		frame.setResizable(false);
		frame.setSize(315, 450);
		
		viewWelcome = new ViewWelcome();
		viewWelcome.setMainWindow(this);
		
		Game m = new Game(new Player("Leo"), new Player("Carlo"));
		viewMatch = new ViewMatch(m);
		viewMatch.setMainWindow(this);
		
		Game[] matches = new Game[3];
		matches[0] = new Game(new Player("Jonh"), null);
		matches[1] = new Game(new Player("Paul"), null);
		matches[2] = new Game(new Player("Ringo"), null);
		viewJoinGame = new ViewJoinGame();
		viewJoinGame.setMatches(matches);
		viewJoinGame.setMainWindow(this);
		
//		showViewWelcome();
		showViewMatch();
	}
	
	public void showViewWelcome() {
		showView(viewWelcome);
	}
	
	public void showViewMatch() {
		showView(viewMatch);
	}
	
	public void showJoinGame() {
		showView(viewJoinGame);
	}
	
	private void showView(JPanel noHide) {
		frame.getContentPane().removeAll();
		frame.getContentPane().add(noHide);
		
		frame.getContentPane().repaint();
		frame.getContentPane().revalidate();
	
		noHide.repaint();
		noHide.revalidate();
	}
	
}
