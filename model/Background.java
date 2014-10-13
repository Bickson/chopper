package model;

import java.util.ArrayList;

import javax.swing.ImageIcon;

public class Background extends Thread {
	private ArrayList<ImageIcon> bgImage = new ArrayList<ImageIcon>();
	private int howManyImagesToLoad = 300;
	private String tempImagePath;
	
	public Background(){
		//preLoader();
	}
	
	public ImageIcon getBG(int index){
		
		
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
		
		//only update every two frames:
		index = index / 2;
		//on the fly loading
		if(index >= howManyImagesToLoad){
			index = (index % howManyImagesToLoad) +1 ;
		}
		tempImagePath = "gfx/bgLevel1/level1Render";
		tempImagePath += index + ".jpg";
		//index = index % howManyImagesToLoad +1;
		
		return new ImageIcon(tempImagePath);
		
		//TO DO:
		// BUffer reader
		
	}
	
	//Loads a few of the images:
	public void preLoader(){
		
		for(int n=1; n<=howManyImagesToLoad;n++){
			//First create the string
			tempImagePath = "gfx/bgLevel1/level1Render";
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
