package it.unibo.games.rpsls.admin.gui;

import it.unibo.games.rpsls.connector.admin.SIBAdmin;
import it.unibo.games.rpsls.interfaces.IGame;
import it.unibo.games.rpsls.interfaces.admin.IAdminConnector;
import it.unibo.games.rpsls.interfaces.admin.IAdminObserver;
import it.unibo.games.rpsls.utils.Debug;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.EventQueue;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.util.List;

import javax.swing.JFrame;
import javax.swing.JPanel;

public class AdminMainWindow implements IAdminObserver {

	public static Color LIGHT_COLOR = Color.GRAY;
	private JFrame frame;
	private ViewMain mainView;
	private ViewConfigConnector viewConfig;
	
	public IAdminConnector connector;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					Debug.setLevel(0);
					AdminMainWindow window = new AdminMainWindow();
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
	public AdminMainWindow() {
		initialize();
	}

	private void initialize() {
		frame = new JFrame();
		frame.setBounds(100, 100, 450, 300);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.addWindowListener(new WindowAdapter() {
			public void windowClosing(WindowEvent e) {
				Debug.print(1, "Closing window");
				try {
					if (connector != null)
						connector.unwatchForEndingGames();
				} catch (Exception ex) {

				}
			}
		});
		frame.setTitle("RPSLS admin panel");
		frame.setResizable(true);
		frame.setMinimumSize(new Dimension(600, 450));
		
		showViewConfig();
	}

	public void init() {
		connector = SIBAdmin.getInstance();
		showMainVew();
	}
	
	public void showViewConfig() {
		viewConfig = new ViewConfigConnector();
		viewConfig.setMainWindow(this);
		showView(viewConfig);
	}
	
	public void showMainVew() {
		mainView = new ViewMain();
		mainView.setMainWindow(this);
		connector.watchForEndingGames(this);
		showView(mainView);
	}
	
	private void showView(JPanel noHide) {
		frame.getContentPane().removeAll();
		frame.getContentPane().add(noHide);
		
		frame.getContentPane().repaint();
		frame.getContentPane().revalidate();
	
		noHide.repaint();
		noHide.revalidate();
	}

	
	@Override
	public void updateGamesEnded(List<IGame> games) {
		mainView.appendEndedGames(games);
	}

	@Override
	public void updateGameEnded(IGame game) {
		mainView.appendEndedGame(game);
	}

	@Override
	public void deleteGamesEnded(List<IGame> games) {
		mainView.deleteGamesEnded(games);
	}

	@Override
	public void deleteGameEnded(IGame game) {
		mainView.deleteGameEnded(game);		
	}
	
}
