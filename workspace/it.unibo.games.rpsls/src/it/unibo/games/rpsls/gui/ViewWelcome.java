package it.unibo.games.rpsls.gui;

import it.unibo.games.rpsls.game.Player;
import it.unibo.games.rpsls.interfaces.IGame;
import it.unibo.games.rpsls.interfaces.IPlayer;

import javax.imageio.ImageIO;
import javax.swing.*;

import java.awt.Component;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.net.URL;

public class ViewWelcome extends ViewDefault {

	private JPanel panelTop;
	private JPanel panelCenter;
	private JPanel panelBottom;
	
	private JPanel insertName;
	private JLabel nameLabel;
	private JLabel loadingLabel;
	private JTextField name;
	
	private JButton start;
	private JButton stop;
	private JButton join;
	private JLabel loadingDots;
	private JLabel subIDLabel;
	
	private IPlayer me;
	
	public ViewWelcome(IPlayer me) {
		this.me = me;
		setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
		init();
		if (me != null)
			set_me();
	}
	
	private void init() {
		initTopPanel();
		initCenterPanel();
		initBottomPanel();
		
		addListeners();
		turnPanel(false);
		
		this.add(panelTop);
		this.add(panelCenter);
		this.add(panelBottom);
	}

	private void initTopPanel() {
		panelTop = new JPanel(new FlowLayout());
		PanelTitle title = new PanelTitle();
		panelTop.add(title);
		
		insertName = new JPanel(new FlowLayout());
		nameLabel = new JLabel("Insert your name");
		name = new JTextField(10);
		nameLabel.setLabelFor(name);
		insertName.add(nameLabel);
		insertName.add(name);
		
		loadingLabel = new JLabel("Waiting for incoming player");
		loadingLabel.setForeground(MainWindow.LIGHT_COLOR);
		loadingLabel.setVisible(false);
		insertName.add(loadingLabel);
		
		panelTop.add(insertName);
	}
	
	private void initCenterPanel() {
		
		BufferedImage image;
		panelCenter = new JPanel();
		panelCenter.setLayout(new BoxLayout(panelCenter, BoxLayout.Y_AXIS));
		panelCenter.setAlignmentX(Component.CENTER_ALIGNMENT);
		try {
			JPanel p1 = new JPanel(new FlowLayout());
			image = ImageIO.read(new File("data/rpsls-tab.png"));
			JLabel center = new JLabel(new ImageIcon(image));
			p1.add(center);
			panelCenter.add(p1);
			
			JPanel p2 = new JPanel();
			p2.setLayout(new BoxLayout(p2, BoxLayout.Y_AXIS));
			p2.setAlignmentX(Component.CENTER_ALIGNMENT);
			URL u = new File("data/loading-dots.gif").toURI().toURL();
			ImageIcon i = new ImageIcon(u);
			loadingDots = new JLabel(i);
			loadingDots.setVisible(false);
			loadingDots.setAlignmentX(Component.CENTER_ALIGNMENT);
			p2.add(loadingDots);
					
			subIDLabel = new JLabel("");
			subIDLabel.setVisible(false);
			subIDLabel.setFont(new Font(subIDLabel.getFont().getName(), Font.PLAIN, 16));
			subIDLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
			p2.add(subIDLabel);
			
			panelCenter.add(p2);

			
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	private void initBottomPanel() {
		panelBottom = new JPanel(new FlowLayout());
		start = new JButton("Start Game");
		start.setEnabled(false);
		join = new JButton("Join a Game");
		join.setEnabled(false);
		stop = new JButton("Stop");
		panelBottom.add(start);
		panelBottom.add(join);
		panelBottom.add(stop);
	}
	
	private void addListeners() {
		
		ActionListener startGame = new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent arg0) {
				if (name.getText().length() > 0) {
					turnPanel(true);
					Player p = new Player(name.getText());
					IGame game = mainWindow.createNewGame(p);
					subIDLabel.setText(Utils.getSubID(game));
				}
			}
		};
		
		start.addActionListener(startGame);
		name.addActionListener(startGame);
		
		join.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				Player p = new Player(name.getText());
				mainWindow.showJoinGames(p);
			}
		});
		
		stop.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				mainWindow.deleteGame();
				turnPanel(false);
			}
		});
		
		name.addKeyListener(new KeyListener() {
			
			@Override
			public void keyTyped(KeyEvent arg0) {
				// TODO Auto-generated method stub	
			}
			
			@Override
			public void keyReleased(KeyEvent arg0) {
				start.setEnabled(name.getText().length() > 0);
				join.setEnabled(name.getText().length() > 0);
			}
			
			@Override
			public void keyPressed(KeyEvent arg0) {
				// TODO Auto-generated method stub
				
			}
		});
	}
	
	private void turnPanel(boolean startingGame) {
		loadingLabel.setVisible(startingGame);
		nameLabel.setVisible(!startingGame);
		name.setVisible(!startingGame);
		loadingDots.setVisible(startingGame);
		subIDLabel.setVisible(startingGame);
		
		start.setVisible(!startingGame);
		join.setVisible(!startingGame);
		stop.setVisible(startingGame);
	}
	
	private void set_me() {
		name.setText(me.getName());
		join.setEnabled(true);
		start.setEnabled(true);
	}
}
