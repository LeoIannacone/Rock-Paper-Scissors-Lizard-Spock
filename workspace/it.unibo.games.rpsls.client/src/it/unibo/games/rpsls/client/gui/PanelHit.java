package it.unibo.games.rpsls.client.gui;

import it.unibo.games.rpsls.interfaces.ICommand;

import java.awt.Component;
import java.awt.Dimension;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;

import javax.imageio.ImageIO;
import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.ImageIcon;
import javax.swing.JLabel;
import javax.swing.JPanel;

public class PanelHit extends JPanel {

	private ICommand hit;
	private JLabel icon;
	private JLabel name;
	
	public PanelHit(ICommand hit) {
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
	
	public void setHit(ICommand hit) {
		if (hit == null) {
			clean();
			return;
		}
		this.hit = hit;
		String icon = Utils.getHitPanelIcon(hit);
		String name = hit.getCommandType();
		setIconAndLabel(icon, name);
	}
	
	public ICommand getHit() {
		return hit;
	}
	
	public void clean() {
		this.hit = null;
		String icon = Utils.getHitPanelIconBlank();
		String name = " ";
		setIconAndLabel(icon, name);
	}
	
	private void setIconAndLabel(String icon, String name) {
		BufferedImage image;
		try {
			image = ImageIO.read(this.getClass().getResource(icon));
			this.icon.setIcon(new ImageIcon(image));
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		this.name.setText(Utils.capitalize(name));
	}
}
