package model;

//import java.util.ArrayList;

import javax.swing.ImageIcon;
/*
 * This starts the thread that loads the background images into memory and act as a middle between them.,
 */
public class Background extends Thread {
	private int howManyImagesToLoad = 1200;
	private DynamicLoader dynamicLoaderThread;

	public Background(){
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
