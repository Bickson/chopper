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
		obstacles.add(new EnemyChopper(1100,100,50,50, chopper));
		obstacles.add(new EnemyChopper(900,600,50,50, chopper));
		obstacles.add(new EnemyChopper(1900,400,50,50, chopper));
		obstacles.add(new EnemyChopper(3500,800,50,50, chopper));
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

}
