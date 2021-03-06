package model;

import java.util.ArrayList;

import javax.swing.ImageIcon;

/*
 * This represents the level that the player first encounters
 */
public class Stage {

	private Chopper chopper;
	private ArrayList<Obstacle> obstacles = new ArrayList<Obstacle>();
	private Player player;
	private Boss boss;

	public Stage() {
		this.chopper = new Chopper();
		//obstacles.add(new Obstacle(500,100,50,50));

		this.player = new Player("Johan", 30);

		//Creates the enemyChoppers:
		obstacles.add(new EnemyChopper(1100,100,50,50, chopper, this));
		obstacles.add(new EnemyChopper(1100,600,50,50, chopper, this));
		/*obstacles.add(new EnemyChopper(1150,100,50,50, chopper, this));
		obstacles.add(new EnemyChopper(1200,500,50,50, chopper, this));
		obstacles.add(new EnemyChopper(1500,100,50,50, chopper, this));
		obstacles.add(new EnemyChopper(1700,100,50,50, chopper, this));
		obstacles.add(new EnemyChopper(2100,100,50,50, chopper, this));
		obstacles.add(new EnemyChopper(3100,600,50,50, chopper, this));
		obstacles.add(new EnemyChopper(3500,300,50,50, chopper, this));
		obstacles.add(new EnemyChopper(3500,600,50,50, chopper, this));*/

	}

	public void createBoss() {
		this.boss = new Boss(1100,100,50,50,this.chopper);
	}

	public Boss getBoss() {
		return this.boss;
	}

	public void killBoss() {
		this.boss = null;
	}

	public Player getPlayer() {
		return this.player;
	}

	public Chopper getChopper() {
		return chopper;
	}

	public Obstacle getObstacles(int index) {
		return obstacles.get(index);
	}

	public int getSizeOfObstacles(){
		return obstacles.size();
	}

	//Overload
	public ArrayList<Obstacle> getObstacles() {
		return this.obstacles;
	}
	
	
	/*
	* @Params Shot, Chopper
	* @Return boolean if shot collides with Chopper 
	*/
	
	public boolean ChopperShotCollition(Shot shot, Chopper chopper) {
		boolean result = false;
		if( shot.getX() <= (chopper.getX() + chopper.getImage(0).getIconWidth()) && shot.getX() > chopper.getX() ) {
			if(shot.getY() < ( chopper.getY() + 70) && shot.getY() > chopper.getY() ) {
				result = true;
			}
		}

		return result;
	}
	
	/*
	* @Params Shot, Chopper
	* @Return boolean if shot collides with obstacle (enemy chopper) 
	*/
	
	public boolean Collition(Shot shot,Obstacle obstacle) {
		boolean result = false;
		if(shot.getX() >= obstacle.getX() && shot.getX() < (obstacle.getX() + obstacle.getImage(0).getIconWidth())) {
			if(shot.getY() > obstacle.getY() && shot.getY() < (obstacle.getY() + obstacle.getImage(0).getIconWidth())) {
				result = true;
			}
		}
		return result;
	}

}
