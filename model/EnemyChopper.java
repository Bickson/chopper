package model;

import java.util.ArrayList;

import javax.swing.ImageIcon;

import controller.GamePanel;

public class EnemyChopper extends Obstacle{
	private Shot shot;
	//private ArrayList<Shot> shots;
	//To do: Change shot to arrayList
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
		if(x < -260)x = 2000;
		return x -= 2 + GamePanel.getDifficulty();
	}


	@Override
	public int moveY(int index) {
		//update AI every 5 frames
		//if(1 == index % 5){
			if(x> 30 && x<1050)whatDoesTheAiDo(index);
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
	
	/*
	 * Decides what the AI does
	 */
	private void whatDoesTheAiDo(int index){
		checkIfFiring(index);
		updateCurrentHeading(index);

	}
	
	/*
	 * This updates where the chopper is headed
	 */
	private void updateCurrentHeading(int index){
		if(1 == index % 5 + (int)(Math.random()*4)){ // Don't update every frame

			if (chopper.getY() -20 > y){
				//Chopper is not horizontally aligned with the player.

				//Also check that no other enemy choppers are in the way:
				//Check above
				for(int i=0; i<stage.getSizeOfObstacles(); i ++){
					if(stage.getObstacles(i).getY(1) + stage.getObstacles(i).getHeight() + 10> y
							&& stage.getObstacles(i).getY(1) -  stage.getObstacles(i).getHeight() - 10 < y){
						targetY = y -10;
					}
				}
				//System.out.println((int)(Math.random()*10));
					targetY =  (chopper.getY() + (int)(Math.random()*10));
				}
				if (chopper.getY() + 20 < y){
					targetY = chopper.getY() - (int)(Math.random()*10);
				}
		}
	}
	
/*
 * Checks if the chopper is in within fire range. 
 * Also resets shotFired so it can fire again
 */
	private void checkIfFiring(int index){
		//This Checks if the chopper is in within fire range
		if(chopper.getY() +50 > y && chopper.getY() -50 < y && x < 800){
		//Fire shot!!!
			if(shotFired == false){
			//if(shot == null){
			shot = new Shot(x,y + (height/2) ,20,20);
				shotFired = true;
			}
		}
		shotTimer++;
		//check if shot is out of the screen . And reset if it is
		if(shotFired == true && shotTimer > 70){
			if(shot.getX(index) <= 0){
				shotFired = false;
				shotTimer = 0;
			}
		}
	}
	
	

}
