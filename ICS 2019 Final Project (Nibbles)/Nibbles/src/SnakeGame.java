import javax.swing.*;
import java.awt.Graphics;
import java.awt.*;
import java.awt.event.*;
import javax.swing.ImageIcon;
import java.awt.Color;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyListener;
import javax.swing.JPanel;
import javax.swing.Timer;
import java.awt.FlowLayout;
import javax.swing.JButton;
import javax.swing.JFrame;

public class SnakeGame extends JFrame implements KeyListener, ActionListener {

	// Declaration section
	private static final int WINDOWWIDTH = 1280;
	private static final int WINDOWHEIGHT = 990;
	private static final int SNAKEWIDTH = 10;
	final static int PIXELSIZE = 10;
	
	private int xValue;
	private int yValue;
	private int xVelocity;
	private int yVelocity;
	
	private int appleX;
	private int appleY;
	
	Timer t;
	
	// Constructor
	public SnakeGame() {
		super("I am speed"); // name the window
		xValue = 100; // set x and y coordinates of ball in middle of screen
		yValue = WINDOWHEIGHT / 2;
		xVelocity = 0; // set speed of ball to 0 (not moving)
		yVelocity = 0;
		addKeyListener(this); // start listening for keyboard inputs within the current class
		setFocusable(true);
		setFocusTraversalKeysEnabled(false);
		t = new Timer (1, this);
		t.start();
	}
	
	public void locateApple (Graphics g) {
		appleX = (int)(Math.random()*1279) + 1;
		appleY = (int)(Math.random()*989) + 1;
		g.setColor(Color.RED);
		g.drawOval(appleX, appleY, 50, 50);
	}
	
	
	
	
	
	
	
	
	
	
	
	
	// keyTyped is one of 3 pre-made methods in KeyListener. Since we implemented
	// KeyListener we MUST write out all three of them. KeyTyped checks for the
	// characters being pressed on keyboard
	public void keyTyped(KeyEvent e) {

		// if the user enters 'd' then the snake will move to the right.
		if (e.getKeyChar() == 'd' || e.getKeyCode() == KeyEvent.VK_RIGHT) {
			xVelocity = PIXELSIZE; // Makes the snake go right.
			yVelocity = 0; // Prevents the snake from going diagonal.
		}

		// if the user enters 'a' then the snake will move to the left.
		else if (e.getKeyChar() == 'a' || e.getKeyCode() == KeyEvent.VK_LEFT) {
			xVelocity = PIXELSIZE * -1; // Makes the snake go left.
			yVelocity = 0; // Prevents the snake from going diagonal.
		}

		// if the user enters 'w' the snake will move up.
		else if (e.getKeyChar() == 'w' || e.getKeyCode() == KeyEvent.VK_UP) {
			xVelocity = 0; // Prevents the snake from going diagonal.
			yVelocity = PIXELSIZE * -1; // Makes the snake go up.
		}

		// if the user enters 's' the snake will move down.
		else if (e.getKeyChar() == 's' || e.getKeyCode() == KeyEvent.VK_DOWN) {
			xVelocity = 0; // Prevents the snake from going diagonal.
			yVelocity = PIXELSIZE; // Makes the snake go down.
		}
	}

	// When the direction key is removed the snake continues to go the direction
	// that was last pressed.
	public void keyReleased(KeyEvent e) {

		// The snake continues going right.
		if (e.getKeyChar() == 'd' || e.getKeyCode() == KeyEvent.VK_RIGHT) {
			xVelocity = PIXELSIZE; // Makes the snake go right.
			yVelocity = 0; // Prevents the snake from going diagonal.
		}

		// The snake continues going left.
		if (e.getKeyChar() == 'a' || e.getKeyCode() == KeyEvent.VK_LEFT) {
			xVelocity = -PIXELSIZE; // Makes the snake go left.
			yVelocity = 0; // Prevents the snake from going diagonal.
		}

		// The snake continues going up.
		if (e.getKeyChar() == 'w' || e.getKeyCode() == KeyEvent.VK_UP) {
			xVelocity = 0; // Prevents the snake from going diagonal.
			yVelocity = -PIXELSIZE; // Makes the snake go up.
		}

		// The snake continues going down.
		if (e.getKeyChar() == 's' || e.getKeyCode() == KeyEvent.VK_DOWN) {
			xVelocity = 0; // Prevents the snake from going diagonal.
			yVelocity = PIXELSIZE; // Makes the snake go down.
		}
	}

	public void keyPressed(KeyEvent e) { // third method from KeyListener that MUST be typed. We don't use it so it's //
											// empty. keyPressed is the same as keyTyped except that keyPressed checks
											// for ASCII codes, while keyTyped checks for characters
	}

	public void paint(Graphics g) { // overridden paint method from JFrame
		//super.paintComponents(g); // clears the screen of previous shapes (try deleting this and see what happens)
		movement();// method we made ourselves below. Customary to not put everything in paint()
		g.fillOval(xValue, yValue, SNAKEWIDTH, SNAKEWIDTH); // draw oval at given location and size
		locateApple(g);
	}

	// BREAK IF OUT OF BOUNDS
	public void movement() {
		if (xValue < 0) { // keep ball from going off screen to the left
			xValue = 1;
		} else if (xValue > WINDOWWIDTH - SNAKEWIDTH) { // keep ball from going off screen to the right
			xValue = WINDOWWIDTH - SNAKEWIDTH;
		} else { // if on screen, move ball horizontally
			xValue += xVelocity;
		}
		if (yValue < 0) {
			yValue = 1;
		} else if (yValue > WINDOWHEIGHT - SNAKEWIDTH) {
			yValue = WINDOWHEIGHT - SNAKEWIDTH;
		} else {
			yValue += yVelocity; // vertical movement unimpeded - no check to see if off screen
		}
	}

	public static void main(String[] args) {
		SnakeGame frame = new SnakeGame();
		frame.setSize(WINDOWWIDTH, WINDOWHEIGHT); // set frame size
		frame.setLocationRelativeTo(null); // center frame on screen
		frame.setResizable(false); // impossible to change size of frame
		frame.setVisible(true); // make frame visible
		frame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE); // frame can be closed
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		// TODO Auto-generated method stub
		repaint();
	}

}

