/*File Name: Snake.java
Programmers: Anson, Bobby
Class: ICS 3U7 Mr Anthony
Date: Thursday June 14th, 2019
Purpose: This is the snake class that gives properties to the snakes in the SolosBoard class
         and DuosBoard class. In this class it contains snake movement.*/

public class Snake {

	// Declaration Section
	
	// size of each snake joint, pixel in the snake and apple
	final static int PIXELSIZE = 10;
	// maximum number of snake joints within the frame
	final static int MAX_JOINTS = (1280 / PIXELSIZE) * (990 / PIXELSIZE);
	// declaring two arrays, one for x coordinates of snake, and one for y coordinates of snake
	private final int [] snakeX = new int [MAX_JOINTS];
	private final int [] snakeY = new int [MAX_JOINTS];
	
	/**
	 * Constructor 
	 * Purpose: give default coordinates to the snake
	 * Pre: none
	 * Post: instantiates the x and y coordinates of the snake
	 */
	public Snake (int x, int y) {
		// sets default coordinates of snake value
		snakeX [0] = x;
		snakeY [0] = y;
	}
	
	/**
	 * Purpose: Returns the x value of the snake
	 * Pre: X has an integer value
	 * Post: Returns the specific x coordinate of snake
	 */
	public int moveX (int x) {
		return snakeX [x];
	}
	
	/**
	 * Purpose: Returns the y value of the snake
	 * Pre: Y has an integer value
	 * Post: Returns the specific y coordinate of snake
	 */
	public int moveY (int y) {
		// returns the specific y coordinate of the snake
		return snakeY [y];
	}
	
	/**
	 * Purpose: Change the snake coordinates when it is moving
	 * Pre: The snake has a value for its coordinates
	 * Post: The coordinates of the snake changes once it moves
	 */
	public void moveSnake (int move) {
		snakeX[move] = snakeX[move-1];
		snakeY[move] = snakeY[move-1];
	}
	
	/**
	 * Purpose: Change the direction of the snake by going left
	 * Pre: movingLeft is true
	 * Post: The snake head moves towards the left side of the screen as it pixel size is subtracted from x coordinate
	 * 
	 */
	public void goingLeft (boolean movingLeft) {
		if (movingLeft) {
			// snakes moves towards the left side
			snakeX[0] -= PIXELSIZE;
		}
	}
	
	/**
	 * Purpose: Change the direction of the snake by going right
	 * Pre: movingRight is true
	 * Post: The snake head moves towards the right side of the screen as it pixel size is add to x coordinate
	 * 
	 */
	public void goingRight (boolean movingRight) {
		if (movingRight) {
			// snake moves towards the right side
			snakeX[0] += PIXELSIZE;
		}
	}
	
	/**
	 * Purpose: Change the direction of the snake by going up
	 * Pre: movingUp is true
	 * Post: The snake head moves up the screen as it pixel size is subtracted from y coordinate
	 * 
	 */
	public void goingUp (boolean movingUp) {
		if (movingUp) {
			snakeY[0] -= PIXELSIZE;
		}
	}
	
	/**
	 * Purpose: Change the direction of the snake by going down
	 * Pre: movingDown is true
	 * Post: The snake head moves down as pixel size is added to y coordinate
	 * 
	 */
	public void goingDown (boolean movingDown) {
		if (movingDown) {
			snakeY[0] += PIXELSIZE;
		}
	}
	
	
}
