package model;

import java.util.ArrayList;

import javax.swing.ImageIcon;

public class Chopper {
	
	//private ImageIcon image = new ImageIcon("gfx/Image001.png");
	private ArrayList<ImageIcon> images = new ArrayList<ImageIcon>();
	private int howManyImagesToLoad = 2;
	private String tempImagePath;
	//= new ImageIcon("gfx/heliAlpha1.png");
	private int height,width,x,y;
	private ArrayList<Shot> shots = new ArrayList<Shot>();
	
	public Chopper() {
		y = 100;
		preLoader();
	}
	
	public ImageIcon getImage(int index) {
		if(index >= howManyImagesToLoad){
			index = index % howManyImagesToLoad;
		}
		return images.get(index);
	}
	
	public int getX() {
		return x;
	}
	
	public int getY() {
		return y;
	}
	
	public int getHeight() {
		return height;
	}
	
	public int getWidth() {
		return width;
	}
	
	public void addToY(int i) {
		this.y += i;
	}
	
	//Loads a few of the images:
	public void preLoader(){
		
		for(int n=1; n<=howManyImagesToLoad;n++){
			//First create the string
			tempImagePath = "gfx/heliAlpha";
			tempImagePath += n + ".png";
			//then load the images into array
			images.add(new ImageIcon(tempImagePath));
		}
		
	}
	
	public ArrayList<Shot> getShots() {
		return this.shots;
	}
	
	public void addShot() {
		int y = this.getY() + 50; 
		int x = this.getImage(0).getIconWidth() - 40;
//<<<<<<< HEAD
		this.shots.add(new Shot(x,y,20,20,"gfx/shot2.png"));
//=======
		//this.shots.add(new Shot(x,y,35,4,"gfx/shot.png"));
//>>>>>>> Added a dynamic background image loader.
	}
	//Removes the shot
	public void removeShot(Shot shot) {
		this.shots.remove(shot);
	}
	
}
