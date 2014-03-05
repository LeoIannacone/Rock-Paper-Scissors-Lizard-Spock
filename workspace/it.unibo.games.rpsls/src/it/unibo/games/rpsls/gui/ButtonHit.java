package it.unibo.games.rpsls.gui;

import java.awt.Component;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;
import java.io.IOException;

import it.unibo.games.rpsls.game.Hit;
import it.unibo.games.rpsls.interfaces.IHit;

import javax.imageio.ImageIO;
import javax.swing.BoxLayout;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JLabel;

public class ButtonHit extends JButton implements ActionListener {

	private IHit hit;
	private ViewMatch panelGame;
	
	public ButtonHit(IHit hit) {
		super();
		this.hit = hit;
		setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
		init();
		addActionListener(this);
	}
	
	public IHit getHit() {
		return hit;
	}
	
	public void setPanelGame(ViewMatch panelGame) {
		this.panelGame = panelGame;
	}
	
	public boolean equals(ButtonHit b) {
		return this.hit.equals(b.getHit());
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
		l.setFont(new Font(l.getFont().getName(), Font.PLAIN, 10));
		this.add(l);
		
		this.setPreferredSize(new Dimension(50,50));
		this.setMargin(new Insets(1, 0, 0, 0));
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		
		if(panelGame == null) 
			return;
		panelGame.clickedButtonHit(this);
		
		// only for test
		TestThread te = new TestThread(panelGame);
		te.start();
	}
	
}

class TestThread extends Thread {
	private ViewMatch p;
	public TestThread (ViewMatch p) {
		this.p = p;
	}

	@Override
	public void run() {
		try {
			Thread.sleep(3000);
			String[] hits = {Hit.ROCK, Hit.PAPER, Hit.SCISSORS, Hit.LIZARD, Hit.SPOCK};
			int i = (int) Math.random() % hits.length;
			p.receivedGuestHit(new Hit(hits[i]));
			Thread.sleep(3000);
			p.clean();
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}
}
