package model;

import java.util.ArrayList;

public class Stage {
	
	private Chopper chopper;
	private Background background;
	private int width;
	private ArrayList<Obstacle> obstacles = new ArrayList<Obstacle>();
	private Obstacle obstacle;
	
	public Stage() {
		this.chopper = new Chopper();
		this.obstacle = new Obstacle(500,100,50,50);
	}
	
	public Chopper getChopper() {
		return chopper;
	}
	
	public Obstacle getObstacles() {
		return this.obstacle;
	}

}
