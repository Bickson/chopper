package model;

import java.util.ArrayList;

public class Stage {
	
	private Chopper chopper;
	private Background background;
	private int width;
	private ArrayList<Obstacle> obstacles = new ArrayList<Obstacle>();
	//private Obstacle obstacle;
	
	public Stage() {
		this.chopper = new Chopper();
		//obstacles.add(new Obstacle(500,100,50,50));
		
		
		//enemyChopper1
		obstacles.add(new EnemyChopper(1100,100,50,50, chopper, this));
		obstacles.add(new EnemyChopper(900,600,50,50, chopper, this));
		obstacles.add(new EnemyChopper(1900,400,50,50, chopper, this));
		obstacles.add(new EnemyChopper(3500,800,50,50, chopper, this));
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
					System.out.println("Entering X");
					if(shot.getY() > obstacle.getY() && shot.getY() < (obstacle.getY() + obstacle.getHeight())) {
						System.out.println("Collition!");
						result = true;
					}	
				}
			}
		}
		
		return result;
	}
	
}
