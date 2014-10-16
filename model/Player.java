package model;

public class Player {
	
	private String name;
	private int lifes;
	
	public Player(String name, int lifes) {
		this.name = name;
		this.lifes = lifes;
	}
	
	public String getName() {
		return this.name;
	}
	
	public int getLifes() {
		return this.lifes;
	}
	
	public void loseLife() {
		this.lifes -= 1;
	}

}
