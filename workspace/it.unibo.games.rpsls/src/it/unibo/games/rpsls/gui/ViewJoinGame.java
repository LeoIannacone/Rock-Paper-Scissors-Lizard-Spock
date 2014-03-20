package it.unibo.games.rpsls.gui;

import it.unibo.games.rpsls.game.Game;
import it.unibo.games.rpsls.interfaces.IGame;
import it.unibo.games.rpsls.utils.Debug;

import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.util.HashMap;
import java.util.List;
import java.util.Vector;

import javax.swing.BorderFactory;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JList;
import javax.swing.JPanel;
import javax.swing.JScrollPane;

public class ViewJoinGame extends ViewDefault implements ActionListener, MouseListener {

	private HashMap<String, IGame> matches;
	private JList<String> list;
	Vector<String> listData;
	private JButton join;
	private JButton back;
	
	public ViewJoinGame() {
		super();
		this.setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
		init();
	}

	public void appendWaitingGames(List<IGame> games) {
		for (IGame g : games) {
			this.appendWaitingGames(g);
		}
	}

	public void appendWaitingGames(IGame game) {
		String home = game.getHomePlayer().getName();
		if (game.getStatus().equals(Game.WAITING)) {
			Debug.print(1, this.getClass().getCanonicalName() + ":appendWaitingGames: new waiting game: " + game.getHomePlayer().getName());
			this.matches.put(home, game);
			this.listData.add(home);
		} 
		else {
			Debug.print(1, this.getClass().getCanonicalName() + ":appendWaitingGames: removing game: " + game.getHomePlayer().getName());
			if (this.matches.containsKey(home)) {
				this.matches.remove(home);
				this.listData.remove(home);
			}
		}
		list.setListData(listData);
	}
	
	public void reset() {
		this.listData = new Vector<String>();
		this.matches = new HashMap<String, IGame>();
	}
	
	private void init() {
		
		this.listData = new Vector<String>();
		this.matches = new HashMap<String, IGame>();

		this.setBorder(BorderFactory.createEmptyBorder(10,10,10,10));
		
		PanelTitle title = new PanelTitle();
		this.add(title);
		
		list = new JList<String>();
		list.addMouseListener(this);
		JScrollPane pL = new JScrollPane(list);
		this.add(pL);
		
		JPanel pB = new JPanel(new FlowLayout());
		join = new JButton("Join");
		join.addActionListener(this);
		back = new JButton("Back");
		back.addActionListener(this);
		pB.add(back);
		pB.add(join);
		
		this.add(pB);
	}
	
	private void joinGame() {
		String matchName = list.getSelectedValue();
		if (matchName == null || matchName == "" || matches.get(matchName) == null)
			return;
		IGame m = matches.get(matchName);
		mainWindow.joinGame(m);
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		
		if (e.getSource() == join) {
			joinGame();
		}
		
		else if (e.getSource() == back) {
			mainWindow.showViewWelcome();
		}
		
	}

	@Override
	public void mouseClicked(MouseEvent e) {
		JList list = (JList) e.getSource();
		if(e.getClickCount() >= 2) {
			joinGame();
		}
	}

	@Override
	public void mouseEntered(MouseEvent e) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void mouseExited(MouseEvent e) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void mousePressed(MouseEvent e) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void mouseReleased(MouseEvent e) {
		// TODO Auto-generated method stub
		
	}
}
