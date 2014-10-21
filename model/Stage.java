package model;

import java.util.ArrayList;

public class Stage {

	private Chopper chopper;
	private Background background;
	private int width;
	private ArrayList<Obstacle> obstacles = new ArrayList<Obstacle>();
	private Player player;
	private Boss boss;

	public Stage() {
		this.chopper = new Chopper();
		//obstacles.add(new Obstacle(500,100,50,50));

		this.player = new Player("Johan", 30);

		//enemyChopper1
		obstacles.add(new EnemyChopper(1100,100,50,50, chopper, this));
		obstacles.add(new EnemyChopper(1100,600,50,50, chopper, this));
		obstacles.add(new EnemyChopper(1150,100,50,50, chopper, this));
		obstacles.add(new EnemyChopper(1200,500,50,50, chopper, this));
		obstacles.add(new EnemyChopper(1500,100,50,50, chopper, this));
		obstacles.add(new EnemyChopper(1700,100,50,50, chopper, this));
		obstacles.add(new EnemyChopper(2100,100,50,50, chopper, this));
		obstacles.add(new EnemyChopper(3100,600,50,50, chopper, this));
		obstacles.add(new EnemyChopper(3500,300,50,50, chopper, this));
		obstacles.add(new EnemyChopper(3500,600,50,50, chopper, this));

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

	public boolean ObsShotCollition(ArrayList<Shot> shots, ArrayList<Obstacle> obstacles) {
		boolean result = false;

		for(Shot shot: shots) {
			for(Obstacle obstacle: obstacles) {
				if(shot.getX() >= obstacle.getX() && shot.getX() < (obstacle.getX() + obstacle.getWidth())) {
					//System.out.println("Entering X");
					if(shot.getY() > obstacle.getY() && shot.getY() < (obstacle.getY() + obstacle.getHeight())) {
						//System.out.println("Collition!");
						result = true;
						EnemyChopper ec = (EnemyChopper) obstacle;
						ec.life -= 1;
					}
				}
			}
		}

		return result;
	}

	public boolean ChopperShotCollition(Shot shot, Chopper chopper) {
		boolean result = false;

		if( shot.getX() <= (chopper.getX() + chopper.getImage(0).getIconWidth()) && shot.getX() > chopper.getX() ) {
			System.out.println("Entering X");
			if(shot.getY() < ( chopper.getY() + 70) && shot.getY() > chopper.getY() ) {
				System.out.println("Collition!");
				result = true;
			}
		}

		return result;
	}


	public boolean bossShotCollition(ArrayList<Shot> shots, ArrayList<Obstacle> obstacles) {
		boolean result = false;

		for(Shot shot: shots) {
			for(Obstacle obstacle: obstacles) {
				if(shot.getX() >= obstacle.getX() && shot.getX() < (obstacle.getX() + obstacle.getImage(0).getIconWidth())) {
					//System.out.println("Entering X");
					if(shot.getY() > obstacle.getY() && shot.getY() < (obstacle.getY() + 130)) {
						//System.out.println("Collition!");
						result = true;
						Boss boss = (Boss) obstacle;
						boss.addToLife(-1);
					}
				}
			}
		}

		return result;
	}

	public boolean Collition(Shot shot,Obstacle obstacle) {
		boolean result = false;
		if(shot.getX() >= obstacle.getX() && shot.getX() < (obstacle.getX() + obstacle.getImage(0).getIconWidth())) {
			if(shot.getY() > obstacle.getY() && shot.getY() < (obstacle.getY() + obstacle.getImage(0).getIconWidth())) {
				System.out.println("Collition!");
				result = true;
			}
		}
		return result;
	}

}
