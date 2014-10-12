package model;

import java.util.ArrayList;

import javax.swing.ImageIcon;

public class Background extends Thread {
	private ArrayList<ImageIcon> bgImage = new ArrayList<ImageIcon>();
	private int howManyImagesToLoad = 157;
	private String tempImagePath;
	
	public Background(){
		//preLoader();
	}
	
	public ImageIcon getBG(int index){
		
		
		//if index is too big
		if(index >= howManyImagesToLoad){
			index = index % howManyImagesToLoad +1;
			//index = howManyImagesToLoad-1;
		}
		//return bgImage.get(index);
		
		//on the fly loading
		tempImagePath = "gfx/bgLevel1/level1Render";
		tempImagePath += index + ".jpg";
		return new ImageIcon(tempImagePath);
		
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
	
}
