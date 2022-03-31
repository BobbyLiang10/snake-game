/*File Name: SolosBoard.java
Programmers: Anson, Bobby
Class: ICS 3U7 Mr Anthony
Date: Thursday June 14th, 2019
Purpose: This is the class for the single player mode. There is a green snake that
         has a red head which makes it easy for the user to see which direction they're
         heading. The snake eats apples which makes it grow longer in size. The snake
         moves faster after it eats 10, 20, 30, or 40 apples. The game ends when the
         snake hits its own body or a wall. A game over screen will appear with two
         buttons. The first button takes the user back to the main menu and the second
         button exits out of the entire program.*/


import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JPanel;
import javax.swing.Timer;
import javax.swing.JFrame;
import java.io.*;

public class SolosBoard extends JPanel implements KeyListener, ActionListener {
	
	private static final long serialVersionUID = 1L;

	// Calls the snake class to receive its inputs
	Snake snake;
	
	// Declaration section
	// Sets the dimensions of the window
	private static final int WINDOW_WIDTH = 1280;
	private static final int WINDOW_HEIGHT = 990;
	
	// Sets the snake size.
	private static final int SNAKE_WIDTH = 50;
	
	// Sets the hit box of the apple.
	private static final int CLOSENESS = 15;
	
	// Size of each snake joint, pixel and apple.
	final static int PIXEL_SIZE = 10;
	
	// Maximum joints, pixels, and apples within the window frame.
	final static int MAX_JOINTS = (WINDOW_HEIGHT / PIXEL_SIZE) * (WINDOW_WIDTH / PIXEL_SIZE);
	
	// Counter for each apple eaten.
	private int score;
	
	// Coordinates for the apple.
	private int appleX;
	private int appleY;
	
	// Number of joints the snake has.
	private int joints;
	
	// counts the number of seconds the level up sound can play
	private boolean stopSound;
	
	// Boolean for directions.
	private boolean movingLeft;
	private boolean movingRight;
	private boolean movingUp;
	private boolean movingDown;
	
	// Boolean for the game to run.
	private boolean inGame = true;
	
	// Boolean to check if apple is on the screen.
	private boolean checkApple;
	
	// Variable for the apple image.
	private Image apple;
	
	// Buttons to return to main menu to replay the snake game or to quit on the spot.
	private JButton mainMenu;
	private JButton quit;
	
	// Timer for different difficulties
	Timer firstDifficulty;
	Timer secondDifficulty;
	Timer thirdDifficulty;
	Timer endlessDifficulty;
	
	// Constructor
	public SolosBoard () {
		// sets the speed for each timer
		// larger the number, the slower the snake is
		firstDifficulty = new Timer (60, this);
		secondDifficulty = new Timer (40, this);
		thirdDifficulty = new Timer (25, this);
		endlessDifficulty = new Timer (15, this);
		// sets the location of the snake
		snake = new Snake (400, 400);
		
		// setting the default direction of the snake once game has started
		movingRight = true;
		movingLeft = false;
		movingUp = false;
		movingDown = false;
		
		// the game has started
		inGame = true;
		
		// apple has not been located
		checkApple = false;
		
		// level up sound is false
		stopSound = false;
		
		// snake starts with three joints
		joints = 3;
		
		// food eaten counter is at 0
		score = 0;
		
		// setting the board
		setBackground(Color.BLACK); // sets the background as black
		addKeyListener(this); // start listening for keyboard inputs within the current class
		setFocusable(true);
		setFocusTraversalKeysEnabled(false);
		
		// displays the return to main menu button
		this.setLayout(null);
		mainMenu = new JButton ("Main Menu");
		mainMenu.setSize(200, 50); // Size of button
		mainMenu.setLocation(200, 650); // Location of button
		mainMenu.setBackground(Color.orange); // Colour of button
		mainMenu.setForeground(Color.black); // Text colour
		mainMenu.setFont(new Font("Serif", Font.BOLD, 20)); // Font of text, Bold, Size
		mainMenu.setVisible(false); // does not appear until the game is over 
		
		// Setting properties for the quit button
		quit = new JButton ("Quit");
		quit.setSize(200, 50); // Size of button
		quit.setLocation(950, 650); // Location of button
		quit.setBackground(Color.red); // Colour of button
		quit.setForeground(Color.white); // Text colour
		quit.setFont(new Font("Serif", Font.BOLD, 20)); // Font of text, Bold, Size
		quit.setVisible(false); // does not appear until the game is over
		
		// adds the two buttons to the panel
		this.add(mainMenu);
		this.add(quit);
		
		// start the timer for first difficulty
		firstDifficulty.start();
	}
	
