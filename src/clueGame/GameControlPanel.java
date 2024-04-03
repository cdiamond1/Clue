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

public class GameControlPanel extends JPanel {
	private JTextField name;
	
	/**
	 * Constructor for the panel, it does 90% of the work
	 */
	public GameControlPanel() {
		setLayout(new GridLayout(2, 1));
		JPanel panel = createPlayerPane1();
		add(panel);
		panel = createButtonPanel();
		add(panel);
		panel = createNamePanel();
		add(panel);
		panel = createNamePane2();
		add(panel);
		
		
	}

	private JPanel createNamePanel() {
		JPanel panel = new JPanel();
		// Use a grid layout, 1 row, 2 elements (label, text)
		panel.setLayout(new GridLayout(1, 2));
		panel.setBorder(new TitledBorder(new EtchedBorder(), "Guess"));
		JLabel jlabel = new JLabel("I have no guess!");
		panel.add(jlabel);
		return panel;
	}
	
	private JPanel createNamePane2() {
		JPanel panel = new JPanel();
		// Use a grid layout, 1 row, 2 elements (label, text)
		panel.setLayout(new GridLayout(1, 2));
		JLabel jlabel = new JLabel("So you have nothing?");
		panel.add(jlabel);
		panel.setBorder(new TitledBorder(new EtchedBorder(), "Guess Result"));
		return panel;
	}
	
	private JPanel createPlayerPane1() {
		JPanel panel = new JPanel();
		// Use a grid layout, 1 row, 2 elements (label, text)
		panel.setLayout(new FlowLayout(FlowLayout.CENTER));
		JLabel jlabel = new JLabel("Whose turn?");		
		panel.add(jlabel);
		
		jlabel = new JLabel("Mustard Test");
		jlabel.setBorder(BorderFactory.createLineBorder(Color.GRAY));
		jlabel.setOpaque(true);
		jlabel.setBackground(Color.YELLOW);
		panel.add(jlabel);
		
		jlabel = new JLabel("Roll:");
		panel.add(jlabel);
		
		jlabel = new JLabel("IDK 6 ig");
		jlabel.setBorder(BorderFactory.createLineBorder(Color.GRAY));
		panel.add(jlabel);
		return panel;
	}
	
	private JPanel createButtonPanel() {
		// no layout specified, so this is flow
		JButton accuse = new JButton("Make Accusation");
		JButton next = new JButton("NEXT!");
		JPanel panel = new JPanel();
		panel.setLayout(new GridLayout(1, 2));
		accuse.setPreferredSize(new Dimension(200, 200));
		next.setPreferredSize(new Dimension(200, 200));
		panel.add(accuse);
		panel.add(next);
		return panel;
	}

	private void setGuessResult(String string) {
		// TODO Auto-generated method stub

	}

	private void setGuess(String string) {
		// TODO Auto-generated method stub

	}

	private void setTurn(ComputerPlayer computerPlayer, int i) {
		// TODO Auto-generated method stub

	}

	/**
	 * Main to test the panel
	 * 
	 * @param args
	 */
	public static void main(String[] args) {
		GameControlPanel panel = new GameControlPanel(); // create the panel
		JFrame frame = new JFrame(); // create the frame
		frame.setContentPane(panel); // put the panel in the frame
		frame.setSize(750, 180); // size the frame
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE); // allow it to close
		frame.setVisible(true); // make it visible

		// test filling in the data
		panel.setTurn(new ComputerPlayer("Col. Mustard", 0, 0, "orange"), 5);
		panel.setGuess("I have no guess!");
		panel.setGuessResult("So you have nothing?");
	}
}
