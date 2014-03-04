package it.unibo.games.rpsls.gui;

import java.awt.Font;

import javax.swing.JLabel;
import javax.swing.JPanel;

public class PanelTitle extends JPanel {

	public PanelTitle() {
		super();
		JLabel gameName = new JLabel("RPSLS");
		gameName.setFont(new Font(gameName.getFont().getName(), Font.PLAIN, 40));
		this.add(gameName);
	}
}
