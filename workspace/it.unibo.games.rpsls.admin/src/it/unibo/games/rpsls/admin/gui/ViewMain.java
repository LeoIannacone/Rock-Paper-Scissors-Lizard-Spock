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
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.ListSelectionModel;

public class ViewMain extends ViewDefault implements ActionListener {

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
			
			if (this.games.containsKey(id)) {
				Debug.print(1, this.getClass().getCanonicalName() + ":appendEndedGames: removing game from gui: " + id);
				this.games.remove(id);
				this.listData.remove(id);
			}
			else{
				Debug.print(1, this.getClass().getCanonicalName() + ":appendEndedGames: new ended game: " + id);
				this.games.put(id, game);
				this.listData.add(id);
			} 
			
			list.setListData(listData);
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
//		list.addMouseListener(this);
		JScrollPane pL = new JScrollPane(list);
		JPanel p1 = new JPanel();
		deleteGame = new JButton("Delete");

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
		pB1.add(reset);
		pB1.add(clean);
		adminButtons.setAlignmentY(BOTTOM_ALIGNMENT);
			
		pB.add(pB1);
		
		this.add(main);
		this.add(pB);
		
		deleteGame.addActionListener(this);
		init.addActionListener(this);
		reset.addActionListener(this);
		clean.addActionListener(this);
		
	}

	@Override
	public void actionPerformed(ActionEvent arg0) {
		JButton source = (JButton) arg0.getSource();
		
		if (source == deleteGame)
			deleteGame();
		else if (source == init)
			mainWindow.connector.init();
		else if (source == reset)
			mainWindow.connector.reset();
		else if (source == clean)
			mainWindow.connector.clean();
	}
	
	
	private void deleteGame() {
		List<String> selected = list.getSelectedValuesList();
		
		for (String sel : selected) {
			if (! games.containsKey(sel))
				continue;
			mainWindow.connector.deleteGame(games.get(sel));
		}
	}
	
}
