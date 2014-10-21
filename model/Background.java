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

	public String toString(){
		String info = new String();
		info += howManyImagesToLoad;
		return info;
	}
}
