package it.unibo.games.rpsls.gui;

import it.unibo.games.rpsls.game.Match;
import it.unibo.games.rpsls.game.Player;

import java.awt.Color;
import java.awt.EventQueue;

import javax.swing.JFrame;

public class PlayerGui {

	public static Color LIGHT_COLOR = Color.GRAY;
	
	
	private JFrame frame;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					PlayerGui window = new PlayerGui();
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
	public PlayerGui() {
		initialize();
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
		
		
//		frame.add(new PanelWelcome());
		Match m = new Match(new Player("Leo"), new Player("Carlo"));
		frame.add(new PanelGame(m));
	}

}
