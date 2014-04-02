package it.unibo.games.rpsls.admin.gui;

import java.awt.Color;
import java.awt.Font;

import javax.swing.JLabel;
import javax.swing.JPanel;

public class PanelTitleAdmin extends JPanel {

	public PanelTitleAdmin() {
		super();
		JLabel gameName = new JLabel("RPSLS ");
		gameName.setFont(new Font(gameName.getFont().getName(), Font.PLAIN, 40));
		gameName.setAlignmentY(BOTTOM_ALIGNMENT);
		this.add(gameName);
		
		JLabel adminLabel = new JLabel("Admin Panel");
		adminLabel.setFont(new Font(adminLabel.getFont().getName(), Font.PLAIN, 20));
		adminLabel.setAlignmentY(BOTTOM_ALIGNMENT);
		adminLabel.setForeground(Color.GRAY);
		this.add(adminLabel);
	}

}