	/**
	 * Purpose: To display the board and its features
	 * Pre: none
	 * Post: The board includes can display the snake, apple, end game message, score, difficulty, and buttons
	 */
	public void paintComponent (Graphics g) {
		super.paintComponent(g);
		Font font = new Font("Times New Roman", Font.BOLD, 30);
		g.setFont(font);
		g.setColor(Color.white);
		
		// first difficulty
		if (score >= 0 && score <= 9) {
			g.drawString("Difficulty Level: 1", 10, 30);
		}
		
		// once the snake reaches the next difficulty, play the level up sound ONCE
		if (score == 10) {
			// checks if the sound has been played
			while (!stopSound) {
				levelUpSound();
				// stop sound changes to true to indicate the sound has been played once
				stopSound = true;
			}
		}
		
		// second difficulty
		if (score >= 10 && score <= 19) {
			firstDifficulty.stop();
			secondDifficulty.start();
			
			// Makes sound playable again for next difficulty.
			if (score == 11) {
				stopSound = false;
			}
			g.drawString("Difficulty Level: 2", 10, 30);
		}
		
		
		// once the snake reaches the next difficulty, play the level up sound ONCE
		if (score == 20) {
			// checks if the sound has been played
			while (!stopSound) {
				levelUpSound();
				// stop sound changes to true to indicate the sound has been played once
				stopSound = true;
			}
		}
					
		
		// third difficulty
		if (score >= 20 && score <= 29) {
			secondDifficulty.stop();
			thirdDifficulty.start();
			
			// Makes sound playable again for next difficulty.
			if (score == 21) {
				stopSound = false;
			}
			g.drawString("Difficulty Level: 3", 10, 30);
		}
		
		
		// once the snake reaches the next difficulty, play the level up sound ONCE
		if (score == 30) {
			// checks if the sound has been played
			while (!stopSound) {
				levelUpSound();
				// stop sound changes to true to indicate the sound has been played once
				stopSound = true;
			}
		}
		
		// endless difficulty
		if (score >= 30) {
			thirdDifficulty.stop();
			endlessDifficulty.start();
			g.drawString("Difficulty Level: Endless!", 10, 30);
		}
		
		// displays the number of apples eaten 
		g.drawString("Apples Eaten: " + score, 500, 30);
		
		// setting the coordinates of the apple
		if (!checkApple) {
			// if the apple is not located on the board, change the x and y coordinates of the apple to 
			// a new location on the board
			appleX = (int)(Math.random()*(WINDOW_WIDTH - 150)) + 50;
			appleY = (int)(Math.random()*(WINDOW_HEIGHT - 150)) + 50;
			// apple is now on the board so checkApple is now true
			checkApple = true;
		}
		
		// redraw the image of the apple
		locateApple(g);
		
		// checks if the snake has hit any of the borders
		borderCollision();
		
		// checks if the snake has collided with itself
		snakeCollision();
		
		// checks if the food has been eaten
		appleEaten();
		
		// draws the snake head
		g.setColor(Color.RED);
		g.fillOval(snake.moveX(0), snake.moveY(0), SNAKE_WIDTH, SNAKE_WIDTH);
		
		// draws the snake body
		for (int i = 1; i < joints; i += 2) {
			// sets the color of the snake to green
			g.setColor(Color.GREEN);
			// draws each joint of the snake
			g.fillOval(snake.moveX(i), snake.moveY(i), SNAKE_WIDTH, SNAKE_WIDTH);
		}
		
		// once the snake hits the borders, the timer stops
		if(!inGame) {
			// timers all terminate and stop
			firstDifficulty.stop();
			secondDifficulty.stop();
			thirdDifficulty.stop();
			endlessDifficulty.stop();
			deathSound();
			// redirects user to the end game screen
			gameOver(g);
		}
		
	}

