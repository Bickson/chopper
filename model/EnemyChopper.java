package model;

import java.util.ArrayList;

import javax.swing.ImageIcon;

import controller.GamePanel;

public class EnemyChopper extends Obstacle{
	private Shot shot;
	//private ArrayList<Shot> shots;
	private boolean shotFired = false;
	private ArrayList<ImageIcon> images = new ArrayList<ImageIcon>();
	private int howManyImagesToLoad = 2;
	private String tempImagePath;
	private Chopper chopper;
	private int targetX, targetY;
	private int shotTimer; // counts how long ago it fired. So it doenst fire to often when it gets close
	private Stage stage;
	protected int life = 3;
	
	public EnemyChopper(int x, int y, int width, int height, Chopper chopper_, Stage stage_){
		super(x,y,width,height);
		preLoader();
		chopper = chopper_;
		//shot = new Shot();
		stage = stage_;
	}
	@Override
	public Shot getShot(){
		if(shotFired == true){
			return shot;
		}
		return null;
	}
	
	public void setShotfire(boolean value) {
		this.shotFired = value;
	}
	
	public int getLife() {
		return this.life;
	}


	@Override
	public int moveX(int index) {
		//Push back the chopper if its too far back:
		if(x < -1000)x = 1500;
		return x -= 1 + GamePanel.getDifficulty();
	}
	
	
	@Override
	public int moveY(int index) {
		//update AI every 5 frames
		//if(1 == index % 5){
			whatDoesTheAiDo(index);
		//}
		if (targetY > y){
			y += 1 + GamePanel.getDifficulty();
		}
		if (targetY < y){
			y -= 1 + GamePanel.getDifficulty() ;
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
				//Chopper is not horizontally aligned with the player.
				//Also check that no other enemy choppers are in the way:
				//for(int i=0; i<stage.getSizeOfObstacles(); i ++){
				//	if(stage.getObstacles(i).getY(1) +30 > y && stage.getObstacles(i).getY(1) -30 < y){
				//		
				//	}
				//}
				//System.out.println((int)(Math.random()*10));
					targetY =  (chopper.getY() + (int)(Math.random()*10));
				}
				if (chopper.getY() + 20 < y){
					targetY = chopper.getY() - (int)(Math.random()*10);
				}
		}
		

	}
}
