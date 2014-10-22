package model;

/*
 * Represents the player and his stats
 */
public class Player {
	
	private String name;
	private int lifes;
	private int score;
	
	public Player(String name, int lifes) {
		this.name = name;
		this.lifes = lifes;
		this.score = 0;
	}
	
	public String getName() {
		return this.name;
	}
	
	public int getLifes() {
		return this.lifes;
	}
	
	public int getScore() {
		return this.score;
	}
	
	public void addToScore(int points) {
		this.score += points;
	}
	
	public void loseLife() {
		this.lifes -= 1;
	}

}