	@Override 
	/**
	 * Purpose: Moves the joints of the snake
	 * Pre: joints must be initialized to a starting number
	 * Post: The joints of the snake are moved in a certain direction depending on where the snake head moves
	 */
	public void actionPerformed(ActionEvent e) {
		// TODO Auto-generated method stub
		repaint();
		// moves each part of the snake
		for (int i = joints; i > 0; i--) {
			snake.moveSnake(i);
		}
		// calls the snake class to determine where it is moving
		snake.goingLeft(movingLeft);
		snake.goingRight(movingRight);
		snake.goingUp(movingUp);
		snake.goingDown(movingDown);
	}
	
	/**
	 * Purpose: Checks if the snake hits any of the borders
	 * Pre: none
	 * Post: inGame will return false if the snake hits any borders, otherwise the game will continue
	 */
	public void borderCollision () {
		// checks if the snake hits any of the four borders
		if (snake.moveX(0) > WINDOW_WIDTH - SNAKE_WIDTH || snake.moveX(0) < 0 || snake.moveY(0) > WINDOW_HEIGHT - SNAKE_WIDTH || snake.moveY(0) < 0) {
			// snake hits the border, therefore game is over
			inGame = false;
		}
	}
	/**
	 * Purpose: Checks if the snake hits itself
	 * Pre: none?
	 * Post: inGame will return false if the snake collides with itself, otherwise the game will continue
	 */
	public void snakeCollision () {
		// snake can only collide with itself if it has more than 4 joints
		if (joints > 4) {
			// this checks the coordinates of the snake's past movements and checks if the snake head runs into itself
			for (int i = joints - 1; i > 0; i--) {
				// checks if the snake coordinates overlap each other
				if (snake.moveX(0) == snake.moveX(i) && snake.moveY(0) == snake.moveY(i)) {
					// snake has collided with itself, therefore game is over
					inGame = false;
				}
			}
		}
	}
	
	/**
	 * Purpose: Checks if the snake has ate an apple
	 * Pre: none
	 * Post: If the apple has been eaten by the snake, the snake joints will increase by one
	 * and the apple will spawn in a different location on the board
	 */
	
	public void appleEaten() {
		// checks if the food has been eaten by the snake
		// closeness is used to see if the snake is close enough to the food, then it will eat it
		if ((snake.moveX(0) >= appleX - CLOSENESS) && (snake.moveX(0) <= appleX + CLOSENESS) && (snake.moveY(0) >= appleY - CLOSENESS) && (snake.moveY(0) <= appleY + CLOSENESS)) {
			appleSound();
			// the snake length increments by one, one joint is added to the body of the snake
			joints += 2;
			// score increments as apple is eaten
			score++;
			// there is no food on the screen
			checkApple = false;
		}
	}
	
	
	/**
	 * Purpose: Draws the apple
	 * Pre: The apple must have a location before it has been drawn
	 * Post: Prints out an image of the apple
	 */
	public void locateApple (Graphics g) {
		// calls the apple image from the src folder
		ImageIcon iia = new ImageIcon("apple.png");
        apple = iia.getImage();
        // draws the image based on the random position on the board
        g.drawImage(apple, appleX, appleY, this);
	}
	
	@Override
	/**
	 * Purpose: To check which keys are pressed to move the snake
	 * Pre: none
	 * Post: The snake movements are initialized to the direction that they are heading towards
	 */
	public void keyTyped(KeyEvent e) {

		// if the user enters 'd', then the snake will move to the right.
		if ((e.getKeyChar() == 'd' || e.getKeyChar() == 'D') && !movingLeft) {
			movingLeft = false;
			movingRight = true;
			movingUp = false;
			movingDown = false;
		}

		// if the user enters 'a', then the snake will move to the left.
		else if ((e.getKeyChar() == 'a' || e.getKeyChar() == 'A') && !movingRight) {
			movingLeft = true;
			movingRight = false;
			movingUp = false;
			movingDown = false;
		}

		// if the user enters 'w', the snake will move up.
		else if ((e.getKeyChar() == 'w' || e.getKeyChar() == 'W') && !movingDown) {
			movingLeft = false;
			movingRight = false;
			movingUp = true;
			movingDown = false;
		}

		// if the user enters 's', the snake will move down.
		else if ((e.getKeyChar() == 's' || e.getKeyChar() == 'S') && !movingUp) {
			movingLeft = false;
			movingRight = false;
			movingUp = false;
			movingDown = true;
		}
	}

