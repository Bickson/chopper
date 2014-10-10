package model;

import javax.swing.ImageIcon;

public class Chopper {
	
	private ImageIcon image = new ImageIcon("gfx/Image001.png");
	private int height,width,x,y;
	
	public Chopper() {
		y = 100;
	}
	
	public ImageIcon getImage() {
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
	
	public void addToY(int i) {
		this.y += i;
	}
}
