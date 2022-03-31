/*File Name: Menu.java
Programmers: Anson, Bobby
Class: ICS 3U7 Mr Anthony
Date: Thursday June 14th, 2019
Purpose: This is a snake game for the final project. This class is the menu screen for the game.
         It has two play buttons for two different modes (single player and two player). It also
         contains buttons for controls and instructions that are specific for the game mode that
         they're under. There is also a quit button to exit out of the program.*/

import java.awt.*;
import java.awt.event.*;
import javax.swing.*;

public class Menu extends JFrame {

	
	private static final long serialVersionUID = 1L;
	
	// Declaration section
	private JLabel label;
	private JLabel player1;
	private JButton play1;
	private JButton instructions1;
	private JButton controls1;
	private JButton quit;
	private boolean instruct1 = false;
	private boolean control1 = false;

	private static Menu frame;

	// Constructor
	public Menu() {
		super("Menu"); // Name of window
		Container c = getContentPane();
		c.setLayout(null);

		play1 = new JButton("Play!"); // Play button
		instructions1 = new JButton("Instructions"); // Instructions button
		controls1 = new JButton("Controls"); // Controls button
		quit = new JButton("Quit"); // Quit button
		label = new JLabel(); // Label for title
		label.setText("Welcome to Snake Game!"); //Title of game
		player1 = new JLabel(); // Label for single player text
		player1.setText("Solo (1p):"); //Text for single player mode
		

		// Checks to see if the play button is pressed
		play1.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				// Goes to the 1 player game board when play is pressed.
				SolosBoard.runSnakeGame();
				frame.setVisible(false);
				// Does not show the text once the game is returned to the main menu
				instruct1 = false;
				control1 = false;
			}

		});

		// Checks to see if the instructions button is pressed
		instructions1.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {

				// Makes instruct true so it can display the instructions.
				instruct1 = true;

				// Makes the rest false so other text won't appear.
				control1 = false;
				repaint(); // Refreshes the screen to make instructions appear.
			}

		});

		// Checks to see if the controls button is pressed
		controls1.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {

				// Makes control true so that it can display the controls
				control1 = true;

				// Makes the rest false so other text won't appear.
				instruct1 = false;
				repaint();// Refreshes the screen to make controls appear.

			}

		});

		// Checks if the quit button is pressed
		quit.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				// exits the entire program
				System.exit(0);
			}

		});
	

		// Adding the 8 buttons, title of game, subtitles, and a line to separate the 2 modes.
		c.add(play1);
		c.add(instructions1);
		c.add(controls1);
		c.add(quit);
		c.add(label);
		c.add(player1);
	}
	
	/**
	 * Purpose: Display the buttons with its specific coordinates on the screen
	 * Pre: The buttons are already created, must be added by c.add(button_name)
	 * Post: The buttons are displayed on the screen, text is also displayed on the screen once certain buttons are clicked
	 */
	public void paint(Graphics g) {
		super.paint(g);

		// Setting properties for the play button
		play1.setSize(200, 50); // Size of button
		play1.setLocation(200, 600); // Location of button
		play1.setBackground(Color.green); // Colour of button
		play1.setForeground(Color.black); // Text colour
		play1.setFont(new Font("Serif", Font.BOLD, 20)); // Font of text, Bold, Size

		// Setting properties for the instructions button
		instructions1.setSize(200, 50); // Size of button
		instructions1.setLocation(450, 600); // Location of button
		instructions1.setBackground(Color.blue); // Colour of button
		instructions1.setForeground(Color.white); // Text colour
		instructions1.setFont(new Font("Serif", Font.BOLD, 20)); // Font of text, Bold, Size
		
		// Setting properties for the controls button
		controls1.setSize(200, 50); // Size of button
		controls1.setLocation(700, 600); // Location of button
		controls1.setBackground(Color.cyan); // Colour of button
		controls1.setForeground(Color.black); // Text colour
		controls1.setFont(new Font("Serif", Font.BOLD, 20)); // Font of text, Bold, Size

		// Setting properties for the quit button
		quit.setSize(200, 50); // Size of button
		quit.setLocation(950, 650); // Location of button
		quit.setBackground(Color.red); // Colour of button
		quit.setForeground(Color.white); // Text colour
		quit.setFont(new Font("Serif", Font.BOLD, 20)); // Font of text, Bold, Size

		// Setting properties for welcoming text.
		label.setLocation(400, 1); // Location of the text
		label.setSize(550, 30); // Size of text
		label.setFont(new Font("Serif", Font.BOLD, 40)); // Font of text, Bold, Size
		label.setForeground(Color.RED);

		// Setting properties for the subtitle player 1
		player1.setLocation(10, 610); //Location of text
		player1.setSize(200, 30); //Size of text
		player1.setFont(new Font("Serif", Font.BOLD, 30)); // Font of text, Bold, Size
		player1.setForeground(Color.red); // Text colour

		// If the single player instruct is true (When the user clicks the instructions button)
		if (instruct1) {
			g.setColor(Color.yellow); // Text colour changes to yellow
			g.setFont(new Font("Serif", Font.BOLD, 20)); // Font of text, Bold, Size

			// Instructions
			g.drawString("Your objective is to control your snake to eat the apples that randomly appear on the game board.", 225, 100);
			g.drawString("Apples help the snake grow longer and they increase your score.", 370, 200);
			g.drawString("The game will end when your snake runs into itself or if it runs into a wall.", 330, 300);
			g.drawString("Once your snake eats 10, 20, or 30 apples, the speed will increase. The head will be red and the body will be green.", 150, 400);
		}

		// If the single player control is true (When the user clicks the controls button)
		if (control1) {
			g.setColor(Color.yellow); // Colour of text
			g.setFont(new Font("Serif", Font.BOLD, 30)); // Font of text, Bold, Size

			// Controls
			g.drawString("Use WASD to Control your Snake", 420, 100);
			g.setColor(Color.gray); // Colour of text

			// Controls
			g.drawString("W", 627, 200);
			g.drawString("S", 627, 400);
			g.drawString("A", 475, 300);
			g.drawString("D", 800, 300);
		}

	}

	/**
	 * Purpose: Allows the user to see the main menu once the main menu button has been clicked
	 * Pre: Main menu button is clicked, the snake game is over
	 * Post: Sets the visibility of the frame to true, user can interact and see the main menu class again
	 */
	public static void returnToMenu () {
		frame.setVisible(true);
	}
	
	/**
	 * Purpose: Sets the components to the JFrame
	 * Pre: none
	 * Post: The JFrame is created with specific background and dimensions
	 * @param args
	 */
	// main method that calls the panel
	public static void main(String[] args) {
		frame = new Menu();
		frame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
		frame.setSize(1280, 990);
		frame.getContentPane().setBackground(Color.black);
		frame.show();
	}

}
