package model;

import java.util.ArrayList;

import javax.swing.ImageIcon;

public class DynamicLoader extends Thread{
	//private ArrayList<ImageIcon> bgImage = new ArrayList<ImageIcon>();
	private final int howManyImagesToLoad = 900;

	//private int imagesLeftToLoad = 0;
	private String tempImagePath;
	private int currentFrame;
	private int framesLoaded;
	private final int buffer = 50;
	private int index = 0;
	//private ImageIcon[] bgImage = new ImageIcon[howManyImagesToLoad+buffer+1]; 
	private ImageIcon[] bgImage = new ImageIcon[howManyImagesToLoad+1]; 
	
	private boolean clearLastFrames = false;
	public DynamicLoader(){
		//imagesLeftToLoad = howManyImagesToLoad;
	}
	
	
	
	public void run(){
		
			//Load First Buffer
			//System.out.println("DEBUG: Thread Started");
			for(int i=0; i<buffer; i++){
				//This is for the long image sequence
				tempImagePath = "gfx/bgLevel1Alt/backGroundRenderv03.";
				if(i<100){
					if(i<10)tempImagePath += "00";
					else tempImagePath += "0";
				}
				
				tempImagePath += i + ".jpg";
				bgImage[i] =  new ImageIcon(tempImagePath);
				
			}
			
			framesLoaded = buffer;
			//System.out.println("DEBUG: Thread first 50 frames loaded");
			//Start Endless loop:
			while(true){
				//If frames needs to be loaded
				if(framesLoaded < (currentFrame + buffer)){
					//System.out.println("DEBUG: framesLoaded: " + framesLoaded
					//		+ " CurrentFrame: " + currentFrame);
			
					//Clear out used frames
					//leave the first frames in there for when it loops
					for(int i=buffer; i<(currentFrame);i++){
						//System.out.println("DEBUG: clearingFrames: " + i);
						bgImage[i]=null;
					}
					
					//If image sequence loops clear last frames:
					if(clearLastFrames==true){
						
						for(int i=howManyImagesToLoad;i>(howManyImagesToLoad+1-buffer);i--){
							bgImage[i]=null;
							System.out.println("DEBUG: clearingFrames: " + i);	
						}

						clearLastFrames = false;
					}
					
					//Load frames
					for(; framesLoaded<(currentFrame + buffer); framesLoaded++){
						
						//This is for the long image sequence
						tempImagePath = "gfx/bgLevel1Alt/backGroundRenderv03.";
						if(framesLoaded<100){
							if(framesLoaded<10)tempImagePath += "00";
							else tempImagePath += "0";
						}
						
						tempImagePath += framesLoaded + ".jpg";
						//Load image
						if(framesLoaded <  howManyImagesToLoad+1)bgImage[framesLoaded] =  new ImageIcon(tempImagePath);
					}

					
				} // End if frames needs to be loaded:
				else {
					try{
						//System.out.println("DEBUG: Thread Sleeping");
						Thread.sleep(1000);
						//System.out.println("DEBUG: Thread Done Sleeping");
					}
					catch (InterruptedException ie){
					}
				}
				
			}// End of endless loop
		}

	public ImageIcon getImage(int index_){
		//System.out.println("DEBUG: currentFrame: " + currentFrame + " index: " + index_);
		if(currentFrame > index_ ) {
			framesLoaded = buffer;
			clearLastFrames = true;
		}
		currentFrame = index_;
		return bgImage[index_];
	}
	
}
