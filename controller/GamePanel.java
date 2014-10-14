package controller;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.event.*;
import java.util.ArrayList;

import javax.swing.*;

import model.*;
//import model.Chopper;


public class GamePanel extends JPanel implements ActionListener{
	
	private Stage level1 = new Stage();
	private Timer timer;
	private Background background = new Background();
	private ImageIcon imageBG;
	private int frameNumber;
	private static int difficulty = 1;
	
	public GamePanel() {
		this.setBackground(Color.blue);
		this.addKeyListener(new keyHandler());
		this.setFocusable(true);
		frameNumber = 1;
	}
	
	public void startAnimation() {
		timer = new Timer(20, this);
		timer.start();
	}
	
	public void actionPerformed(ActionEvent e) {
		//Obstacle obstacle = this.level1.getObstacles();
		//obstacle.addToX(-1);
		repaint();
		
		/*for(Obsticle obs: obsticles) {
			obs.move();
			if(obs.getX() <= 0){
				this.addAndRemoveObs(obs);
				break;
			}
			
			if(checkCollition(obs)) {
				System.out.println("COLLITION!");
				System.exit(0);
				//Toolkit.getDefaultToolkit().beep();
				//JOptionPane.showMessageDialog(this, "U suck");
			}
			repaint();
		}*/
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
		
		JMenuItem difEasy= new JMenuItem("Easy");
		dif.add(difEasy);
		JMenuItem difMedium = new JMenuItem("Medium");
		dif.add(difMedium);
		JMenuItem difHard = new JMenuItem("Hard");
		dif.add(difHard);
		settings.add(dif);
		
		bar.add(game);
		bar.add(settings);
		
		// add Listeners
		exit.addActionListener(new exitHandler());
		difEasy.addActionListener(new difficultyEasyHandler());
		
		
		difMedium.addActionListener(new difficultyMediumHandler());
		difHard.addActionListener(new difficultyHardHandler());
		
		
		return bar;
	}
	
	public class exitHandler implements ActionListener {
		public void actionPerformed(ActionEvent e) {
			System.exit(0);
		}
		
	}
	public class difficultyEasyHandler implements ActionListener {
		public void actionPerformed(ActionEvent e) {
			difficulty = 0;
		}
	}

	public class difficultyMediumHandler implements ActionListener {
		public void actionPerformed(ActionEvent e) {
			difficulty = 1;
		}
	}
	public class difficultyHardHandler implements ActionListener {
		public void actionPerformed(ActionEvent e) {
			difficulty = 3;
		}
	}
	
	public void paintComponent(Graphics g) {
		super.paintComponent(g);
		//Paint Background
		//Background
		
		imageBG = background.getBG(frameNumber);
		imageBG.paintIcon(this, g, 0, 0);
		
		
		//Paint Chopper
		Chopper chopper = this.level1.getChopper();
		chopper.getImage(frameNumber).paintIcon(this,g,chopper.getX(),chopper.getY());
		
		
		//Paint all obstacles
		for(int i=0; i<level1.getSizeOfObstacles(); i++){
			Obstacle obstacle = level1.getObstacles(i);
			obstacle.getImage(frameNumber).paintIcon(this,g,obstacle.moveX(frameNumber),obstacle.moveY(frameNumber));
		}
		
		//Paint any shots fired
		for(int i=0; i<level1.getSizeOfObstacles(); i++){
			Shot shot = level1.getObstacles(i).getShot();
			//Shot shot = obstacle.getShot();
			if(shot != null ){
				//System.out.println("DEBUG: drawing shot!!");
				shot.getImage(frameNumber).paintIcon(this,g,shot.moveX(frameNumber),shot.moveY(frameNumber));
			}
			//obstacle.getImage(frameNumber).paintIcon(this,g,obstacle.getX(),obstacle.getY(frameNumber));
		}
		
		//Obstacle obstacle = this.level1.getObstacles();
		//g.fillOval(obstacle.getX(), obstacle.getY(), obstacle.getWidth(), obstacle.getHeight());
		
		// Painting Chopper shots logic
		ArrayList<Shot> shots = chopper.getShots();
		if(shots.size() != 0) {
			for(Shot shot: shots) {
				//g.setColor(Color.red);
				//g.fillArc(shot.getX(),shot.getY(),shot.getWidth(),shot.getHeight(),90,360);
				shot.getImage(frameNumber).paintIcon(this,g,shot.getX(),shot.getY());
				shot.addToX(5);
				if(shot.getX() > 1024){
					chopper.removeShot(shot);
					break;
				}
			}
		}// end of painting shots logic
		
		if(this.level1.ObsShotCollition(shots,this.level1.getObstacles())) {;
			shots.remove(0); //  I just assume that the first 
		}					//   shot in the list has the biggest x value
		
		//check if any enemychoppers life is zero and should die
		ArrayList<Obstacle> enemychoppers = this.level1.getObstacles();
		for(Obstacle ec: enemychoppers) {
			EnemyChopper enemychopper = (EnemyChopper) ec;
			if(enemychopper.getLife() <= 0) {
				this.level1.getObstacles().remove(enemychopper);
				break;
			}
		}
		
		
		frameNumber++;
	}
	public static int getDifficulty(){
		return difficulty;
	}
	public Stage getStage(){
		return level1;
	}
	
	
	private class keyHandler implements KeyListener {
		
		public void keyTyped(KeyEvent ke) {
			System.out.println("Key typed   : " + 
				ke.getKeyChar() + ", " + ke.getKeyCode());
		}
		
		public void keyPressed(KeyEvent ke) {
			//System.out.println("Key pressed : " + 
			//	ke.getKeyChar() + ", " + ke.getKeyCode());
			Chopper chopper = GamePanel.this.level1.getChopper();
			
			if(ke.getKeyCode() == KeyEvent.VK_DOWN){
				chopper.addToY(8);
				//GamePanel.this.repaint();
				//Ship.this.y += 5;
				//Ship.this.repaint();
			}
			if(ke.getKeyCode() == KeyEvent.VK_UP){
				chopper.addToY(-8);
				//GamePanel.this.repaint();
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
			if(ke.getKeyCode() == KeyEvent.VK_SPACE) {
				chopper.addShot();
			}
		}
		
		public void keyReleased(KeyEvent ke) {
			//System.out.println("Key released: " + 
			//	ke.getKeyChar() + ", " + ke.getKeyCode());
		}
	}
	
}
