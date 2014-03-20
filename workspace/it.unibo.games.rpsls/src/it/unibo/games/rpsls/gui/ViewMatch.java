package it.unibo.games.rpsls.gui;

import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Font;
import java.util.ArrayList;

import it.unibo.games.rpsls.game.Hit;
import it.unibo.games.rpsls.game.Utils;
import it.unibo.games.rpsls.interfaces.ICommand;
import it.unibo.games.rpsls.interfaces.IGame;

import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.JLabel;
import javax.swing.JPanel;

public class ViewMatch extends ViewDefault {

	private IGame match;
	private PanelScore mePanelScore;
	private PanelScore enemyPanelScore;
	
	private PanelHit mePanelHit;
	private PanelHit enemyPanelHit;
	
	private ButtonHit currentHitButton;
	
	private ArrayList<ButtonHit> buttonsHit;
	
	private JLabel versus;
	private JLabel winnerLabel;
	
	private boolean me_has_home;
	
	public ViewMatch(IGame match, boolean me_has_home) {
		this.match = match;
		setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
		this.me_has_home = me_has_home;
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
		if (me_has_home) {
			mePanelScore = new PanelScore(match.getHomePlayer());
			enemyPanelScore = new PanelScore(match.getGuestPlayer());
		} else {
			mePanelScore = new PanelScore(match.getGuestPlayer());
			enemyPanelScore = new PanelScore(match.getHomePlayer());
		}
		if (me_has_home) {
		p2.add(mePanelScore);
		p2.add(Box.createRigidArea(new Dimension(120,0)));
		p2.add(enemyPanelScore);
		} else {
			p2.add(enemyPanelScore);
			p2.add(Box.createRigidArea(new Dimension(120,0)));
			p2.add(mePanelScore);	
		}
		p1.add(Box.createRigidArea(new Dimension(0,10)));
		p1.add(p2);
		this.add(p1);
	}
	
	private void initCurrentHitsPanel() {
		JPanel p1 = new JPanel();
		p1.setLayout(new BoxLayout(p1, BoxLayout.X_AXIS));
		mePanelHit = new PanelHit();
		enemyPanelHit = new PanelHit();
		
		JPanel p2 = new JPanel();
		p2.setLayout(new BoxLayout(p2, BoxLayout.Y_AXIS));
		p2.add(Box.createRigidArea(new Dimension(55,0)));
		versus = new JLabel();
		versus.setFont(new Font(versus.getFont().getName(), Font.BOLD, 40));
		versus.setForeground(MainWindow.LIGHT_COLOR);
		versus.setAlignmentX(CENTER_ALIGNMENT);
		versus.setAlignmentY(CENTER_ALIGNMENT);
		p2.add(Box.createRigidArea(new Dimension(0,0)));
		p2.add(versus);
		p2.add(Box.createRigidArea(new Dimension(0,22)));
		winnerLabel = new JLabel();
		winnerLabel.setAlignmentX(CENTER_ALIGNMENT);
		winnerLabel.setAlignmentY(CENTER_ALIGNMENT);
		winnerLabel.setForeground(MainWindow.LIGHT_COLOR);
		p2.add(winnerLabel);
		

		if (me_has_home) {
			p1.add(mePanelHit);
			p1.add(p2);
			p1.add(enemyPanelHit);
		} else {
			p1.add(enemyPanelHit);
			p1.add(p2);
			p1.add(mePanelHit);
		}
		this.add(p1);
	}
	
	private void initButtonsPanel() {
		JPanel p1 = new JPanel(new FlowLayout());
		buttonsHit = new ArrayList<ButtonHit>();
		String[] hits = {Hit.ROCK, Hit.PAPER, Hit.SCISSORS, Hit.LIZARD, Hit.SPOCK};
		for (String h : hits) {
			Hit hit = new Hit(h);
			if(me_has_home)
				hit.setIssuer(match.getHomePlayer());
			else
				hit.setIssuer(match.getGuestPlayer());
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
				mePanelHit.setHit(b.getHit());
			}
			else {
				b.setEnabled(false);
			}
		}
		mainWindow.sendHit(clicked.getHit());
	}
	
	public void clean() {
		currentHitButton = null;
		
		for (ButtonHit b: buttonsHit)
			b.setEnabled(true);
		mePanelHit.clean();
		enemyPanelHit.clean();
		versus.setText("");
		winnerLabel.setText("");
	}
	
	public void receivedEnemyHit(ICommand hit) {
		enemyPanelHit.setHit(hit);
		if (currentHitButton != null)
			showLabelWinning();
		CleanerThread c = new CleanerThread(this);
		c.start();
	}
	
	private void showLabelWinning() {
		if (enemyPanelHit == null || currentHitButton == null)
			return;
		try{
			String[] info = Utils.compareHits(currentHitButton.getHit(), enemyPanelHit.getHit());
			int i = Integer.parseInt(info[0]);
			
			String ltr = "<html>&rarr;</html>"; // →
			String rtl = "<html>&larr;</html>"; // ←

			if (i != 0) {
				winnerLabel.setText(info[1]);
			}
			if (i > 0) {
				if (me_has_home) versus.setText(ltr); else versus.setText(rtl);
				mePanelScore.increaseScore();
			}
			else if (i == 0) versus.setText("=");
			else {
				if (me_has_home) versus.setText(rtl); else versus.setText(ltr);
				enemyPanelScore.increaseScore();
			}
		}catch (Exception e){
			e.printStackTrace();
			System.exit(1);
		}
	}
}


class CleanerThread extends Thread {
	private ViewMatch m;
	public CleanerThread(ViewMatch m) {
		this.m = m;
	}
	public void run() {
		try {
			Thread.sleep(3000);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}		
		m.clean();
	}
}