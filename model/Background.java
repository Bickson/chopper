package model;

//import java.util.ArrayList;

import javax.swing.ImageIcon;

public class Background extends Thread {
	//private ArrayList<ImageIcon> bgImage = new ArrayList<ImageIcon>();
	private int howManyImagesToLoad = 1200;
	//private String tempImagePath;
	//private ImageIcon tempImage;
	private DynamicLoader dynamicLoaderThread;


	public Background(){
		//preLoader();
		//Create thread.
		dynamicLoaderThread = new DynamicLoader();
		//StartThread:
		dynamicLoaderThread.start();
	}

	public ImageIcon getBG(int index){
		return dynamicLoaderThread.getImage(index);
	}

	//Loads a few of the images:
	public void preLoader(){

		for(int n=1; n<=howManyImagesToLoad;n++){
			//First create the string
			tempImagePath = "gfx/bgLevel1/level1Backgorundv9.";
			tempImagePath += n + ".jpg";
			//then load the images into array
			bgImage.add(new ImageIcon(tempImagePath));
		}

	}

	public String toString(){
		String info = new String();
		info += howManyImagesToLoad;
		return info;
	}
}
