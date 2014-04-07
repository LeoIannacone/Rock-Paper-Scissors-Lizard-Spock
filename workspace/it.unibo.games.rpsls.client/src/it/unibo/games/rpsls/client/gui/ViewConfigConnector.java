package it.unibo.games.rpsls.client.gui;

import it.unibo.games.rpsls.connector.Config;
import it.unibo.games.rpsls.connector.client.SIBClient;
import it.unibo.games.rpsls.utils.Validate;

import java.awt.Color;
import java.awt.Component;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.regex.PatternSyntaxException;

import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.border.EmptyBorder;

public class ViewConfigConnector extends ViewDefault implements ActionListener{

	private JTextField host;
	private JTextField port;
	private JTextField name;
	private JButton connect;
	
	public ViewConfigConnector() {
		this.setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
		init();
	}
	
	private void init() {
		JPanel p1 = new JPanel(new FlowLayout());
		p1.add(new PanelTitle());
		this.add(p1);
		
		JPanel p5 = new JPanel(new FlowLayout());
		JLabel w = new JLabel("SIB Connection");
		w.setFont(new Font(w.getFont().getName(), Font.PLAIN, 20));
		p5.add(w);
		this.add(p5);
		
		JPanel p2 = new JPanel(new FlowLayout());
		JPanel p3 = new JPanel(new GridLayout(3,2));
		JLabel l = new JLabel("Host");
		l.setAlignmentX(Component.RIGHT_ALIGNMENT);
		//l.setBorder(new EmptyBorder(10, 0, 10, 0));
		p3.add(l);
		host = new JTextField(7);
		host.setText(Config.SIB_HOST);
		p3.add(host);
		p3.add(new JLabel("Port"));
		port = new JTextField(5);
		port.setText("" + Config.SIB_PORT);
		p3.add(port);
		p3.add(new JLabel("Name"));
		name = new JTextField(10);
		name.setText(Config.SIB_NAME);
		name.setEditable(false);
		p3.add(name);
		p2.add(p3);
		this.add(p2);
		
		JPanel p4 = new JPanel(new FlowLayout());
		connect = new JButton("Connect");
		connect.addActionListener(this);
		p4.add(connect);
		
		this.add(p4);
	}

	@Override
	public void actionPerformed(ActionEvent e) {
	
		try {
			int port_num = Validate.port(port.getText());
			reset(port);
			
			String ip = Validate.ip(host.getText());
			reset(host);
			
			Config.SIB_HOST = ip;
			Config.SIB_PORT = port_num;
			
			mainWindow.init();
			
		} catch (NumberFormatException ex){
			warn(port);
		} catch (PatternSyntaxException ex) {
			warn(host);
		}
		
	}
	
	private void warn(JTextField field) {
		field.setBackground(new Color(242, 222, 222));
		field.setForeground(new Color(169, 68, 66));
	}
	
	private void reset(JTextField field) {
		field.setBackground(new Color(255,255,255));
		field.setForeground(new Color(51,51,51));
	}
	
}