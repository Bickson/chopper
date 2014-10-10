package model;

import java.util.ArrayList;

public class Stage {
	
	private Chopper chopper;
	private Background background;
	private int width;
	private ArrayList<Obstacle> obstacles = new ArrayList<Obstacle>();
	
	public Stage() {
		this.chopper = new Chopper();
	}
	
	public Chopper getChopper() {
		return chopper;
	}

}
