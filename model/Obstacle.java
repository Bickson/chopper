package model;

import java.awt.*;
import java.util.ArrayList;

import javax.swing.ImageIcon;

public class Obstacle {
	
	protected ImageIcon image;
	protected int x,y,width,height;
	//protected Shot shot;
	protected ArrayList<Shot> shots;
	public Obstacle (int x, int y, int width, int height) {
		this.x = x;
		this.y = y;
		this.width = width;
		this.height = height;
		this.shots = new ArrayList<Shot>();
	}
	
	/* public void setImage();
	public ImageIcon getImage() {
		return image;
	}*/
	
	public int getLife() {
		return 0;
	}
	
	public ImageIcon getImage(int index){
		return image;
	}
	
	public int moveX(int index) {
		return x;
	}
	
	public int moveY(int index) {
		return y;
	}
	public int getX(int index) {
		return x;
	}
	
	public int getY(int index) {
		return y;
	}
	
	// Overload
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
	
	public void addToX(int i) {
		this.x += i;
	}
	
	public void addToY(int i) {
		this.y += i;
	}
	
	public ArrayList<Shot> getShots(){
		return this.shots;
	}
}