	@Override
	// empty method as keyTyped performs the same operations and there is no ASCII code used
	public void keyPressed(KeyEvent e) {
		// TODO Auto-generated method stub
		
	}

	@Override
	// empty method as the velocity of the snake will stay the same
	public void keyReleased(KeyEvent e) {
		// TODO Auto-generated method stub
		
	}
	
	/**
	 * Purpose: Plays the sound of an apple being eaten
	 * Pre: The Apple must be consumed by the snake in order to play the sound
	 * Post: After the snake eats the apple, this sound will be played
	 */
	public void appleSound() {
		// sets the path directory of the sound file
		String filePath = "Eat.wav";
		try {
	        // creates AudioInputStream object and initializes it to the sound file
			AudioInputStream audioInputStream = AudioSystem.getAudioInputStream(new File(filePath).getAbsoluteFile());
	        // creates the clip reference
			Clip clip = AudioSystem.getClip();
	        // opens the sound
	        clip.open(audioInputStream);
	        // plays the sound
	        clip.start();
	    } catch(Exception e) {
	        // if the sound cannot be played, display this message
	    	System.out.println("Error with playing sound.");
	        e.printStackTrace();
	    }
	}
	
	
	/**
	 * Purpose: Plays the sound of level up
	 * Pre: The player score must eat 10, 20, or 30 apples in order to allow this to play
	 * Post: After reaching this score, the music will play for three seconds and stop
	 */
	public void levelUpSound () {
		String filePath = "LevelUp.wav";
		try {
	        // creates AudioInputStream object and initializes it to the sound file
			AudioInputStream audioInputStream = AudioSystem.getAudioInputStream(new File(filePath).getAbsoluteFile());
	        // creates the clip reference
			Clip clip = AudioSystem.getClip();
	        // opens the sound
	        clip.open(audioInputStream);
	        // plays the sound
	        clip.start();
	    } catch(Exception e) {
	        // if the sound cannot be played, display this message
	    	System.out.println("Error with playing sound.");
	        e.printStackTrace();
	    }
	}
	
	/**
	 * Purpose: Plays the sound of a trombone
	 * Pre: The game must end and the snake either runs into itself or the border
	 * Post: Once the game ends, this plays the sad trombone sound to indicate the user lost
	 */
	public void deathSound () {
		String filePath = "Death.wav";
		try {
	        // creates AudioInputStream object and initializes it to the sound file
			AudioInputStream audioInputStream = AudioSystem.getAudioInputStream(new File(filePath).getAbsoluteFile());
	        // creates the clip reference
			Clip clip = AudioSystem.getClip();
	        // opens the sound
	        clip.open(audioInputStream);
	        // plays the sound
	        clip.start();
	    } catch(Exception e) {
	        // if the sound cannot be played, display this message
	    	System.out.println("Error with playing sound.");
	        e.printStackTrace();
	    }
	}
	
	/**
	 * Purpose: To display the score and once the game has ended
	 * Pre: inGame = false
	 * Post: User is given a score and will be redirected back to the main menu (NOT IMPLEMENTED YET)
	 */
	public void gameOver (Graphics g) {
		// when the snake hits the borders or the snake hits itself, this method is called
		Font font = new Font ("Times New Roman", Font.BOLD, 50);
		g.setFont(font);
		g.setColor(Color.YELLOW);
		// displays the end game messages
		g.drawString("Game Over.", 500, 500);
		g.drawString("Your score: " + score + ".", 500, 700);
		
		// main menu button appears once the game has ended
		mainMenu.setVisible(true);
		mainMenu.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				// sets the board's visibility to false, disappears off the screen
				SolosBoard.this.setVisible(false);
				// returns to main menu
				Menu.returnToMenu();
			}

		});
		
		// user can see the quit button once the game has ended
		quit.setVisible(true);
		// checks if the quit button is pressed
		quit.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				// exits the entire program
				System.exit(0);
			}

		});
		
	}
	
	/**
	 * Purpose: Sets a new board for the snake game to run
	 * Pre: none
	 * Post: JFrame is created to run the snake game
	 */
	public static void runSnakeGame () {
		// creates a new board
		SolosBoard board = new SolosBoard ();
		// new frame
		JFrame a = new JFrame ();
		a.add(board);
		a.setVisible(true);
		a.setSize(1280, 990);
		a.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		a.setTitle("Snake Game");
	}
}