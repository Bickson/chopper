package model;

import java.util.ArrayList;

import javax.swing.ImageIcon;

public class Background extends Thread {
	private ArrayList<ImageIcon> bgImage = new ArrayList<ImageIcon>();
	private int howManyImagesToLoad = 900;
	private String tempImagePath;
	private ImageIcon tempImage;
	private DynamicLoader dynamicLoaderThread;


	public Background(){
		//preLoader();
		//Create thread.
		dynamicLoaderThread = new DynamicLoader();
		//StartThread:
		dynamicLoaderThread.start();
	}

	public ImageIcon getBG(int index){
		//only update every two frames:
		index = index / 2;

		//
		//on the fly loading
		if(index >= howManyImagesToLoad){
			index = (index % howManyImagesToLoad) +1 ;
		}
		//System.out.println("Index: " + index);
		//tempImage = dynamicLoaderThread.bgImage[index];
		//System.out.println("ImageRecieved: " + index);
		//dynamicLoaderThread.index = index;

		//return tempImage;
		return dynamicLoaderThread.getImage(index);

		/*
		//tempImagePath = "gfx/bgLevel1/level1Render";
		//This is for the long image sequence
		tempImagePath = "gfx/bgLevel1Alt/backGroundRenderv03.";
		if(index<100){
			if(index<10)tempImagePath += "00";
			else tempImagePath += "0";
		}

		tempImagePath += index + ".jpg";
		//index = index % howManyImagesToLoad +1;

		return new ImageIcon(tempImagePath);


		//if index is too big
		//if(index >= howManyImagesToLoad){
		//	index = index % howManyImagesToLoad +1;
		//	//index = howManyImagesToLoad-1;
		//}
		//return bgImage.get(index);


		//TEST with 300 frames @ 1280x720 + 157 frames @ 1024x680 = 3.5GB ram
//		if(index >= howManyImagesToLoad + 300){
//			index = index % (howManyImagesToLoad +300);
//		}
//
//		if(index <= howManyImagesToLoad){
//			tempImagePath = "gfx/bgLevel1/level1Render";
//			tempImagePath += index + ".jpg";
//			//index = index % howManyImagesToLoad +1;
//		}
//
//
//
//		if(index >= howManyImagesToLoad && index <= (howManyImagesToLoad + 300) ){
//			//index = index % howManyImagesToLoad +1;
//			//index = howManyImagesToLoad-1;
//			index -= howManyImagesToLoad;
//			tempImagePath = "gfx/bgTest/test";
//			tempImagePath += index + ".jpg";
//		}

		// END TEST


		/*
		//only update every two frames:
		index = index / 2;
		//on the fly loading
		if(index >= howManyImagesToLoad){
			index = (index % howManyImagesToLoad) +1 ;
		}

		tempImagePath = "gfx/bgLevel1Alt/backGroundRenderv03.";
		tempImagePath += index + ".jpg";
		//index = index % howManyImagesToLoad +1;

		return new ImageIcon(tempImagePath);

		//TO DO:
		// BUffer reader
		 *
		 */

	}

	//Loads a few of the images:
	public void preLoader(){

		for(int n=1; n<=howManyImagesToLoad;n++){
			//First create the string
			tempImagePath = "gfx/bgLevel1Alt/backGroundRenderv03.";
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
