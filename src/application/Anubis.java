package application;

import java.awt.*;
import java.awt.event.KeyEvent;
import java.util.ArrayList;

import javax.swing.ImageIcon;

public class Anubis {
	int x, dx, y, nx, nx2, left, dy;
	Image still, jump, reverse;

	int ammo = 25;

	ImageIcon rightSprite = new ImageIcon("C:/eclipseJP/workspaceJava/Assignment3/src/Anubis_Running_003.png");
	ImageIcon jumpSprite = new ImageIcon("C:/eclipseJP/workspaceJava/Assignment3/src/Anubis_Jump Start_005.png");
	ImageIcon leftSprite = new ImageIcon("C:/eclipseJP/workspaceJava/Assignment3/src/Anubis_Running_3-L.png");

	static ArrayList ankhs;

	public Anubis() {
		x = 75;
		left = 150;
		nx = 0;
		nx2 = 685;
		y = 172;
		still = rightSprite.getImage();
		ankhs = new ArrayList();

	}

	public Rectangle getBounds() {
		return new Rectangle(left, y, 63, 154);
	}

	public static ArrayList getAnkhs() {
		return ankhs;
	}

	public void fire() {
		if (ammo > 0) {
			ammo--;
			// The v is from the board class, which corresponds to the character's
			// position when it is jumping, resulting in the bullet being formed
			// at a higher position when the character is at the peak of its jump
			Ankh z = new Ankh((left + 60), (Board.v + 100 / 2));
			ankhs.add(z);
		}
	}

	public void move() {
		if (dx != -1) {
			if (left + dx <= 150)
				left += dx;
			else {
				x = x + dx;

				nx2 = nx2 + dx;
				nx = nx + dx;
			}
		} else {
			if (left + dx > 0)
				left = left + dx;
		}
	}

	public int getX() {
		return x;
	}

	public int getLeft() {
		return left;
	}

	public int getnX() {
		return nx;
	}

	public int getnX2() {
		return nx2;
	}

	public int getdx() {
		return dx;
	}

	public Image getImage() {
		return still;
	}

	public void keyPressed(KeyEvent e) {
		int key = e.getKeyCode();
		
		if (key == KeyEvent.VK_LEFT) {
			dx = -1;
			still = leftSprite.getImage();
		}

		if (key == KeyEvent.VK_RIGHT) {
			dx = 1;
			still = rightSprite.getImage();
		}

		if (key == KeyEvent.VK_SPACE) {
			fire();
		}

		if (key == KeyEvent.VK_UP) {
			dy = 1;
			still = jumpSprite.getImage();
		}
		
		if (key == KeyEvent.VK_A) {
			dx = -1;
			still = leftSprite.getImage();
		}

		if (key == KeyEvent.VK_D) {
			dx = 1;
			still = rightSprite.getImage();
		}

		if (key == KeyEvent.VK_E) {
			fire();
		}

		if (key == KeyEvent.VK_W) {
			dy = 1;
			still = jumpSprite.getImage();
		}
		
		if (key == KeyEvent.VK_ESCAPE) {
			System.exit(0);
		}
	}

	public void keyReleased(KeyEvent e) {
		int key = e.getKeyCode();

		if (key == KeyEvent.VK_LEFT)
			dx = 0;

		if (key == KeyEvent.VK_RIGHT)
			dx = 0;

		if (key == KeyEvent.VK_UP) {
			dy = 0;
			still = rightSprite.getImage();
		}
		
		if (key == KeyEvent.VK_A)
			dx = 0;

		if (key == KeyEvent.VK_D)
			dx = 0;

		if (key == KeyEvent.VK_W) {
			dy = 0;
			still = rightSprite.getImage();
		}
	}
}