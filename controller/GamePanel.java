package controller;

import java.awt.Color;

import javax.swing.*;

public class GamePanel extends JPanel {
	
	public GamePanel() {
		this.setBackground(Color.blue);
		
	}
	
	public JMenuBar createMenu() {
		JMenuBar bar = new JMenuBar();
		JMenu game = new JMenu("Game");
		game.add(new JMenuItem("New Game"));
		game.add(new JMenuItem("Load Game"));
		game.add(new JMenuItem("Exit Game"));
		
		JMenu settings = new JMenu("settings");
		JMenu dif = new JMenu("Difficulty");
		dif.add(new JMenuItem("Easy"));
		dif.add(new JMenuItem("Medium"));
		dif.add(new JMenuItem("Hard"));
		settings.add(dif);
		
		bar.add(game);
		bar.add(settings);
		
		return bar;
	}
	
}
