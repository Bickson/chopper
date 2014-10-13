package model;

import javax.swing.ImageIcon;

import controller.GamePanel;

public class Shot extends Obstacle{
	//private ImageIcon image;
	//int x,y,width,height;
	
	public Shot(int x, int y, int width, int height){
		super(x,y,width,height);
		image = new ImageIcon("gfx/shot.png");
	}
	
	@Override
	public int moveY(int index) {
		return y;
	}
	@Override
	public int moveX(int index) {
		return x -= 6 + GamePanel.getDifficulty();
	}
	@Override
	public ImageIcon getImage(int index){
		return image;
	}
	
}
