package it.unibo.games.rpsls.gui;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import it.unibo.games.rpsls.interfaces.IGame;
import it.unibo.games.rpsls.interfaces.IPlayer;

import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;

public class ViewWin extends ViewDefault implements ActionListener {
	
	private IGame game;

	public ViewWin(IGame game) {
		this.game = game;
		this.setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
		initialize();
	}
	
	private void initialize() {
		JPanel p1 = new JPanel();
		p1.setLayout(new BoxLayout(p1, BoxLayout.Y_AXIS));
		JLabel win = new JLabel();
		IPlayer me = game.getMe();
		IPlayer enemy = game.getOpponent();
		String label = "You WIN";
		win.setForeground(Color.GREEN);
		if (me.getScore() < enemy.getScore()) {
			win.setForeground(Color.RED);
			label = "You loose";
		}
		
		win.setAlignmentX(CENTER_ALIGNMENT);
		win.setAlignmentY(CENTER_ALIGNMENT);
		win.setText(label);
		win.setFont(new Font(win.getFont().getName(), Font.PLAIN, 36));
		p1.add(Box.createRigidArea(new Dimension(0, 60)));
		p1.add(win);
		p1.add(Box.createRigidArea(new Dimension(0, 40)));
		
		JPanel p2 = new JPanel(new FlowLayout());
		PanelScore scoreMe = new PanelScore(me);
		PanelScore scoreEnemy = new PanelScore(enemy);
		p2.add(scoreMe);
		p2.add(Box.createRigidArea(new Dimension(60, 0)));
		p2.add(scoreEnemy);
		
		JPanel p3 = new JPanel(new FlowLayout());
		JButton back = new JButton("Back");
		back.addActionListener(this);
		p3.add(back);
		
		this.add(p1);
		this.add(p2);
		this.add(p3);
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		mainWindow.showViewWelcome();
	}
	
}
