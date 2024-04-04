package clueGame;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.GridLayout;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.border.EtchedBorder;
import javax.swing.border.TitledBorder;

/*
 * GameCardsPanel class. Holds information and methods
 * about the game cards panel on the right of the GUI.
 * 
 * Authors:
 * Carson Diamond
 * Charlie Dupras
 */

public class GameCardsPanel extends JPanel {
	
	// constructor
	public GameCardsPanel() {
		
	}
	
	public static void main(String[] args) {
		GameCardsPanel panel = new GameCardsPanel(); // create the panel
		JFrame frame = new JFrame();
		
		frame.setContentPane(panel); // put the panel in the frame
		frame.setSize(180, 750); // size the frame
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE); // allow it to close
		frame.setVisible(true); // make it visible
	}
}
