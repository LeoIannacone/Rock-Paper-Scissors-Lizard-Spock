package it.unibo.games.rpsls.gui;

import it.unibo.games.rpsls.connector.SIBConnector;
import it.unibo.games.rpsls.game.Game;
import it.unibo.games.rpsls.game.Player;
import it.unibo.games.rpsls.interfaces.IConnector;
import it.unibo.games.rpsls.interfaces.IGame;
import it.unibo.games.rpsls.interfaces.IPlayer;
import it.unibo.games.rpsls.prototypes.SimpleConnectorPrototype;

import java.awt.Color;
import java.awt.EventQueue;
import java.util.List;

import javax.swing.JFrame;
import javax.swing.JPanel;

public class MainWindow {

	public static Color LIGHT_COLOR = Color.GRAY;
	
	
	private JFrame frame;
	
	private ViewWelcome viewWelcome;
	private ViewMatch viewMatch;
	private ViewJoinGame viewJoinGame;
	
	private IConnector connector;
	
	private IPlayer me;
	private IGame current_game;

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
		connector = SimpleConnectorPrototype.getInstance();
	}
	
	private void set_me(IPlayer p) {
		if (p == null)
			return;
		if (me == null || me.getName() != p.getName()) {
			me = p;
			storeMeFile();
			connector.createNewPlayer(me);
		}
	}
	
	private void storeMeFile() {
		// FIX_ME: creare funzione per registrare info sul giocatore
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
		viewMatch = new ViewMatch(m, true);
		viewMatch.setMainWindow(this);
		
		viewJoinGame = new ViewJoinGame();
		viewJoinGame.setMainWindow(this);
		
		showViewWelcome();
	}
	
	public void showViewWelcome() {
		showView(viewWelcome);
	}
	
	public void showViewMatch() {
		showView(viewMatch);
	}
	
	private void showView(JPanel noHide) {
		frame.getContentPane().removeAll();
		frame.getContentPane().add(noHide);
		
		frame.getContentPane().repaint();
		frame.getContentPane().revalidate();
	
		noHide.repaint();
		noHide.revalidate();
	}
	
	public void deleteGame() {
		
	}
	
	public void createNewGame(IPlayer player) {
		set_me(player);
		current_game = new Game(me, null);
		connector.createNewGame(current_game);
		IPlayer guest = connector.getIncomingPlayer(current_game);
		current_game.setGuestPlayer(guest);
		viewMatch = new ViewMatch(current_game, true);
		viewMatch.setMainWindow(this);
		showView(viewMatch);
	}
	
	public void joinGame(IGame game) {
		current_game = game;
		game.setGuestPlayer(me);
		connector.joinGame(current_game, me);
		viewMatch = new ViewMatch(current_game, false);
		viewMatch.setMainWindow(this);
		showView(viewMatch);
	}
	
	public void showJoinGame(IPlayer player) {
		List<IGame> games = connector.getWaitingGames();
		set_me(player);
		viewJoinGame.setWaitingGames(games);
		showView(viewJoinGame);
	}
}
