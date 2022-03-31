/*File Name: DuosBoard.java
Programmers: Anson, Bobby
Class: ICS 3U7 Mr Anthony
Date: Thursday June 14th, 2019
Purpose: This is the class for the two player mode. There are two snakes, one is
         blue and the other is red. The two snakes eat apples which makes them grow longer
         in size. The two snakes will move faster if one of the two snakes eat 10, 20, or 30
         apples. The game ends when one of the two snakes hits a wall, its body, or their opponents
         body. A game over screen will appear with two buttons. The first button takes the user back 
         to the main menu and the second button exits out of the entire program.*/


import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.io.File;

import javax.swing.JPanel;
import javax.swing.Timer;
import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;


public class DuosBoard extends JPanel implements KeyListener, ActionListener {
	
	private static final long serialVersionUID = 1L;

	// calls the snake class to receive its inputs
	Snake snake1;
	Snake snake2;
	
	// Declaration section

	// sets the dimensions of the window
	private static final int WINDOW_WIDTH = 1280;
	private static final int WINDOW_HEIGHT = 990;
	// sets the size of t
	private static final int SNAKE_WIDTH = 50; 
	// sets the closeness to determine whether the snake hits itself or one another check how close the snake is to the apple so it can eat it
	private static final int SNAKE_CLOSENESS = 8;
	// sets the closeness of the apple to check whether the snake is close enough to eat it
	private static final int APPLE_CLOSENESS = 15;
	
	// size of each snake joint, pixel and apple
	final static int PIXEL_SIZE = 10;
	// maximum joints, pixels, and apples within the window frame
	final static int MAX_JOINTS = (WINDOW_HEIGHT / PIXEL_SIZE) * (WINDOW_WIDTH / PIXEL_SIZE);
	
	// counter for each apple eaten
	// NOTE: The scores are used differently than the solo board class as the scores are only used to determine whether the snake moves faster
	// once it eats more a specific amount of apples. Winner is determined by the longest survival time, not the most apples eaten.
	
	// score to check the number of apples eaten by each snake
	private int s1Score;
	private int s2Score;
	
	// score to check which snake has the higher score, used to determine difficulty
	private int higherScore;
	
	// coordinates for the apple
	private int appleX;
	private int appleY;
	
	// number of joints each snake has
	private int s1Joints;
	private int s2Joints;
	

	// buttons to return to main menu to replay the snake game or to quit on the spot
	private JButton mainMenu;
	private JButton quit;
	
	// boolean for directions
	private boolean s1MovingLeft;
	private boolean s1MovingRight;
	private boolean s1MovingUp;
	private boolean s1MovingDown;
	
	private boolean s2MovingLeft;
	private boolean s2MovingRight;
	private boolean s2MovingUp;
	private boolean s2MovingDown;
	
	// boolean for the game to run
	private boolean inGame;
	
	// boolean to check if the speed level up sound plays once
	private boolean stopSound;
	
	// boolean to check if apple is on the screen
	private boolean checkApple;
	
	// boolean to check if it's a tie
	private boolean checkTie;
	
	// boolean variables to check which player won the game
	private boolean SnakeOneWins = false;
	private boolean SnakeTwoWins = false;
	
	// variable for the apple image
	private Image apple;
	
	// Timer for different difficulties
		Timer FirstDifficulty;
		Timer SecondDifficulty;
		Timer ThirdDifficulty;
	
