package model;

import java.awt.*;

import javax.swing.ImageIcon;

public class Obstacle {
	
	ImageIcon image;
	int x,y,width,height;
	
	public Obstacle (int x, int y, int width, int height) {
		this.x = x;
		this.y = y;
		this.width = width;
		this.height = height;
	}
	
	/* public void setImage();
	public ImageIcon getImage() {
		return image;
	}*/
	
	public ImageIcon getImage(int index){
		return image;
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
	
	public void addToX(int i) {
		this.x += i;
	}
	
	public void addToY(int i) {
		this.y += i;
	}
	
}
