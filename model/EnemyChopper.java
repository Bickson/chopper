package model;

import java.util.ArrayList;

import javax.swing.ImageIcon;

import controller.GamePanel;

public class EnemyChopper extends Obstacle{
	private Shot shot;
	private boolean shotFired = false;
	private ArrayList<ImageIcon> images = new ArrayList<ImageIcon>();
	private int howManyImagesToLoad = 2;
	private String tempImagePath;
	private Chopper chopper;
	private int targetX, targetY;
	private int shotTimer; // counts how long ago it fired. So it doenst fire to often when it gets close
	
	public EnemyChopper(int x, int y, int width, int height, Chopper chopper_){
		super(x,y,width,height);
		preLoader();
		chopper = chopper_;
		//shot = new Shot();
	}
	@Override
	public Shot getShot(){
		if(shotFired == true){
			return shot;
		}
		return null;
	}


	@Override
	public int moveX(int index) {
		return x -= 2 + GamePanel.getDifficulty();
	}
	
	
	@Override
	public int moveY(int index) {
		//update AI every 5 frames
		//if(1 == index % 5){
			whatDoesTheAiDo(index);
		//}
		if (targetY > y){
			y += 1 ;
		}
		if (targetY < y){
			y -= 1;
		}
		return y;
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
			tempImagePath = "gfx/heliAlpha";
			tempImagePath += n + ".png";
			//then load the images into array
			images.add(new ImageIcon(tempImagePath));
		}
		
	}
	
	private void whatDoesTheAiDo(int index){
		//update AI every * frames
		if(chopper.getY() +50 > y && chopper.getY() -50 < y && x < 800){
			
		//if(chopper.getY() )
		//Fire shot!!!
			if(shotFired == false){
			//if(shot == null){
			shot = new Shot(x,y + (height/2) ,20,20);
				shotFired = true;
				//System.out.println("DEBUG: Fire!!");
				//System.out.println("DEBUG: Fire!!");
			}
			//System.out.println("DEBUG: chopperY: " + chopper.getY() + " enemyChopper Y: " + y );
		}
		shotTimer++;
		//check if shot is out.
		if(shotFired == true && shotTimer > 70){
			if(shot.getX(index) <= 0){
				shotFired = false;
				shotTimer = 0;
			}
		}

		if(1 == index % 5 + (int)(Math.random()*4)){
		//if(1 == index % 5){
			
			if (chopper.getY() -20 > y){
				//System.out.println((int)(Math.random()*10));
					targetY =  (chopper.getY() + (int)(Math.random()*10));
				}
				if (chopper.getY() + 20 < y){
					targetY = chopper.getY() - (int)(Math.random()*10);
				}
		}
		

	}
}
