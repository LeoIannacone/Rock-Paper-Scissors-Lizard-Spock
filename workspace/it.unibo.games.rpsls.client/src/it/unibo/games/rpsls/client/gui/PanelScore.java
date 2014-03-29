package it.unibo.games.rpsls.client.gui;

import it.unibo.games.rpsls.interfaces.IPlayer;

import java.awt.Component;
import java.awt.Font;
import java.awt.Panel;

import javax.swing.BoxLayout;
import javax.swing.JLabel;

public class PanelScore extends Panel {

	IPlayer player;
	JLabel score;
	JLabel name;
	
	public PanelScore(IPlayer player) {
		super();
		this.player = player;
		setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
		init();
	}
	
	private void init() {
		name = new JLabel(player.getName());
		name.setAlignmentX(Component.CENTER_ALIGNMENT);
		name.setFont(new Font(name.getFont().getName(), Font.PLAIN, 14));
		this.add(name);
		
		score = new JLabel("" + player.getScore());
		score.setAlignmentX(Component.CENTER_ALIGNMENT);
		score.setFont(new Font(score.getFont().getName(), Font.PLAIN, 72));

		this.add(score);
		
	}
	
	public void increaseScore() {
		player.increaseScore();
		score.setText("" + player.getScore());
	}
}