	// Constructor
	public DuosBoard () {
		// sets the speed for each timer
		// larger the number, slower the snake is
		FirstDifficulty = new Timer (45, this);
	    SecondDifficulty = new Timer (25, this);
		ThirdDifficulty = new Timer (15, this);
		
		// sets the location of the snake
		snake1 = new Snake (400, 400);
		snake2 = new Snake (800, 400);
		
		// setting the default direction of the snake once game has started
		s1MovingRight = true;
		s1MovingLeft = false;
		s1MovingUp = false;
		s1MovingDown = false;
		
		s2MovingRight = false;
		s2MovingLeft = true;
		s2MovingUp = false;
		s2MovingDown = false;
		
		// the game has started
		inGame = true;
		
		// apple has not been located
		checkApple = false;
		
		// each snake starts with three joints
		s1Joints = 3;
		s2Joints = 3;
		
		// food eaten counter is at 0
		s1Score = 0;
		s2Score = 0;
		higherScore = 0;
		
		// setting the board
		setBackground(Color.BLACK);
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
						
		quit = new JButton ("Quit");
				
		// Setting properties for the quit button
		quit.setSize(200, 50); // Size of button
		quit.setLocation(950, 650); // Location of button
		quit.setBackground(Color.red); // Colour of button
		quit.setForeground(Color.white); // Text colour
		quit.setFont(new Font("Serif", Font.BOLD, 20)); // Font of text, Bold, Size
		quit.setVisible(false); // does not appear until the game is over
		
		// adds the two buttons to the panel
		this.add(mainMenu);
		this.add(quit);
		
		// the first difficulty of the game starts
		FirstDifficulty.start();
	}
	
	/**
	 * Purpose: To display the board and its features
	 * Pre: none
	 * Post: The board includes can display the snakes, apple, end game message, score, difficulty, and buttons
	 */
	public void paintComponent (Graphics g) {
		super.paintComponent(g);
		Font font = new Font("Times New Roman", Font.BOLD, 30);
		g.setFont(font);
		g.setColor(Color.WHITE);
		
		// checks which player has the higher score, this will be used to determine the speed level
		if (s1Score > s2Score) {
			// player one has the higher score
			higherScore = s1Score;
		} else {
			// player two has the higher score
			higherScore = s2Score;
		}
		
		// first difficulty
		if (higherScore >= 0 && higherScore <= 9) {
			g.drawString("Speed Level: 1", 500, 30);
		}
		
		// once the snake reaches the next difficulty, play the level up sound ONCE
		if (higherScore == 10) {
			// checks if the sound has been played
			while (!stopSound) {
				speedLevelUpSound();
				// stop sound changes to true to indicate the sound has been played once
				stopSound = true;
			}
		}
		
		// second difficulty
		if (higherScore >= 10 && higherScore <= 19) {
			FirstDifficulty.stop();
			SecondDifficulty.start();
			
			// Makes sound playable again for next difficulty.
			if (higherScore == 11) {
				stopSound = false;
			}
			g.drawString("Speed Level: 2", 500, 30);
		}
		
		// once the snake reaches the next difficulty, play the level up sound ONCE
		if (higherScore == 20) {
			// checks if the sound has been played
			while (!stopSound) {
				speedLevelUpSound();
				// stop sound changes to true to indicate the sound has been played once
				stopSound = true;
			}
		}
		
		// third and last difficulty
		if (higherScore >= 20) {
			SecondDifficulty.stop();
			ThirdDifficulty.start();
			g.drawString("Speed Level: 3", 500, 30);
		}
		
		// displays the number of apples eaten 
		g.setColor(Color.RED);
		g.drawString("Player 1 Apples Eaten: " + s1Score, 100, 30);
		g.setColor(Color.BLUE);
		g.drawString("Player 2 Apples Eaten: " + s2Score, 800, 30);
		
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
		g.setColor(Color.GREEN);
		g.fillOval(snake1.moveX(0), snake1.moveY(0), SNAKE_WIDTH, SNAKE_WIDTH);
		g.fillOval(snake2.moveX(0), snake2.moveY(0), SNAKE_WIDTH, SNAKE_WIDTH);
		
		// draws the first snake
		for (int i = 1; i < s1Joints; i += 2) {
			// sets the color of the snake to red
			g.setColor(Color.red);
			// draws each joint of the snake
			g.fillOval(snake1.moveX(i), snake1.moveY(i), SNAKE_WIDTH, SNAKE_WIDTH);
		}
		
		// draws the second snake
		for (int i = 1; i < s2Joints; i += 2){
			// sets the color of the snake to blue
			g.setColor(Color.blue);
			// draws each joint of the snake
			g.fillOval(snake2.moveX(i), snake2.moveY(i), SNAKE_WIDTH, SNAKE_WIDTH);
		}
		
		// once the snake hits the borders, the timer stops
		if(!inGame) {
			// timers all terminate and stop
			FirstDifficulty.stop();
			SecondDifficulty.stop();
			ThirdDifficulty.stop();
			// checks if the game ends with a tie, then play the tie sound
			if (checkTie) {
				tieSound();
			} else {
				// otherwise, play the winning sound to indicate their is a clear winner
				winningSound();
			}
			// redirects user to the end game screen
			gameOver(g);
		}
		
	}

