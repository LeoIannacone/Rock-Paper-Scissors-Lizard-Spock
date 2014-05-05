package it.unibo.games.rpsls.admin.gui;

import it.unibo.games.rpsls.interfaces.IGame;
import it.unibo.games.rpsls.utils.Debug;

import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.HashMap;
import java.util.List;
import java.util.Vector;

import javax.swing.BorderFactory;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.ListSelectionModel;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;

public class ViewMain extends ViewDefault implements ActionListener, ListSelectionListener {

	private HashMap<String, IGame> games;
	private JList<String> list;
	Vector<String> listData;

	private JButton init;
	private JButton clean;
	private JButton reset;
	private JButton deleteGame;
	
	public ViewMain() {
		super();
		this.setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
		init();
	}
		
	public void appendEndedGames(List<IGame> games) {
		for (IGame g : games) {
			this.appendEndedGame(g);
		}
	}

	public void appendEndedGame(IGame game) {
		try {
			String id = game.getURIToString();
			
			if (!this.games.containsKey(id)) {
				Debug.print(1, this.getClass().getCanonicalName() + ":appendEndedGames: new ended game: " + id);
				this.games.put(id, game);
				this.listData.add(id);
				list.setListData(listData);
			} 
			
		} catch (Exception e) {}
	}
	
	private void init() {
		
		this.listData = new Vector<String>();
		this.games = new HashMap<String, IGame>();

		this.setBorder(BorderFactory.createEmptyBorder(10,10,10,10));
		
		PanelTitleAdmin title = new PanelTitleAdmin();
		this.add(title);
		
		JPanel main = new JPanel();
		main.setLayout(new BoxLayout(main, BoxLayout.X_AXIS));
		main.setAlignmentY(TOP_ALIGNMENT);
		list = new JList<String>();
		list.setSelectionMode(ListSelectionModel.MULTIPLE_INTERVAL_SELECTION);
		list.addListSelectionListener(this);
//		list.addMouseListener(this);
		JScrollPane pL = new JScrollPane(list);
		JPanel p1 = new JPanel();
		deleteGame = new JButton("Delete");
		deleteGame.setEnabled(false);
		
		p1.add(deleteGame);
		main.add(pL);
		main.add(p1);	
		
		JPanel pB = new JPanel(new FlowLayout());
		pB.setLayout(new BoxLayout(pB, BoxLayout.Y_AXIS));
		pB.setSize(10, 10);
		
		JPanel pB1 = new JPanel(new FlowLayout());
		JLabel adminButtons = new JLabel("SIB Actions");
		init = new JButton("Init");
		reset = new JButton("Reset");
		clean = new JButton("Clean");

		pB1.add(adminButtons);
		pB1.add(init);
		pB1.add(clean);
		pB1.add(reset);
		adminButtons.setAlignmentY(BOTTOM_ALIGNMENT);
			
		pB.add(pB1);
		
		this.add(main);
		this.add(pB);
		
		deleteGame.addActionListener(this);
		init.addActionListener(this);
		reset.addActionListener(this);
		clean.addActionListener(this);
		
	}
	
	private boolean showDialog(String label) {
		int dialogButton = JOptionPane.YES_NO_OPTION;
		int dialogResult = JOptionPane.showConfirmDialog(this, label, "Confirm action", dialogButton, JOptionPane.PLAIN_MESSAGE);
		return dialogResult == 0;
	}

	@Override
	public void actionPerformed(ActionEvent arg0) {
		JButton source = (JButton) arg0.getSource();
		
		if (source == deleteGame) {
			int games_num = list.getSelectedValuesList().size();
			if (games_num > 0 && showDialog("You are going to remove " + games_num + " games from SIB. Are you sure about that?"))
				deleteGame();
		}

		else if (source == init && showDialog("You are going to inject ontology into SIB. Are you sure about that?"))
			mainWindow.connector.init();
		else if (source == reset && showDialog("You are going to remove everything from SIB. Are you sure about that?")){
			mainWindow.connector.reset();
			games.clear();
			list.removeAll();
			listData.removeAllElements();
		}
		else if (source == clean && showDialog("You are going to remove every RPSLS data from SIB. Are you sure about that?")){
			mainWindow.connector.clean();
			games.clear();
			list.removeAll();
			listData.removeAllElements();
		}
	}

	@Override
	public void valueChanged(ListSelectionEvent arg0) {
		int games_num = list.getSelectedValuesList().size();
		deleteGame.setEnabled(games_num > 0);
	}
	
	
	private void deleteGame() {
		List<String> selected = list.getSelectedValuesList();
		
		for (String sel : selected) {
			if (! games.containsKey(sel))
				continue;
			mainWindow.connector.deleteGame(games.get(sel));
		}
	}

	public void deleteGameEnded(IGame game) {
		try {
			String id = game.getURIToString();
			
			if (this.games.containsKey(id)) {
				Debug.print(1, this.getClass().getCanonicalName() + ":appendEndedGames: removing game from gui: " + id);
				this.games.remove(id);
				this.listData.remove(id);
				list.setListData(listData);
			}
		}catch(Exception e){
		}
	}

	public void deleteGamesEnded(List<IGame> games) {
		for (IGame g : games) {
			this.deleteGameEnded(g);
		}
	}

}
