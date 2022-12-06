package application;

import java.awt.*;
import java.awt.event.*;
import java.util.ArrayList;

import javax.swing.*;

public class Board extends JPanel implements ActionListener, Runnable {
	Anubis player;
	public Image img;
	Timer time;
	static int v = 172;
	Thread animator;
	Enemy enemyOne;
	Enemy enemyTwo;

	boolean lost = false;
	int deaths = 0;

	boolean a = false;
	boolean done2 = false;

	static Font font = new Font("SanSerif", Font.BOLD, 24);
	static Font over = new Font("SanSerif", Font.BOLD, 50);

	public Board() {
		player = new Anubis();
		addKeyListener(new AL());
		setFocusable(true);
		ImageIcon backgroundImage = new ImageIcon(
				"C:/eclipseJP/workspaceJava/Assignment3/src/Volcano Level Set_Background - Layer 00.png");
		img = backgroundImage.getImage();
		time = new Timer(5, this);
		time.start();
		
		enemyOne = new Enemy(700, 200, "C:/eclipseJP/workspaceJava/Assignment3/src/Dragon_Idle_001.png");
		enemyTwo = new Enemy(700, 200, "C:/eclipseJP/workspaceJava/Assignment3/src/Dragon_Idle_001.png");
	}
	
//	public void enemySpawner() {
//		enemyOne = new Enemy(700, 200, "C:/eclipseJP/workspaceJava/Assignment3/src/Dragon_Idle_001.png");
//		enemyTwo = new Enemy(700, 200, "C:/eclipseJP/workspaceJava/Assignment3/src/Dragon_Idle_001.png");
//	}

	public void actionPerformed(ActionEvent e) {
		checkCollisions();
		ArrayList ankhs = Anubis.getAnkhs();
		for (int w = 0; w < ankhs.size(); w++) {
			Ankh m = (Ankh) ankhs.get(w);
			if (m.getVisible() == true) {
				m.move();
			}
			else {
				ankhs.remove(w);
			}
		}

		player.move();

		if (player.x > 400) {
			enemyOne.move(player.getdx(), player.getLeft()); }
		
		if (player.x > 500) {
			enemyTwo.move(player.getdx(), player.getLeft());
		}
		repaint();
	}

	public void checkCollisions() {
		Rectangle r1 = enemyOne.getBounds();
		Rectangle r2 = enemyTwo.getBounds();
		ArrayList ankhs = Anubis.getAnkhs();
		for (int w = 0; w < ankhs.size(); w++) {
			Ankh m = (Ankh) ankhs.get(w);
			Rectangle m1 = m.getBounds();
			if (r1.intersects(m1) && enemyOne.Alive()) {
				enemyOne.isAlive = false;
				m.visible = false;
			} else if (r2.intersects(m1) && enemyTwo.Alive()) {
				enemyTwo.isAlive = false;
				m.visible = false;
			}
		}

		Rectangle d = player.getBounds();
		if ((d.intersects(r1) && enemyOne.Alive()) || (d.intersects(r2) && enemyTwo.Alive())) {
			lost = true;
		}

	}

	public void Restart(){
//	public void Restart(Graphics g){
		JFrame popup = new JFrame();
		popup.add(new Board());	
		popup.setTitle("Anubis Run: " + deaths + " deaths");
		popup.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		popup.setSize(700,365);
		popup.setVisible(true);
		popup.setLocationRelativeTo(null);
//		super.paint(g);
//		Graphics2D graphics2d = (Graphics2D) g;
//		graphics2d.setFont(font);
//		graphics2d.setColor(Color.RED);
//		graphics2d.drawString("Anubis Run: " + deaths + " deaths", 500, 20);
		time.stop();
	}
	
	public void paint(Graphics g) {
		super.paint(g);
		Graphics2D graphics2d = (Graphics2D) g;
		
		if (lost) {
			graphics2d.setFont(over);
			graphics2d.setColor(Color.RED);
			graphics2d.drawString("GAME OVER", 220, 150);
			System.out.println("GAME OVER");
			deaths++;
//			frame.setTitle("Anubis Run: " + deaths + " deaths");
//			Restart(g);
//			Restart();
			
			graphics2d.drawImage(img, player.getnX2(), 0, null);
			
			time.stop();
//			System.exit(0);
		}
		else {
			if (player.dy == 1 && done2 == false) {
				done2 = true;
				animator = new Thread(this);
				animator.start();
			}

			if ((player.getX() - 590) % 2400 == 0) {
				player.nx = 0;
			}
			if ((player.getX() - 1790) % 2400 == 0) {
				player.nx2 = 0;
			}

			graphics2d.drawImage(img, 685 - player.getnX2(), 0, null);
			if (player.getX() > 590) {
				graphics2d.drawImage(img, 685 - player.getnX(), 0, null);
			}
			graphics2d.drawImage(player.getImage(), player.left, v, null);

			if (player.getdx() == -1) {
				graphics2d.drawImage(img, 685 - player.getnX2(), 0, null);
				graphics2d.drawImage(player.getImage(), player.left, v, null);
			}

			ArrayList ankhs = Anubis.getAnkhs();
			for (int w = 0; w < ankhs.size(); w++) {
				Ankh m = (Ankh) ankhs.get(w);
				if (m.getVisible()) {
					graphics2d.drawImage(m.getImage(), m.getX(), m.getY(), null);
				}
			}
			graphics2d.setFont(font);
			graphics2d.setColor(Color.BLUE);
			graphics2d.drawString("Ammo left: " + player.ammo, 500, 20);
			if (player.x > 400) {
				if (enemyOne.Alive() == true) {
					graphics2d.drawImage(enemyOne.getImage(), enemyOne.getX(), enemyOne.getY(), null);
				}
			}
			if (player.x > 500) {
				if (enemyTwo.Alive() == true) {
					graphics2d.drawImage(enemyTwo.getImage(), enemyTwo.getX(), enemyTwo.getY(), null);
				}
			}
		}
	}

	private class AL extends KeyAdapter {
		public void keyReleased(KeyEvent e) {
			player.keyReleased(e);
		}

		public void keyPressed(KeyEvent e) {
			player.keyPressed(e);
		}
	}

	boolean h = false;
	boolean done = false;

	public void cycle() {

		if (h == false) {
			v--;
		}
		if (v == 125) {
			h = true;
		}
		if (h == true && v <= 172) {
			v++;
			if (v == 172) {
				done = true;
			}
		}
	}

	public void run() {

		long beforeTime, timeDiff, sleep;

		beforeTime = System.currentTimeMillis();

		while (done == false) {

			cycle();

			timeDiff = System.currentTimeMillis() - beforeTime;
			sleep = 10 - timeDiff;

			if (sleep < 0) {
				sleep = 2;
			}
			try {
				Thread.sleep(sleep);
			} catch (InterruptedException e) {
				System.out.println("sleep error");
			}

			beforeTime = System.currentTimeMillis();
		}
		done = false;
		h = false;
		done2 = false;
	}

}