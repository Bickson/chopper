package controller;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.event.*;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;

import javax.swing.*;

import view.GFXInterface;
import main.GameMain;
import model.*;
//import model.Chopper;

/*
 * Creates the panel and draws all objects onto it
 */
public class GamePanel extends JPanel implements ActionListener{

	/**
	 *
	 */
	private static final long serialVersionUID = 1L;
	private Stage level1 = new Stage();
	private Timer timer;
	private Background background = new Background();
	private ImageIcon imageBG;
	private int frameNumber;
	private static int difficulty = 0;
	private boolean paused=false;
	private String filename = "scoreboard.csv";
	private ArrayList<Integer> scoreBoard = new ArrayList<Integer>();

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

	@Override
	public void actionPerformed(ActionEvent e) {
		repaint();

		if(this.level1.getPlayer().getLifes() <= 0){
			try {
				this.stopAnimation();
			} catch (IOException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
		}
		if(this.level1.getBoss() != null) {
			if(this.level1.getBoss().getY() > 680) {
				try {
					this.stopAnimation();
				} catch (IOException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
			}
		}

	}
/*
 * Creates the menu used in the game
 *
 * @return bar - object JMenuBar
 *
 */
	public JMenuBar createMenu() {
		JMenuBar bar = new JMenuBar();
		JMenu game = new JMenu("Game");
		JMenuItem pauseResume = new JMenuItem("Pause/Resume");
		game.add(pauseResume);
		JMenuItem exit = new JMenuItem("Exit Game");
		game.add(exit);
		JMenuItem newgame = new JMenuItem("New Game");
		game.add(newgame);
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

		newgame.addActionListener(new newGameHandler());

		difEasy.addActionListener(new difficultyEasyHandler());

		pauseResume.addActionListener(new pauseResumeHandler());

		difMedium.addActionListener(new difficultyMediumHandler());
		difHard.addActionListener(new difficultyHardHandler());


		return bar;
	}




	public class pauseResumeHandler implements ActionListener {
		@Override
		public void actionPerformed(ActionEvent e) {
			if(paused){
				GamePanel.this.resumeGame();
			}
			else {
				GamePanel.this.pauseGame();
			}

		}

	}

	public class newGameHandler implements ActionListener {
		@Override
		public void actionPerformed(ActionEvent e) {
			try {
				Runtime.getRuntime().exec("java -jar chopper.jar");
			} catch (IOException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
			System.exit(0);
		}

	}
	public class exitHandler implements ActionListener {
		@Override
		public void actionPerformed(ActionEvent e) {
			System.exit(0);
		}

	}
	public class difficultyEasyHandler implements ActionListener {
		@Override
		public void actionPerformed(ActionEvent e) {
			difficulty = 0;
		}
	}

	public class difficultyMediumHandler implements ActionListener {
		@Override
		public void actionPerformed(ActionEvent e) {
			difficulty = 1;
		}
	}
	public class difficultyHardHandler implements ActionListener {
		@Override
		public void actionPerformed(ActionEvent e) {
			difficulty = 2;
		}
	}
/*
 * Paints all the game elements on the canvas
 * @see javax.swing.JComponent#paintComponent(java.awt.Graphics)
 */
	@Override
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
		chopper.addToShottimer(1);
		//============================//



		//=======Boss Logic========//
		if(this.level1.getObstacles().size() == 0) {// No EnemyChoppers left Time for boss
			if(this.level1.getBoss() == null) {
				this.level1.createBoss();
			}
			if(this.level1.getBoss().getLife() > 0) {
				this.paintBoss(g);
				this.paintBossShots(g, chopper);
				this.checkBossHit();
				this.checkHitByBoss();
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
				shot.getImage(frameNumber).paintIcon(this,g,shot.getX(),shot.getY());
				shot.addToX(5);
				if(shot.getX() > 1024){
					this.level1.getChopper().removeShot(shot);
					break;
				}
			}
		}// end of painting shots logic
	}

	public void checkBossHit() {
		ArrayList<Obstacle> bosses = new ArrayList<Obstacle>();
		ArrayList<Shot> shots = this.level1.getChopper().getShots();
		bosses.add(this.level1.getBoss());
		for(Obstacle obs: bosses) {
			for(Shot shot: shots) {
				if(this.level1.Collition(shot,obs)) {
					this.level1.getBoss().addToLife(-1);
					shots.remove(0);
					break;
				}
			}
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
			/*if(this.level1.ChopperShotCollition(shot,chopper)) {
				bossShots.remove(0);
				this.level1.getPlayer().loseLife();
				break;
			}*/
		}
	}
	public void checkHitByBoss() {
		ArrayList<Shot> bossShots = this.level1.getBoss().getShots();
		for(Shot shot: bossShots) {
			if(this.level1.ChopperShotCollition(shot,this.level1.getChopper())) {
				bossShots.remove(shot);
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
		ArrayList<Obstacle> obstacles = this.level1.getObstacles();
		for(Obstacle obs: obstacles) {
			obs.getShots();
			for(Shot shot: obs.getShots()) {
				shot.getImage(frameNumber).paintIcon(this,g,shot.moveX(frameNumber),shot.moveY(frameNumber));
			}
		}
	}

	public void checkObstacleHit() {
		ArrayList<Obstacle> obstacles = this.level1.getObstacles();
		ArrayList<Shot> shots = this.level1.getChopper().getShots();
		for(Obstacle obs: obstacles) {
			for(Shot shot: shots) {
				if(this.level1.Collition(shot,obs)) {
					//this.level1.getBoss().addToLife(-1);
					EnemyChopper ec = (EnemyChopper) obs;
					ec.addToLife(-1);
					shots.remove(0);
					break;
				}
			}
		}
	}

	public void checkHitByObstacle() {
		ArrayList<Obstacle> obstacles = this.level1.getObstacles();
		for(Obstacle obstacle:obstacles) {
			if(obstacle.getShots().size() != 0 ) {
				for(Shot shot:obstacle.getShots()){
					if( this.level1.ChopperShotCollition(shot, this.level1.getChopper()) ) {
						this.level1.getPlayer().loseLife();
						this.level1.getPlayer().addToScore(-10);
						obstacle.getShots().remove(shot);
						break;
					}
				}
			}
		}
	}

	public void paintPlayerStats(Graphics g) {
		//Player stats
		Font font1 = new Font("Helvetica", Font.PLAIN, 20);
		Font font2 = new Font("Helvetica", Font.BOLD, 30);
		String playerlife = "" + this.level1.getPlayer().getScore();
		g.setFont(font1);
		g.drawString("Score: ", 25, 610);
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

	/*
	 * Handles player key presses.
	 */
	private class keyHandler implements KeyListener {

		@Override
		public void keyTyped(KeyEvent ke) {
			System.out.println("Key typed   : " +
				ke.getKeyChar() + ", " + ke.getKeyCode());
		}
		//test
		@Override
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
			if(ke.getKeyCode() == KeyEvent.VK_P){
				if(paused){
					GamePanel.this.resumeGame();
				}
				else {
					GamePanel.this.pauseGame();
				}
				//Ship.this.x += 5;
				//Ship.this.repaint();
			}
			if(ke.getKeyCode() == KeyEvent.VK_LEFT){
				//Ship.this.x -= 5;
				//Ship.this.repaint();
			}
			if(ke.getKeyCode() == KeyEvent.VK_SPACE) {
				if(chopper.getshotTimer() >= 20){
					chopper.addShot();
					chopper.resetShottimer();
				}

			}
		}

		@Override
		public void keyReleased(KeyEvent ke) {
			//System.out.println("Key released: " +
			//	ke.getKeyChar() + ", " + ke.getKeyCode());
		}
	}

	public void pauseGame() {
		timer.stop();
		this.paused = true;
	}
	public void resumeGame(){
		timer.start();
		this.paused = false;
	}

/*
* Stops the game and shows the end stats and scoreboard. Also saves the score and writes it to file.
*/
	public void stopAnimation() throws IOException {
		timer.stop();
		String playername = this.level1.getPlayer().getName();
		int playerscore = this.level1.getPlayer().getScore();
		int playerlifelost = this.level1.getPlayer().getLifes() -30;
		int totalscore = playerscore + (playerlifelost*10);
		
		scoreBoard = readScoreBoard(filename);
		System.out.println("totalScore: " + totalscore);
		
		for(int i: scoreBoard) {
			System.out.println("-"+ i);
		}
		
		scoreBoard.add(totalscore);
		
		for(int i: scoreBoard) {
			System.out.println("--"+ i);
		}
		
		//addToScoreBoard(totalscore);
		/*JOptionPane.showMessageDialog(this, "Player: " + playername + "\nScore: "+ playerscore + "\nLifelost: "
		+ playerlifelost + " (*10)" + "\nTotal score: " + totalscore + "\n"
		+ "Sore Board: \n" + scoreBoardToString());
		
		for(int i: scoreBoard) {
			System.out.println
		}

		
		System.exit(0);*/


		writeScoreBoardToFile(filename, scoreBoard);
		
		JFrame frame = new JFrame("GAME END");
		frame.setSize(170,350);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setVisible(true);

		JPanel endPanel = new JPanel();
		endPanel.setFocusable(true);
		
		JLabel playerscoresum = new JLabel("Score: " + playerscore);
		JLabel lifelostsum= new JLabel("Lifelost: " + playerlifelost + "(*10)");
		JLabel scoresum = new JLabel("Final score: " + totalscore);

		JButton exit = new JButton("  Quit  ");
		exit.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e){
				System.exit(0);
			}
		});

		JButton newgame = new JButton("New Game");
		newgame.addActionListener(new newGameHandler());

		Object[] columnNames = {"Scoreboard"};
		Object[][] data = new Object[11][1];
		int temp = 1;
		//for(int i=0;i<scoreBoard.size();i++) {
		data[0][0] = "Scoreboard";
		for(int i=scoreBoard.size()-1;i>scoreBoard.size()-11;i--){
			Array.set(data[temp], 0,new Integer(scoreBoard.get(i)));
			temp++;
		}

		JTable table = new JTable(data,columnNames);
		table.setShowGrid(true);
		
		endPanel.add(playerscoresum);
		endPanel.add(lifelostsum);
		endPanel.add(scoresum);
		endPanel.add(table);
		endPanel.add(newgame);
		endPanel.add(exit);

		frame.add(endPanel);
		frame.setLocationRelativeTo(this);

		//JOptionPane.showMessageDialog(this, "Player: " + playername + "\nScore: "+ playerscore + "\nLifelost: " + playerlifelost + " (*10)" + "\nTotal score: " + totalscore);
		//System.exit(0);
	}

	
	

	public void addToScoreBoard(int score){
		scoreBoard.add(score);
	}

	private String scoreBoardToString(){
		String info = new String();
		int temp = 1;
		Collections.sort(scoreBoard);
		//Arrays.sort(scoreBoard);
		for(int i=scoreBoard.size()-1;i>scoreBoard.size()-11;i--){
			info += temp + ": " + scoreBoard.get(i) + "\n";
			temp++;
		}
		return info;
	}

	public static ArrayList<Integer> readScoreBoard(String filename)
			throws IOException {
				//CollectionOfBooks books = new CollectionOfBooks();
				ArrayList<Integer> scoreBoard = new ArrayList<Integer>();
				//Error handling:
				// Tries to open file, if unsuccessful returns an empty object.
				try{
					FileReader reader = new FileReader(filename);
					reader.close();
				}
				catch (IOException e){
					System.out.println("The file " + filename + " could not be opened");
				}

				//If ok then start reading:
				BufferedReader reader = new BufferedReader(new FileReader(filename));
				String line;
				while((line = reader.readLine()) != null){

					String[] parts = line.split(",");
					scoreBoard.add(Integer.parseInt(parts[0]));
				}
				reader.close();
				return scoreBoard;
		}


		public static void writeScoreBoardToFile(String filename, ArrayList<Integer> scoreBoard)
			throws FileNotFoundException, IOException {
			String whatToWrite = new String();
			Collections.sort(scoreBoard);
			for(int i=0; i<scoreBoard.size();i++){
				whatToWrite += scoreBoard.get(i) + ", \n";
			}
			BufferedWriter writer = new BufferedWriter(new FileWriter(filename));
			writer.write(whatToWrite);
			writer.close();

		}










}
