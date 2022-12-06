package application;

import javax.swing.*;


public class Main {

	public Main(){
		JFrame frame = new JFrame();
		frame.add(new Board());	
		frame.setTitle("Anubis Run");
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setSize(700,365);
		frame.setVisible(true);
		frame.setLocationRelativeTo(null);
	}
	public static void main(String[] args){
		new Main();
	}
}