package controller;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.*;

import model.*;

public class GamePanel extends JPanel {
	
	private Stage level1 = new Stage(); 
	
	public GamePanel() {
		this.setBackground(Color.blue);
		
	}
	
	public JMenuBar createMenu() {
		JMenuBar bar = new JMenuBar();
		JMenu game = new JMenu("Game");
		game.add(new JMenuItem("New Game"));
		game.add(new JMenuItem("Load Game"));
		
		JMenuItem exit = new JMenuItem("Exit Game");
		game.add(exit);
		
		JMenu settings = new JMenu("settings");
		JMenu dif = new JMenu("Difficulty");
		dif.add(new JMenuItem("Easy"));
		dif.add(new JMenuItem("Medium"));
		dif.add(new JMenuItem("Hard"));
		settings.add(dif);
		
		bar.add(game);
		bar.add(settings);
		
		// add Listeners
		exit.addActionListener(new exitHandler());
		
		return bar;
	}
	
	public class exitHandler implements ActionListener {
		public void actionPerformed(ActionEvent e) {
			System.exit(0);
		}
		
	}
	
	public void paintComponent(Graphics g) {
		super.paintComponent(g);
		Chopper chopper = this.level1.getChopper();
		chopper.getImage().paintIcon(this,g,chopper.getX(),chopper.getY());
	}
	
}
