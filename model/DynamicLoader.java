package model;

//import java.util.ArrayList;

import javax.swing.ImageIcon;

public class DynamicLoader extends Thread{
	//private ArrayList<ImageIcon> bgImage = new ArrayList<ImageIcon>();
	private final int howManyImagesToLoad = 1200;
	private String tempImagePath;
	private String imagePath = "gfx/bglevel1/level1Backgorundv9.";
	//private String imagePath = "gfx/OldLevel1/level1Render";
	private int currentFrame;
	private int framesLoaded;
	private final int buffer = 50;
	private ImageIcon[] bgImage = new ImageIcon[howManyImagesToLoad+1]; 
	private int lastFrameCleared = buffer;
	private boolean clearLastFrames = false;
	
	
	
	//Constructor:
	public DynamicLoader(){

		
	}
	
	/*
	 * @see java.lang.Thread#run()
	 */
	public void run(){
		//Load First Buffer
		preLoadBuffer();
			//Start Endless loop:
			while(true){		
				if(framesLoaded < (currentFrame + buffer)){
					clearUsedFrames();
					loadBufferFrames();
				}
				//If no frames needs to be load sleep.
				else {
					threadLoaderSleep();
				}
			}
		}
	
	/*
	 * Loads the initial amount of frames that makes up the buffer
	 */
	private void preLoadBuffer(){
		//System.out.println("DEBUG: Thread Started");
		for(int i=1; i<buffer; i++){
			//This is for the long test image sequence
			tempImagePath = imagePath;
			//(if(i<100){
			//	if(i<10)tempImagePath += "00";
			//	else tempImagePath += "0";
			//}
			
			tempImagePath += i + ".jpg";
			bgImage[i] =  new ImageIcon(tempImagePath);
			//System.out.println("loadBufferFrames: Loading frame " + tempImagePath + " at: " + i + " Index:" + i );
			
		}
		framesLoaded = buffer;
		//System.out.println("DEBUG: Thread first 50 frames loaded");
	}
	
	
	/*
	 * Clear out used frames
	 */
	private void clearUsedFrames(){
		
		//leave the first frames in there for when it loops
		for(int i=lastFrameCleared; i < (currentFrame -1) ; i++){
			
			//System.out.println("DEBUG: clearingFrames: " + i);
			bgImage[i]=null;
			lastFrameCleared = currentFrame -1;
		}
		
		
		//If image sequence loops clear last frames:
		if(clearLastFrames==true){
			
			for(int i=howManyImagesToLoad; i >(howManyImagesToLoad -1 -buffer);i--){
				bgImage[i]=null;
				//System.out.println("DEBUG: clearingFrames: " + i);	
			}
			clearLastFrames = false;
		}
		
	}

	/*
	 * Check if frames needs to be loaded
	 */
	private void loadBufferFrames(){
		//TO DO: Try and catch when loading frames
		//Load frames
		for(; framesLoaded<(currentFrame + buffer); framesLoaded++){
			
			
			tempImagePath = imagePath;
			//This is for the long test image sequence
			//if(framesLoaded<100){
			//	if(framesLoaded<10)tempImagePath += "00";
			//	else tempImagePath += "0";
			//}
			
			tempImagePath += framesLoaded + ".jpg";
			
			//Load image
			if(framesLoaded <  howManyImagesToLoad+1){
				//System.out.println("loadBufferFrames: Loading frame " + tempImagePath + " at: " + framesLoaded);
				bgImage[framesLoaded] =  new ImageIcon(tempImagePath);
			}
		}
	}
	
	/*
	 * Makes the thread sleep
	 */
	private void threadLoaderSleep(){
		try{
			//System.out.println("DEBUG: Thread Sleeping");
			Thread.sleep(1000);
			//System.out.println("DEBUG: Thread Done Sleeping");
		}
		catch (InterruptedException ie){}
	}
	
	
	
	/*
	 * Fetches the image from the Image array and also checks if the buffers last images needs to be cleared.
	 */
	public ImageIcon getImage(int index_){
		//only update every two frames:
		index_ = index_ / 2;
		
		//Checks if the images has looped and started on from 1150 again.
		//if(index_ > howManyImagesToLoad){
		//	index_ = (index_ % 50) +1150 );
		//	System.out.println("DEBUG: Image to load: " + index_);
		//}
		
		//Loops the image sequence if it goes over
		if(index_ >= howManyImagesToLoad){
			index_ = (index_ % howManyImagesToLoad) +1;
			//System.out.println("DEBUG: Image to load: " + index_);
		}
		
		//If so it clears the last images in the Image array
		if(currentFrame > index_ ) {
			framesLoaded = buffer;
			
			clearLastFrames = true;
		}
		currentFrame = index_;
		//System.out.println("DEBUG: getImage: currentFrame: " + currentFrame + " index: " + index_);
		return bgImage[index_];
	}
	
}
