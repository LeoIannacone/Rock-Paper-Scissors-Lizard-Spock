package it.unibo.games.rpsls.client.gui;

import it.unibo.games.rpsls.connector.client.SIBConnector;
import it.unibo.games.rpsls.connector.SIBFactory;
import it.unibo.games.rpsls.game.Game;
import it.unibo.games.rpsls.game.Player;
import it.unibo.games.rpsls.interfaces.IGame;
import it.unibo.games.rpsls.interfaces.ICommand;
import it.unibo.games.rpsls.interfaces.IPlayer;
import it.unibo.games.rpsls.interfaces.client.IObserver;
import it.unibo.games.rpsls.utils.Debug;

import java.awt.Color;
import java.awt.EventQueue;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.util.List;

import javax.swing.JFrame;
import javax.swing.JPanel;

public class ClientMainWindow implements IObserver {

	public static Color LIGHT_COLOR = Color.GRAY;
	
	
	private JFrame frame;
	
	private ViewWelcome viewWelcome;
	private ViewMatch viewMatch;
	private ViewJoinGame viewJoinGame;
	private ViewWin viewWin;
	private ViewConfigConnector viewConfig;
	
	private SIBConnector connector;
	
	private IPlayer me;
	private IPlayer enemy;
	private IGame current_game;
	
	// ME_FILENAME as file in user home directory
	private String ME_FILENAME = new File(System.getProperty("user.home"), ".rpls.player.info").toString();
	
	public int MAX_RESULT = 3;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					Debug.setLevel(2);
					ClientMainWindow window = new ClientMainWindow();
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
	public ClientMainWindow() {		
		initialize();
	}
	
	private void initialize_me() {
		try {
			InputStreamReader f = new InputStreamReader(new FileInputStream(ME_FILENAME));
			BufferedReader r = new BufferedReader(f);
			String line = r.readLine();
			String[] info = line.split(" ");
			me = new Player(info[0]);
			me.setURI(info[1]);
			r.close();
			me = SIBFactory.getInstance().getPlayer(me.getURIToString());
		} catch (Exception e) {
			me = null;
		}
	}
	
	private void set_me(IPlayer p) {
		if (p == null)
			return;
		if (me == null || ! me.getName().equals(p.getName())) {
			me = p;
			store_me();
		}
	}
	
	private void store_me() {
		try {
			PrintWriter o = new PrintWriter(ME_FILENAME);
			String info = me.getName() + " " +  me.getURIToString();
			o.println(info);
			o.close();
			connector.createNewPlayer(me);
		} catch (Exception e) {
		}
		
	}

	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize() {
		frame = new JFrame();
		frame.setBounds(100, 100, 450, 300);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.addWindowListener(new WindowAdapter() {
			public void windowClosing(WindowEvent e) {
				Debug.print(1, "Closing window");
				if (current_game != null) {
//					connector.endGame(current_game);
					Debug.print(2, this.getClass().getCanonicalName() + ": Close all subscription");
					connector.unwatchAll();
					Debug.print(1, "Leaving game: " + current_game.getURIToString());
					connector.leaveGame(current_game, me);
				}
			}
		});
		frame.setTitle("RPSLS");
		frame.setResizable(false);
		frame.setSize(315, 450);
			
		showViewConfigConnector();
	}
	
	public void init() {
		connector = SIBConnector.getInstance();
		initialize_me();
		showViewWelcome();
	}
	
	public void showViewConfigConnector() {
		viewConfig = new ViewConfigConnector();
		viewConfig.setMainWindow(this);
		showView(viewConfig);
	}
	
	public void showViewWelcome() {
		viewWelcome = new ViewWelcome(me);
		viewWelcome.setMainWindow(this);
		connector.unwatchForWaitingGames();
		connector.unwatchForIncomingPlayer();
		showView(viewWelcome);
	}
	
	public void showViewWin() {
		viewWin = new ViewWin(current_game);
		viewWin.setMainWindow(this);
		showView(viewWin);
		connector.endGame(current_game);
		connector.unwatchAll();
	}
	
	
	public void showViewMatch() {
		connector.watchForHit(current_game, current_game.getOpponent(), this);
		connector.watchForGameEnded(current_game, this);
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
		connector.leaveGame(current_game, me);
//		connector.deleteGame(current_game);
//		current_game = null;
	}
	
	public IGame createNewGame(IPlayer player) {
		set_me(player);
		current_game = new Game(me, null);
		current_game.setScore("0-0");
		connector.createNewGame(current_game);
		connector.watchForIncomingPlayer(current_game, this);
		return current_game;
	}
	
	public void joinGame(IGame game) {
		set_me(me);
		current_game = game;
		current_game.setGuestPlayer(me);
		current_game.setHomeAsOpponent(true);
		current_game.setScore("0-0");
		enemy = game.getHomePlayer();
		connector.joinGame(current_game, me);
		viewMatch = new ViewMatch(current_game, false);
		viewMatch.setMainWindow(this);
		showViewMatch();
	}
	
	public void showJoinGames(IPlayer player) {
		set_me(player);
		viewJoinGame = new ViewJoinGame();
		viewJoinGame.setMainWindow(this);
		viewJoinGame.reset();
		showView(viewJoinGame);
		connector.watchForWaitingGames(this);
	}
	
	public void sendHit(ICommand hit) {
		connector.sendHit(current_game, me, hit);
	}

	@Override
	public void updateWaitingGames(List<IGame> games) {
		viewJoinGame.appendWaitingGames(games);
	}

	@Override
	public void updateWaitingGames(IGame game) {
		viewJoinGame.appendWaitingGames(game);		
	}

	@Override
	public void updateHit(ICommand hit) {
		viewMatch.receivedEnemyHit(hit);
	}

	@Override
	public void updateIncomingPlayer(IGame game) {
		current_game.setGuestPlayer(game.getGuestPlayer());
		enemy = current_game.getGuestPlayer();
		viewMatch = new ViewMatch(current_game, true);
		viewMatch.setMainWindow(this);
		showViewMatch();
	}

	@Override
	public void udpateGameEnded(IGame game) {
//		if (game.getURI().equals(current_game.getURI())) {
			showViewWin();
			if (!(current_game.getHomeScore() >= 3 || current_game.getGuestScore() >= 3))
				viewWin.setTitle("Enemy leaved");
//		}
	}
}
