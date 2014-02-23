package it.unibo.games.rpsls.gui;

import java.awt.Dimension;
import java.awt.FlowLayout;
import java.util.ArrayList;

import it.unibo.games.rpsls.game.DefaultValues;
import it.unibo.games.rpsls.game.Hit;
import it.unibo.games.rpsls.game.Player;
import it.unibo.games.rpsls.interfaces.IMatch;

import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.JPanel;

import com.sun.xml.internal.bind.v2.schemagen.xmlschema.List;

public class PanelGame extends JPanel {

	private IMatch match;
	private PanelScore home;
	private PanelScore guest;
	
	private PanelHit homeHit;
	private PanelHit guestHit;
	
	private ArrayList buttonsHit;
	
	public PanelGame(IMatch match) {
		this.match = match;
		setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
		init();
	}
	
	private void init() {
		
		initScorePanel();
		initCurrentHitsPanel();
		initButtonsPanel();
		
	}
	
	private void initScorePanel() {
		JPanel p1 = new JPanel(new FlowLayout());
		
		home = new PanelScore(match.getHomePlayer());
		guest = new PanelScore(match.getGuestPlayer());
		
		p1.add(home);
		p1.add(Box.createRigidArea(new Dimension(120,0)));
		p1.add(guest);
		this.add(p1);
	}
	
	private void initCurrentHitsPanel() {
		JPanel p1 = new JPanel(new FlowLayout());
		
		homeHit = new PanelHit(new Hit(DefaultValues.SPOCK));
		guestHit = new PanelHit();
		
		p1.add(homeHit);
		p1.add(guestHit);
		
		this.add(p1);
	}
	
	private void initButtonsPanel() {
		JPanel p1 = new JPanel(new FlowLayout());
		buttonsHit = new ArrayList<ButtonHit>();
		String[] hits = {DefaultValues.ROCK, DefaultValues.PAPER, DefaultValues.SCISSORS, DefaultValues.LIZARD, DefaultValues.SPOCK};
		for (String h : hits) {
			ButtonHit b = new ButtonHit(new Hit(h));
			buttonsHit.add(b);
			p1.add(b);
		}
		
		this.add(p1);
	}
	
}
