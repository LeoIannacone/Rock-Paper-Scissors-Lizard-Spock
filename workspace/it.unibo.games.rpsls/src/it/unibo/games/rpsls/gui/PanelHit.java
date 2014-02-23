package it.unibo.games.rpsls.gui;

import java.awt.Component;
import java.awt.Dimension;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import it.unibo.games.rpsls.interfaces.IHit;

import javax.imageio.ImageIO;
import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.ImageIcon;
import javax.swing.JLabel;
import javax.swing.JPanel;

public class PanelHit extends JPanel {

	private IHit hit;
	private JLabel icon;
	private JLabel name;
	
	public PanelHit(IHit hit) {
		super();
		init();
		setHit(hit);
	}
	
	public PanelHit() {
		super();
		init();
		clean();
	}
	
	private void init() {
		JPanel p1 = new JPanel();
		p1.setLayout(new BoxLayout(p1, BoxLayout.Y_AXIS));
		icon = new JLabel();
		name = new JLabel();
		icon.setAlignmentX(Component.CENTER_ALIGNMENT);
		name.setAlignmentX(Component.CENTER_ALIGNMENT);
		p1.add(icon);
		p1.add(Box.createRigidArea(new Dimension(0,10)));
		p1.add(name);
		
		this.add(p1);
	}
	
	public void setHit(IHit hit) {
		this.hit = hit;
		File icon = Utils.getHitPanelIcon(hit);
		String name = hit.getName();
		setIconAndLabel(icon, name);
	}
	
	public void clean() {
		this.hit = null;
		File icon = Utils.getHitPanelIconBlank();
		String name = " ";
		setIconAndLabel(icon, name);
	}
	
	private void setIconAndLabel(File icon, String name) {
		BufferedImage image;
		try {
			image = ImageIO.read(icon);
			this.icon.setIcon(new ImageIcon(image));
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		this.name.setText(Utils.capitalize(name));
	}
}
