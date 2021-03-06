package it.unibo.games.rpsls.client.gui;

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

	public static int SLEEP_TIME = 3000;

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
	
	private ICommand receivedEnemyHit;
	
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
		versus.setForeground(ClientMainWindow.LIGHT_COLOR);
		versus.setAlignmentX(CENTER_ALIGNMENT);
		versus.setAlignmentY(CENTER_ALIGNMENT);
		p2.add(Box.createRigidArea(new Dimension(0,0)));
		p2.add(versus);
		p2.add(Box.createRigidArea(new Dimension(0,22)));
		winnerLabel = new JLabel();
		winnerLabel.setAlignmentX(CENTER_ALIGNMENT);
		winnerLabel.setAlignmentY(CENTER_ALIGNMENT);
		winnerLabel.setForeground(ClientMainWindow.LIGHT_COLOR);
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
			ButtonHit b = new ButtonHit(hit);
			b.setPanelGame(this);
			buttonsHit.add(b);
			p1.add(b);
		}
		
		this.add(p1);
	}
	
	public void clickedButtonHit(ButtonHit clicked) {
		if (currentHitButton != null)
			return;
		for (ButtonHit b : buttonsHit) {
			if (b.equals(clicked)) {
				b.setEnabled(false);
				currentHitButton = clicked;
				b.getModel().setSelected(true);
				mePanelHit.setHit(b.getHit());
			}
			else {
				b.setEnabled(false);
			}
		}
		mainWindow.sendHit(clicked.getHit());
		showEnemyHit();
	}
	
	public void clean() {
		currentHitButton = null;
		receivedEnemyHit = null;
		mePanelHit.clean();
		enemyPanelHit.clean();
		versus.setText("");
		winnerLabel.setText("");
		for (ButtonHit b: buttonsHit)
			b.setEnabled(true);
	}
	
	private void showEnemyHit() {
		if (receivedEnemyHit == null)
			return;
		enemyPanelHit.setHit(receivedEnemyHit);
		showLabelWinning();
	}
	
	public void receivedEnemyHit(ICommand hit) {
		receivedEnemyHit = hit;
		if (currentHitButton != null)
			showEnemyHit();
	}
	
	private void showLabelWinning() {
		if (enemyPanelHit == null || currentHitButton == null)
			return;
		try{
			String[] info = Utils.compareHits(currentHitButton.getHit(), enemyPanelHit.getHit());
			int i = Integer.parseInt(info[0]);
			
			String ltr = "\u2192"; // →
			String rtl = "\u2190"; // ←

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
			if (match.getHomePlayer().getScore() >= mainWindow.MAX_RESULT ||
				match.getGuestPlayer().getScore() >= mainWindow.MAX_RESULT)
			{	// game ended
				EndThread e = new EndThread(mainWindow);
				e.start();
			}
			else {
				// clean panel and go again
				CleanerThread c = new CleanerThread(this);
				c.start();
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
			Thread.sleep(ViewMatch.SLEEP_TIME);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}		
		m.clean();
	}
}

class EndThread extends Thread {
	private ClientMainWindow m;
	public EndThread (ClientMainWindow m) {
		this.m = m;
	}
	public void run() {
		try {
			Thread.sleep(ViewMatch.SLEEP_TIME);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		m.showViewWin();
	}
}
