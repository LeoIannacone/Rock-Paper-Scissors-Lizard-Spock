package it.unibo.games.rpsls.gui;

import javax.imageio.ImageIO;
import javax.swing.*;

import java.awt.Component;
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

public class PanelWelcome extends JPanel {

	private JPanel panelTop;
	private JPanel panelCenter;
	private JPanel panelBottom;
	
	private JPanel insertName;
	private JLabel nameLabel;
	private JLabel loadingLabel;
	private JTextField name;
	
	private JButton start;
	private JButton stop;
	private JLabel loadingDots;
	
	public PanelWelcome() {
		setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
		init();
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
		JLabel gameName = new JLabel("RPSLS");
		gameName.setFont(new Font(gameName.getFont().getName(), Font.PLAIN, 40));
		panelTop.add(gameName);
		
		insertName = new JPanel(new FlowLayout());
		nameLabel = new JLabel("Insert your name");
		name = new JTextField(10);
		nameLabel.setLabelFor(name);
		insertName.add(nameLabel);
		insertName.add(name);
		
		loadingLabel = new JLabel("Waiting for incoming player");
		loadingLabel.setVisible(false);
		insertName.add(loadingLabel);
		
		panelTop.add(insertName);
	}
	
	private void initCenterPanel() {
		
		BufferedImage image;
		panelCenter = new JPanel();
		panelCenter.setLayout(new BoxLayout(panelCenter, BoxLayout.Y_AXIS));
		panelCenter.setAlignmentX(Component.CENTER_ALIGNMENT);
		panelCenter.setAlignmentY(Component.TOP_ALIGNMENT);
		try {
			JPanel p1 = new JPanel(new FlowLayout());
			image = ImageIO.read(new File("data/rpsls-tab.png"));
			JLabel center = new JLabel(new ImageIcon(image));
			p1.add(center);
			panelCenter.add(p1);
			
			JPanel p2 = new JPanel(new FlowLayout());
			URL u = new File("data/loading-dots.gif").toURI().toURL();
			ImageIcon i = new ImageIcon(u);
			loadingDots = new JLabel(i);
			loadingDots.setVisible(false);
			p2.add(loadingDots);
			panelCenter.add(p2);
			
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	private void initBottomPanel() {
		panelBottom = new JPanel(new FlowLayout());
		start = new JButton("Start Game");
		start.setEnabled(false);
		stop = new JButton("Stop");
		panelBottom.add(start);
		panelBottom.add(stop);
	}
	
	private void addListeners() {
		
		ActionListener startGame = new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent arg0) {
				if (name.getText().length() > 0) {
					turnPanel(true);
				}
			}
		};
		
		start.addActionListener(startGame);
		name.addActionListener(startGame);
		
		stop.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
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
				// TODO Auto-generated method stub
				start.setEnabled(name.getText().length() > 0);
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
		
		start.setVisible(!startingGame);
		stop.setVisible(startingGame);
	}
}
