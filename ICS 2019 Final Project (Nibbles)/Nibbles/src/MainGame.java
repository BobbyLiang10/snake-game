/* File Name: MainGame.java
 * Programmers: Anson Liang, Bobby Liang
 * Date: June 13th 2019
 * Class: ICS 3U7 Mr. Anthony
 * Purpose: To run a game of nibbles 
 */

import java.awt.*;

import javax.swing.*;

import java.awt.event.*;

public class MainGame extends JFrame implements ActionListener{

	JButton playButton;
	JButton instructionsButton;
	JButton quitButton;
	
	boolean playPressed;
	boolean instructionsPressed;
	
	
	private static final int WINDOWWIDTH = 1500;
	private static final int WINDOWHEIGHT = 1000;
	
	private String playText = "";
	private String instructionsText = "";
	
	private Image apple;
	
	public MainGame (String windowName) {
		super(windowName);
		playButton = new JButton ("Play!");
		playButton.addActionListener(this);
		playButton.setLocation(180,540);
		playButton.setSize(100,50);
		
		instructionsButton = new JButton ("Instructions");
		instructionsButton.addActionListener(this);
		instructionsButton.setLocation(490,540);
		instructionsButton.setSize(125,50);
		
		quitButton = new JButton ("Quit");
		quitButton.addActionListener(this);
		quitButton.setLocation(820,540);
		quitButton.setSize(100,50);
		
		Container c = getContentPane();
		c.setLayout(null);
		c.add(playButton);
		c.add(instructionsButton);
		c.add(quitButton);
	}
	
	public void actionPerformed (ActionEvent evt)  {
		if (evt.getSource() == playButton) {
			playPressed = true;
			instructionsPressed = false;
			playText = "PLAY!";
			
		} else if (evt.getSource() == instructionsButton) {
			instructionsText = "Hi";
			playPressed = false;
			instructionsPressed = true;
			loadImages();
		} else if (evt.getSource() == quitButton) {
			System.exit(0);
		}
		
		repaint();
	}
	

	public void paint (Graphics g) {
		super.paintComponents(g);
		if (instructionsPressed = true) {
			instructionsButtonPressed(g);
		} else if (playPressed = true) {
			playButtonPressed(g);
		}
		
	}
	
	private void instructionsButtonPressed(Graphics g) {
		repaint();
		g.drawString(instructionsText, 200, 300);
		g.drawImage(apple, 500, 500, this);
	}
	
	private void playButtonPressed(Graphics g) {
		repaint();
		g.drawString(playText, 100, 300);
	}
	
	private void loadImages() {

        ImageIcon iia = new ImageIcon("src/apple.png");
        apple = iia.getImage();

    }
	

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		MainGame a = new MainGame ("Nibbles");
		a.setSize(WINDOWWIDTH,WINDOWHEIGHT);
		a.setVisible(true);
		a.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
	}
	
}
	

