package controller;

import java.awt.Color;
import java.awt.Font;
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
	private static int difficulty = 0;
	
	public GamePanel() {
		this.setBackground(Color.blue);
		this.addKeyListener(new keyHandler());
		this.setFocusable(true);
		frameNumber = 2;
	}
	
	public Stage getStage() {
		return this.level1;
	}
	
	public void startAnimation() {
		timer = new Timer(20, this);
		timer.start();
	}
	
	public void actionPerformed(ActionEvent e) {
		repaint();
		
		if(this.level1.getBoss() != null) {
			if(this.level1.getBoss().getY() > 680) {
				this.stopAnimation();
			}
		}
		
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
			difficulty = 2;
		}
	}
	
	public void paintComponent(Graphics g) {
		super.paintComponent(g);
		//Paint Background
		//Background
		
		imageBG = background.getBG(frameNumber);
		imageBG.paintIcon(this, g, 0, 0);
		
		if(frameNumber%25 == 0){
			this.level1.getPlayer().addToScore(1);
		}
		
		//=======Paint Chopper========//
		Chopper chopper = this.level1.getChopper();
		chopper.getImage(frameNumber).paintIcon(this,g,chopper.getX(),chopper.getY());
		this.paintChopperShots(g);
		//============================//
		
		
		//=======Boss Logic========//
		if(this.level1.getObstacles().size() == 0) {// No EnemyChoppers left Time for boss
			if(this.level1.getBoss() == null) {
				this.level1.createBoss();
			}
			if(this.level1.getBoss().getLife() > 0) {
				this.paintBoss(g);
				this.paintBossShots(g, chopper);
				this.checkBossCollition();
			}
			if(this.level1.getBoss().getLife() <= 0){
				this.level1.getBoss().LoaderDead();
				this.level1.getBoss().addToY(2);
				this.level1.getBoss().getImage(frameNumber).paintIcon(this,g, this.level1.getBoss().getX(), this.level1.getBoss().getY());
			}
			if(this.level1.getBoss().getLife() == 0) {
				this.level1.getPlayer().addToScore(1000);
				this.level1.getBoss().addToLife(-1);
			}
			// intro logic
			if(this.level1.getBoss().getX() >= 700){
				this.level1.getBoss().addToX(-2);
				if(this.level1.getBoss().getX() == 700) {
					this.level1.getBoss().setIntroduced(true);
				}
			}
			
		}
		//============================//
		
		
		//=======Obstacle Logic========//
		this.paintObstacles(g);
		this.paintObstacleShots(g);
		this.checkObstacleHit();
		this.checkHitByObstacle();
		//============================//

		this.paintPlayerStats(g);
		
		
		frameNumber++;
	}
	
	public void paintChopperShots(Graphics g) {
		ArrayList<Shot> shots = this.level1.getChopper().getShots();
		if(shots.size() != 0) {
			for(Shot shot: shots) {
				//g.setColor(Color.red);
				//g.fillArc(shot.getX(),shot.getY(),shot.getWidth(),shot.getHeight(),90,360);
				shot.getImage(frameNumber).paintIcon(this,g,shot.getX(),shot.getY());
				shot.addToX(5);
				if(shot.getX() > 1024){
					this.level1.getChopper().removeShot(shot);
					break;
				}
			}
		}// end of painting shots logic
	}
	
	public void checkBossCollition() {
		ArrayList<Obstacle> bosses = new ArrayList<Obstacle>();
		ArrayList<Shot> shots = this.level1.getChopper().getShots();
		bosses.add(this.level1.getBoss());
		if(this.level1.bossShotCollition(shots,bosses)) {
			shots.remove(0);
		}
	}
	
	public void paintBoss(Graphics g) {
		Boss boss = this.level1.getBoss();
		boss.getImage(frameNumber).paintIcon(this,g, boss.getX(), boss.moveY(frameNumber));
		Font font = new Font("Helvetica", Font.BOLD, 20);
		//g.setFont(font);
		//g.drawString("" + boss.getLife(), boss.getX() + 110, boss.getY() + 45);
		g.setColor(Color.red);
		g.fillRect(boss.getX() + 30, boss.getY(), 9*boss.getLife(), 5);
		g.setColor(Color.black);
		g.drawRect(boss.getX() + 30, boss.getY(), 180, 5);
	}
	
	public void paintBossShots(Graphics g, Chopper chopper) {
		ArrayList<Shot> bossShots = this.level1.getBoss().getShots();
		for(Shot shot: bossShots) {
			shot.getImage(frameNumber).paintIcon(this,g,shot.getX(),shot.getY());
			shot.addToX(-7);
			if(this.level1.ChopperShotCollition(shot,chopper)) {
				bossShots.remove(0);
				this.level1.getPlayer().loseLife();
				break;
			}
		}
	}
	
	public void paintObstacles(Graphics g) {
		ArrayList<Obstacle> obstacles = this.level1.getObstacles();
		for(Obstacle obstacle: obstacles) {
			obstacle.getImage(frameNumber).paintIcon(this,g,obstacle.moveX(frameNumber),obstacle.moveY(frameNumber));
			g.setColor(Color.red);
			g.fillRect(obstacle.getX() + 40, obstacle.getY() + 20, 50*obstacle.getLife(), 5);
			g.setColor(Color.black);
			g.drawRect(obstacle.getX() + 40, obstacle.getY() + 20, 150, 5);
			
		}
		
		//check if any enemychoppers life is zero and should die
		ArrayList<Obstacle> enemychoppers = this.level1.getObstacles();
		for(Obstacle ec: enemychoppers) {
			EnemyChopper enemychopper = (EnemyChopper) ec;
			if(enemychopper.getLife() <= 0) {
				this.level1.getObstacles().remove(enemychopper);
				this.level1.getPlayer().addToScore(100);
				break;
			}
		}
	}
	
	public void paintObstacleShots(Graphics g) {
		for(int i=0; i<level1.getSizeOfObstacles(); i++){
			Shot shot = level1.getObstacles(i).getShot();
			//Shot shot = obstacle.getShot();
			if(shot != null ){
				//System.out.println("DEBUG: drawing shot!!");
				shot.getImage(frameNumber).paintIcon(this,g,shot.moveX(frameNumber),shot.moveY(frameNumber));
			}
			//obstacle.getImage(frameNumber).paintIcon(this,g,obstacle.getX(),obstacle.getY(frameNumber));
		}
	}
	
	public void checkObstacleHit() {
		ArrayList<Shot> shots = this.level1.getChopper().getShots();
		if(this.level1.ObsShotCollition(shots,this.level1.getObstacles())) {;
			shots.remove(0); //  I just assume that the first 
		}					//   shot in the list has the biggest x value
	}
	
	public void checkHitByObstacle() {
		ArrayList<Obstacle> obstacles = this.level1.getObstacles();
		EnemyChopper ECshotToRemove = null;
		for(Obstacle obstacle:obstacles) {
			if(obstacle.getShot() != null) {
				if( this.level1.ChopperShotCollition(obstacle.getShot(), this.level1.getChopper()) ) {
					this.level1.getPlayer().loseLife();
					this.level1.getPlayer().addToScore(-10);
					ECshotToRemove = (EnemyChopper) obstacle;
					break;
				}
			}
		}
		
		if(ECshotToRemove != null) {
			ECshotToRemove.setShotfire(false);
		}
	}
	
	public void paintPlayerStats(Graphics g) {
		//Player stats
		Font font1 = new Font("Helvetica", Font.PLAIN, 20);
		Font font2 = new Font("Helvetica", Font.BOLD, 30);
		String playername = this.level1.getPlayer().getName();
		String playerlife = "" + this.level1.getPlayer().getScore();
		g.setFont(font1);
		g.drawString("Player: ", 25, 580);
		g.drawString("Score: ", 25, 610);
		g.setFont(font2);
		g.drawString(playername, 100, 580);
		g.setFont(font2);
		g.drawString(playerlife, 100, 610);
		
		Chopper chopper = this.level1.getChopper();
		if(this.level1.getPlayer().getLifes() > 0) {
			g.setColor(Color.red);
			g.fillRect(chopper.getX() + 40, chopper.getY() + 20, 5*this.level1.getPlayer().getLifes(), 5);
			g.setColor(Color.black);
			g.drawRect(chopper.getX() + 40, chopper.getY() + 20, 150, 5);
		}
	}
	
	public static int getDifficulty(){
		return difficulty;
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
				chopper.addToY(10);
				//GamePanel.this.repaint();
				//Ship.this.y += 5;
				//Ship.this.repaint();
			}
			if(ke.getKeyCode() == KeyEvent.VK_UP){
				chopper.addToY(-10);
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
	
	public void stopAnimation() {
		timer.stop();
		String playername = this.level1.getPlayer().getName();
		int playerscore = this.level1.getPlayer().getScore();
		int playerlifelost = this.level1.getPlayer().getLifes() -30;
		int totalscore = playerscore + (playerlifelost*10);
		JOptionPane.showMessageDialog(this, "Player: " + playername + "\nScore: "+ playerscore + "\nLifelost: " + playerlifelost + " (*10)" + "\nTotal score: " + totalscore);
		System.exit(0);
	}
	
	
}
