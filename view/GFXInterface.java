package view;

import javax.swing.JFrame;
import javax.swing.JMenuBar;

import controller.*;

public class GFXInterface {
	
	public GFXInterface() {
		
	}
	
	public void run() {
		JFrame frame = new JFrame("Chopper");
		GamePanel panel = new GamePanel();
		
		frame.add(panel);
		JMenuBar menu = panel.createMenu();
		frame.setJMenuBar(menu);
		
		frame.setSize(600,300);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setVisible(true);
		
	}
	

}
