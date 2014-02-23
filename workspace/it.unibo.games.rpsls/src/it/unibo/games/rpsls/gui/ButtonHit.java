package it.unibo.games.rpsls.gui;

import java.awt.Color;
import java.awt.Component;
import java.awt.Image;
import java.awt.image.BufferedImage;
import java.io.IOException;

import it.unibo.games.rpsls.interfaces.IHit;

import javax.imageio.ImageIO;
import javax.swing.BoxLayout;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JLabel;

public class ButtonHit extends JButton {

	private IHit hit;
	
	public ButtonHit(IHit hit) {
		super();
		this.hit = hit;
		setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
		init();
	}
	
	public IHit getHit() {
		return hit;
	}
	
	private void init() {
		try {
			BufferedImage image = ImageIO.read(Utils.getHitButtonIcon(hit));
			JLabel i = new JLabel(new ImageIcon(image));
			i.setAlignmentX(Component.CENTER_ALIGNMENT);
			this.add(i);
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		JLabel l = new JLabel(hit.getName());
		l.setAlignmentX(Component.CENTER_ALIGNMENT);
		this.add(l);
	}
	
}
