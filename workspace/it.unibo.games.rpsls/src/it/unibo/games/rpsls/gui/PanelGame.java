package it.unibo.games.rpsls.gui;

import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Font;
import java.util.ArrayList;

import it.unibo.games.rpsls.game.DefaultValues;
import it.unibo.games.rpsls.game.Hit;
import it.unibo.games.rpsls.game.Player;
import it.unibo.games.rpsls.game.Utils;
import it.unibo.games.rpsls.interfaces.IHit;
import it.unibo.games.rpsls.interfaces.IMatch;

import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.JLabel;
import javax.swing.JLayeredPane;
import javax.swing.JPanel;

import com.sun.xml.internal.bind.v2.WellKnownNamespace;


public class PanelGame extends JPanel {

	private IMatch match;
	private PanelScore homePanelScore;
	private PanelScore guestPanelScore;
	
	private PanelHit homePanelHit;
	private PanelHit guestPanelHit;
	
	private ButtonHit currentHitButton;
	
	private ArrayList<ButtonHit> buttonsHit;
	
	private JLabel versus;
	private JLabel winnerLabel;
	
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
		JPanel p1 = new JPanel();
		p1.setLayout(new BoxLayout(p1, BoxLayout.Y_AXIS));
		
		JPanel p2 = new JPanel(new FlowLayout());
		homePanelScore = new PanelScore(match.getHomePlayer());
		guestPanelScore = new PanelScore(match.getGuestPlayer());
		
		p2.add(homePanelScore);
		p2.add(Box.createRigidArea(new Dimension(120,0)));
		p2.add(guestPanelScore);
		
		p1.add(Box.createRigidArea(new Dimension(0,10)));
		p1.add(p2);
		this.add(p1);
	}
	
	private void initCurrentHitsPanel() {
		JPanel p1 = new JPanel();
		p1.setLayout(new BoxLayout(p1, BoxLayout.X_AXIS));
		homePanelHit = new PanelHit();
		guestPanelHit = new PanelHit();
		
		JPanel p2 = new JPanel();
		p2.setLayout(new BoxLayout(p2, BoxLayout.Y_AXIS));
		p2.add(Box.createRigidArea(new Dimension(55,0)));
		versus = new JLabel();
		versus.setFont(new Font(versus.getFont().getName(), Font.BOLD, 40));
		versus.setForeground(PlayerGui.LIGHT_COLOR);
		versus.setAlignmentX(CENTER_ALIGNMENT);
		versus.setAlignmentY(CENTER_ALIGNMENT);
		p2.add(Box.createRigidArea(new Dimension(0,0)));
		p2.add(versus);
		p2.add(Box.createRigidArea(new Dimension(0,22)));
		winnerLabel = new JLabel();
		winnerLabel.setAlignmentX(CENTER_ALIGNMENT);
		winnerLabel.setAlignmentY(CENTER_ALIGNMENT);
		winnerLabel.setForeground(PlayerGui.LIGHT_COLOR);
		p2.add(winnerLabel);
		

		p1.add(homePanelHit);
		p1.add(p2);
		p1.add(guestPanelHit);
		
		this.add(p1);
	}
	
	private void initButtonsPanel() {
		JPanel p1 = new JPanel(new FlowLayout());
		buttonsHit = new ArrayList<ButtonHit>();
		String[] hits = {DefaultValues.ROCK, DefaultValues.PAPER, DefaultValues.SCISSORS, DefaultValues.LIZARD, DefaultValues.SPOCK};
		for (String h : hits) {
			ButtonHit b = new ButtonHit(new Hit(h));
			b.setPanelGame(this);
			buttonsHit.add(b);
			p1.add(b);
		}
		
		this.add(p1);
	}
	
	public void clickedButtonHit(ButtonHit clicked) {
		if (currentHitButton == clicked)
			return;
		for (ButtonHit b : buttonsHit) {
			if (b.equals(clicked)) {
				currentHitButton = clicked;
				b.getModel().setSelected(true);
				homePanelHit.setHit(b.getHit());
			}
			else {
				b.setEnabled(false);
			}
		}
	}
	
	public void clean() {
		currentHitButton = null;
		
		for (ButtonHit b: buttonsHit)
			b.setEnabled(true);
		homePanelHit.clean();
		guestPanelHit.clean();
		versus.setText("");
		winnerLabel.setText("");
	}
	
	public void receivedGuestHit(IHit hit) {
		guestPanelHit.setHit(hit);
		if (currentHitButton != null)
			showLabelWinning();
	}
	
	private void showLabelWinning() {
		if (guestPanelHit == null || currentHitButton == null)
			return;
		String[] info = Utils.compareHits(currentHitButton.getHit(), guestPanelHit.getHit());
		
		int i = Integer.parseInt(info[0]);
		
		if (i != 0)
			winnerLabel.setText(info[1]);
		
		if (i > 0) versus.setText("→");
		else if (i == 0) versus.setText("=");
		else versus.setText("←");
		
		
	}
	
	
}
