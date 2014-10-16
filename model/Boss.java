package model;

import java.util.ArrayList;

import javax.swing.ImageIcon;

import controller.GamePanel;

public class Boss extends Obstacle {
	
	Chopper targetchopper;
	ArrayList<ImageIcon> images = new ArrayList<ImageIcon>();
	private int howManyImagesToLoad = 2;
	private String tempImagePath;
	private int shotTimer;
	ArrayList<Shot> shots = new ArrayList<Shot>();
	protected int life = 20;

	public Boss(int x, int y, int width, int height, Chopper chopper) {
		super(x, y, width, height);
		this.targetchopper = chopper;
		this.shotTimer=0;
		preLoader();
		// TODO Auto-generated constructor stub
	}
	
	public void addtarget(Chopper chopper) {
		this.targetchopper = chopper;
	}
	
	public int getX() {
		return this.x;
	}
	
	public int getY() {
		return this.y;
	}
	
	public int getLife() {
		return this.life;
	}
	
	@Override
	public int moveY(int index) {
		//update AI every 5 frames
		//if(1 == index % 5){
		whatDoesTheAiDo(index);
		//}
		int targetY = this.targetchopper.getY();
		int targetX = this.targetchopper.getX();
		
		if (targetY > y){
			y += 1;
		}
		if (targetY < y){
			y -= 1;
		}
		return y;
	}
	
	public void whatDoesTheAiDo(int index) {
		this.shotTimer++;
		
		if(shotTimer > 50) {
			Shot shot = new Shot(x,y + (height/2) ,20,20, "gfx/bossShot.png");
			this.shots.add(shot);
			this.shotTimer = 0;
		}
	}
	
	public ArrayList<Shot> getShots() {
		return this.shots;
	}
	
	
	@Override
	public ImageIcon getImage(int index) {
		if(index >= howManyImagesToLoad){
			index = index % howManyImagesToLoad;
		}
		return images.get(index);
	}
	
	public void preLoader(){
		for(int n=1; n<=howManyImagesToLoad;n++){
			//First create the string
			tempImagePath = "gfx/Boss";
			tempImagePath += n + ".png";
			//then load the images into array
			images.add(new ImageIcon(tempImagePath));
		}
		
	}
}