	@Override 
	/**
	 * Purpose: Moves the joints of each snake
	 * Pre: joints must be initialized to a starting number
	 * Post: The joints of the snake are moved in a certain direction depending on where the snake head moves
	 */
	public void actionPerformed(ActionEvent e) {
		// TODO Auto-generated method stub
		repaint();
		
		// moves the first player snake
		for (int i = s1Joints; i > 0; i--) {
			snake1.moveSnake(i);
		}
		// checks which direction the first snake is moving 
		snake1.goingLeft(s1MovingLeft);
		snake1.goingRight(s1MovingRight);
		snake1.goingUp(s1MovingUp);
		snake1.goingDown(s1MovingDown);
		
		// moves the second player snake
		for (int i = s2Joints; i > 0; i--){
			snake2.moveSnake(i);
		}
		
		// checks which direction the second snake is moving
		snake2.goingLeft(s2MovingLeft);
		snake2.goingRight(s2MovingRight);
		snake2.goingUp(s2MovingUp);
		snake2.goingDown(s2MovingDown);
	}
	
	/**
	 * Purpose: Checks if the snake hits any of the borders
	 * Pre: none
	 * Post: inGame will return false if the snake hits any borders, otherwise the game will continue
	 */
	public void borderCollision () {
		// checks if the first player snake hits any of the four borders
		if (snake1.moveX(0) > WINDOW_WIDTH - SNAKE_WIDTH || snake1.moveX(0) < 0 || snake1.moveY(0) > WINDOW_HEIGHT - SNAKE_WIDTH || snake1.moveY(0) < 0) {
			// snake hits the border, therefore game is over
			inGame = false;
			// player two wins because player one hits the borders and dies
			SnakeTwoWins = true;
		}
		
		// checks if the second player snake hits any of the four borders
		if(snake2.moveX(0) > WINDOW_WIDTH - SNAKE_WIDTH || snake2.moveX(0) < 0 || snake2.moveY(0) > WINDOW_HEIGHT|| snake2.moveY(0) < 0){
			// snake hits the border, game is over
			inGame = false;
			// player one wins because player two hits the borders and dies
			SnakeOneWins = true;
		}
	}
	/**
	 * Purpose: Checks if the snake hits itself
	 * Pre: none?
	 * Post: inGame will return false if the snake collides with itself, otherwise the game will continue
	 */
	public void snakeCollision () {
		// checks if player one snake hits itself
		// snake can only collide with itself if it has more than 4 joints
		if (s1Joints > 4) {
			for (int i = s1Joints - 1; i > 0; i--) {
				// checks if the snake coordinates overlap each other
				if (snake1.moveX(0) == snake1.moveX(i) && snake1.moveY(0) == snake1.moveY(i)) {
					// snake has collided with itself, therefore game is over
					inGame = false;
					// player two wins because player one collided with itself
					SnakeTwoWins = true;
				}
			}
		}
		
		// checks if player two snake hits itself
		// snake can only collide with itself if it has more than 4 joints
		if (s2Joints > 4){
			for (int i = s2Joints - 1; i > 0; i--){
				// checks if the snake coordinates overlap each other
				if (snake2.moveX(0) == snake2.moveX(i) && snake2.moveY(0) == snake2.moveY(i)) {
					// snake has collided with itself, game is over
					inGame = false;
					// player one wins because the second player ran into itself
					SnakeOneWins = true;
				}
			}
		}
		
		// checks if snake heads collide with each other, results in a tie
		if (snake1.moveX(0) == snake2.moveX(0) && snake1.moveY(0) == snake2.moveY(0)) {
			// the two snakes run into each other, game is over
			inGame = false;
			// since both heads hit each other, the game results in a tie, no winner is determined
			SnakeOneWins = false;
			SnakeTwoWins = false;
			checkTie = true;
		}
		
		
		// checks if one snake hits the joints of the other snake
		for (int i = s1Joints - 1; i > 0; i--) {
			for (int j = s2Joints - 1; j > 0; j--) {
				// if snake is close enough to the other snake, snake1 will run into the other snake, causing snake2 to win
				if ((snake1.moveX(0) >= snake2.moveX(j) - SNAKE_CLOSENESS) && (snake1.moveX(0) <= snake2.moveX(j) + SNAKE_CLOSENESS)
					&& (snake1.moveY(0) >= snake2.moveY(j) - SNAKE_CLOSENESS) && (snake1.moveY(0) <= snake2.moveY(j) + SNAKE_CLOSENESS)){
					// winner has been determined, game is over
					inGame = false;
					SnakeTwoWins = true;
				// if snake is close enough to the other snake, snake2 will run into the other snake, causing snake1 to win
				} else if ((snake2.moveX(0) >= snake1.moveX(i) - SNAKE_CLOSENESS) && (snake2.moveX(0) <= snake1.moveX(i) + SNAKE_CLOSENESS)
						&& (snake2.moveY(0) >= snake1.moveY(i) - SNAKE_CLOSENESS) && (snake2.moveY(0) <= snake1.moveY(i) + SNAKE_CLOSENESS)) {
					// winner has been determined, game is over
					inGame = false;
					SnakeOneWins = true;
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
		if ((snake1.moveX(0) >= appleX - APPLE_CLOSENESS) && (snake1.moveX(0) <= appleX + APPLE_CLOSENESS) && (snake1.moveY(0) >= appleY - APPLE_CLOSENESS) && (snake1.moveY(0) <= appleY + APPLE_CLOSENESS)) {
			appleSound();
			// the snake length increments by one, one joint is added to the body of the snake
			s1Joints += 2;
			// score increments as apple is eaten
			s1Score++;
			// there is no food on the screen
			checkApple = false;
		}
		
		if ((snake2.moveX(0) >= appleX - APPLE_CLOSENESS) && (snake2.moveX(0) <= appleX + APPLE_CLOSENESS) && (snake2.moveY(0) >= appleY - APPLE_CLOSENESS) && (snake2.moveY(0) <= appleY + APPLE_CLOSENESS)) {
			appleSound();
			// the snake length increments by one, one joint is added to the body of the snake
			s2Joints += 2;
			// score increments as apple is eaten
			s2Score++;
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
		// calls the image from the src folder
		ImageIcon iia = new ImageIcon("apple.png");
        apple = iia.getImage();
        // draws the apple based on its random coordinates 
        g.drawImage(apple, appleX, appleY, this);
	}
	
	@Override
	/**
	 * Purpose: To check which keys are pressed to move the snake
	 * Pre: none
	 * Post: The snake movements are initialized to the direction that they are heading towards
	 */
	public void keyTyped(KeyEvent e) {

		// if the player one enters 'd' then the snake will move to the right.
		if ((e.getKeyChar() == 'd' || e.getKeyChar() == 'D') && !s1MovingLeft) {
			s1MovingLeft = false;
			s1MovingRight = true;
			s1MovingUp = false;
			s1MovingDown = false;
		}

		// if the player one enters 'a' then the snake will move to the left.
		else if ((e.getKeyChar() == 'a' || e.getKeyChar() == 'A') && !s1MovingRight) {
			s1MovingLeft = true;
			s1MovingRight = false;
			s1MovingUp = false;
			s1MovingDown = false;
		}

		// if the player one enters 'w', the snake will move up.
		else if ((e.getKeyChar() == 'w' || e.getKeyChar() == 'W') && !s1MovingDown) {
			s1MovingLeft = false;
			s1MovingRight = false;
			s1MovingUp = true;
			s1MovingDown = false;
		}
		
		// if the player one enters 's', the snake will move down.
		else if ((e.getKeyChar() == 's' || e.getKeyChar() == 'S') && !s1MovingUp) {
			s1MovingLeft = false;
			s1MovingRight = false;
			s1MovingUp = false;
			s1MovingDown = true;
		}
		
	}

	@Override
	/**
	 * Purpose: To allow snake movements for the second player using arrow keys
	 * Pre: none
	 * Post: The snake movements are initialized to a direction where the snake is going
	 */
	public void keyPressed(KeyEvent e) {
		// TODO Auto-generated method stub

		// if the player two enters right arrow key, the snake will move right.
		if (e.getKeyCode() == KeyEvent.VK_RIGHT && !s2MovingLeft) {
			s2MovingLeft = false;
			s2MovingRight = true;
			s2MovingUp = false;
			s2MovingDown = false;
		}
		
		// if the player two enters left arrow key, the snake will move left.
		else if (e.getKeyCode() == KeyEvent.VK_LEFT && !s2MovingRight) {
			s2MovingLeft = true;
			s2MovingRight = false;
			s2MovingUp = false;
			s2MovingDown = false;
		}
		
		// if the player two enters the up arrow key, the snake will move up.
		else if (e.getKeyCode() == KeyEvent.VK_UP && !s2MovingDown) {
			s2MovingLeft = false;
			s2MovingRight = false;
			s2MovingUp = true;
			s2MovingDown = false;
		}
		
		// if the player two presses the down arrow key, the snake will move down.
		else if (e.getKeyCode() == KeyEvent.VK_DOWN && !s2MovingUp) {
			s2MovingLeft = false;
			s2MovingRight = false;
			s2MovingUp = false;
			s2MovingDown = true;
		}
		
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
	 * Pre: The player score must eat 10 or 20 apples in order to allow this to play
	 * Post: After reaching this score, the music will play for three seconds and stop
	 */
	public void speedLevelUpSound () {
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
	        // stops the sound
	    } catch(Exception e) {
	        // if the sound cannot be played, display this message
	    	System.out.println("Error with playing sound.");
	        e.printStackTrace();
	    }
	}
	
	/**
	 * Purpose: Plays the sound of a crowd cheering
	 * Pre: The game must have a winner and the game ends
	 * Post: Once the game ends, this plays the cheering sound to indicate a winner
	 */
	public void winningSound () {
		String filePath = "Cheering.wav";
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
	 * Pre: The game must end and the end result is a tie
	 * Post: Once the game ends, this plays the sad trombone sound
	 */
	public void tieSound () {
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
	 * Purpose: To display the winner once the game has ended
	 * Pre: inGame = false
	 * Post: Winner is announced and users will be redirected back to the main menu (NOT IMPLEMENTED YET)
	 */
	public void gameOver (Graphics g) {
		Font font = new Font ("Times New Roman", Font.BOLD, 50);
		g.setFont(font);
		g.setColor(Color.YELLOW);
		g.drawString("Game Over.", 500, 400);
		
		// displays the message for the winner or if the game ends in a tie
		// NOTE: Winner is determined by longest survival time. NOT the number of apples eaten, different from solo board
		if (SnakeOneWins) {
			g.drawString("Player 1 wins!", 500, 500);
		} else if (SnakeTwoWins) {
			g.drawString("Player 2 wins!", 500, 500);
		} else if (SnakeOneWins == SnakeTwoWins) {
			g.drawString("It's a tie!", 500, 500);
		}
		
		// return to main menu and quit buttons appear once the game has ended
				mainMenu.setVisible(true);
				mainMenu.addActionListener(new ActionListener() {
					public void actionPerformed(ActionEvent arg0) {
						// sets the duos board visibility to false
						DuosBoard.this.setVisible(false);
						// returns to the main menu
						Menu.returnToMenu();
					}

				});
				
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
		DuosBoard board = new DuosBoard ();
		// creates a new snake
		// new frame
		JFrame a = new JFrame ();
		a.add(board);
		a.setVisible(true);
		a.setSize(1280, 990);
		a.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		a.setTitle("Snake Game");
	}
}
