package gui;

import java.awt.Dimension;

import javax.swing.JFrame;

public class HexUtilGui {
	
	private JFrame mainFrame;
	
	public HexUtilGui(){
		mainFrame = new JFrame();
		mainFrame.setTitle("HexUtil");

		mainFrame.setSize(new Dimension(800, 600));
		mainFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		
		mainFrame.setVisible(true);
		mainFrame.setLocationRelativeTo(null);
	}

	public static void main(String[] args) {
		new HexUtilGui();
	}
}
