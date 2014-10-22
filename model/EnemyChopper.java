package model;

import java.util.ArrayList;

import javax.swing.ImageIcon;

import controller.GamePanel;
/*
 * Represents the enemy Choppers
 */
public class EnemyChopper extends Obstacle{
	private Shot shot;


	private boolean shotFired = false;
	private ArrayList<ImageIcon> images = new ArrayList<ImageIcon>();
	private int howManyImagesToLoad = 2;
	private String tempImagePath;
	private Chopper chopper;
	private int targetX, targetY;
	private int shotTimer; // counts how long ago it fired. So it doesn't fire to often when it gets close
	private Stage stage;
	protected int life = 3;
	private static int numOfChoppers = 0; // For ai to know who it is
	private int chopperID = 0;				// For ai to know who it is
	private boolean closeToAlly = false; //To know when to steer clear of ally
	
	public EnemyChopper(int x, int y, int width, int height, Chopper chopper_, Stage stage_){
		super(x,y,width,height);
		preLoader();
		chopper = chopper_;
		//shot = new Shot();
		stage = stage_;
		chopperID=numOfChoppers;
		numOfChoppers++;
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



	/*
	 * Loads the images that belongs to enemyChopper
	 */
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
		if(x<1400)checkIfFiring(index);
		if(x<1400)checkIfCloseToAlly(index);
		if(x<1400 && closeToAlly==false)updateCurrentHeading(index);
	}
	
	/*
	 * This updates where the chopper is headed
	 */
	private void updateCurrentHeading(int index){
		if(1 == index % 10 + (int)(Math.random()*4)){ // Don't update every frame

			if (chopper.getY() -20 > y){
				//Chopper is not horizontally aligned with the player.


				//System.out.println((int)(Math.random()*10));
					targetY =  (chopper.getY() + (int)(Math.random()*10));
				}
				if (chopper.getY() + 20 < y){
					targetY = chopper.getY() - (int)(Math.random()*10);
				}
		}
	}
	
	/*
	 * Checks if close to an ally and correct course so they stay parallelso there's no overlap and greater fire range
	 */
	private void checkIfCloseToAlly(int index){

		//Check that no other enemy choppers are in the way:
		if(1 == index % 10){
			for(int i=0; i<stage.getSizeOfObstacles(); i ++){
				//Check below
				if((stage.getObstacles(i).getY() + 200 < y + 120 ) && i != chopperID && 
						(stage.getObstacles(i).getX()-160 <= x ) && (stage.getObstacles(i).getX()+160 >= x)){
					closeToAlly = true;
					//System.out.println(i + "DEBUG:  Chopper above! Chopper Id: " + chopperID + "closeToAlly: " + closeToAlly);
					targetY = y  - 250; // go up!
					break;
				}
				//Check up
				else if((stage.getObstacles(i).getY() -200  > y- 120 ) && i != chopperID &&
						(stage.getObstacles(i).getX()-160 <= x ) && (stage.getObstacles(i).getX()+160 >= x)){
					closeToAlly = true;
					//System.out.println(i + "DEBUG:  Chopper below! Chopper Id: " + chopperID 
					//		+ "closeToAlly: " + closeToAlly);
					targetY = y + 250;// go down!
					break;
				}
				else {
					closeToAlly = false;
					//System.out.println("DEBUG: Not close Chopper Id: " + chopperID + " closeToAlly: " + closeToAlly);
				}
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
