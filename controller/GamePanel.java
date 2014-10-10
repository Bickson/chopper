package controller;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.event.*;

import javax.swing.*;

import model.*;

public class GamePanel extends JPanel {
	
	private Stage level1 = new Stage(); 
	
	public GamePanel() {
		this.setBackground(Color.blue);
		this.addKeyListener(new keyHandler());
		this.setFocusable(true);
		
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
	
	private class keyHandler implements KeyListener {
		
		public void keyTyped(KeyEvent ke) {
			System.out.println("Key typed   : " + 
				ke.getKeyChar() + ", " + ke.getKeyCode());
		}
		
		public void keyPressed(KeyEvent ke) {
			System.out.println("Key pressed : " + 
				ke.getKeyChar() + ", " + ke.getKeyCode());
			Chopper chopper = GamePanel.this.level1.getChopper();
			
			if(ke.getKeyCode() == KeyEvent.VK_DOWN){
				chopper.addToY(5);
				GamePanel.this.repaint();
				//Ship.this.y += 5;
				//Ship.this.repaint();
			}
			if(ke.getKeyCode() == KeyEvent.VK_UP){
				chopper.addToY(-5);
				GamePanel.this.repaint();
				//Ship.this.y -= 5;
				//Ship.this.repaint();
			}
			if(ke.getKeyCode() == KeyEvent.VK_RIGHT){
				//Ship.this.x += 5;
				//Ship.this.repaint();
			}
			if(ke.getKeyCode() == KeyEvent.VK_LEFT){
				//Ship.this.x -= 5;
				//Ship.this.repaint();
			}
		}
		
		public void keyReleased(KeyEvent ke) {
			System.out.println("Key released: " + 
				ke.getKeyChar() + ", " + ke.getKeyCode());
		}
	}
	
}
