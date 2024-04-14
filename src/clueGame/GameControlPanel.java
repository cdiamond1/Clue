package clueGame;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.border.EtchedBorder;
import javax.swing.border.TitledBorder;

public class GameControlPanel extends JPanel {
	private JTextField name;
	private JTextField Guess = new JTextField();
	private JTextField Roll = new JTextField();
	private JTextField GuessResult = new JTextField();
	private JTextField PlayerTurn = new JTextField();	
	
	private static final int WINDOW_WIDTH = 750;
	private static final int WINDOW_HEIGHT = 200;

	/**
	 * Constructor for the panel, it does 90% of the work
	 */
	public GameControlPanel() {
		setLayout(new GridLayout(2, 0));
		JPanel pane2 = new JPanel();
		pane2.setLayout(new GridLayout(1, 4));
		add(pane2);
		JPanel panel = createPlayerPane1();
		add(panel);
		panel = createRollPanel();
		add(panel);
		JButton button1 = createButtonPanel();
		add(button1);
		button1 = createButtonPanel2();
		add(button1);
		pane2.setLayout(new GridLayout(0, 2));
		add(pane2);
		panel = createGuessPanel();
		add(panel);
		panel = createGuessResultPanel();
		add(panel);

	}

	private JPanel createPlayerPane1() {
		JPanel panel = new JPanel();
		// Use a grid layout, 1 row, 2 elements (label, text)
		JLabel jlabel = new JLabel("Whose turn?");
		panel.add(jlabel);

		PlayerTurn = new JTextField();
		PlayerTurn.setBorder(BorderFactory.createLineBorder(Color.GRAY));
		PlayerTurn.setOpaque(true);
		PlayerTurn.setForeground(Color.BLACK);
		PlayerTurn.setEditable(false);
		//PlayerTurn.setBackground(Color.YELLOW);
		panel.add(PlayerTurn);

		return panel;
	}

	private JPanel createRollPanel() {
		JPanel panel = new JPanel();
		JLabel jlabel = new JLabel("Roll:");
		panel.add(jlabel);

		Roll.setBorder(BorderFactory.createLineBorder(Color.GRAY));
		Roll.setOpaque(false);
		Roll.setForeground(Color.BLACK);
		Roll.setEnabled(false);
		panel.add(Roll);
		return panel;
	}

	private JButton createButtonPanel() {
		// no layout specified, so this is flow
		JButton accuse = new JButton("Make Accusation");
		accuse.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				if(e.getSource() == accuse) {
					System.out.println("B");
				}
			}
		});
		accuse.setSize(new Dimension(100, 100));
		return accuse;
	}

	private JButton createButtonPanel2() {
		// no layout specified, so this is flow
		JButton next = new JButton("NEXT!");
		next.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				if(e.getSource() == next) {
					System.out.println("A");
				}
			}
		});
		next.setSize(new Dimension(100, 100));
		
		next.doClick();
		
		return next;
	}
	
	private JPanel createGuessResultPanel() {
		JPanel panel = new JPanel();
		// Use a grid layout, 1 row, 2 elements (label, text)
		panel.setLayout(new GridLayout(1, 0));
		
		GuessResult.setOpaque(false);
		GuessResult.setForeground(Color.BLACK);
		GuessResult.setEnabled(false);
		panel.add(GuessResult);
		panel.setBorder(new TitledBorder(new EtchedBorder(), "Guess Result"));
		return panel;
	}

	private JPanel createGuessPanel() {
		JPanel panel = new JPanel();
		panel.setLayout(new GridLayout(1, 0));
		panel.setBorder(new TitledBorder(new EtchedBorder(), "Guess"));
		JTextField Guess = new JTextField("I have no guess!");
		Guess.setOpaque(false);
		Guess.setForeground(Color.BLACK);
		Guess.setEnabled(false);
		panel.add(Guess);
		return panel;
	}

	public void setGuessResult(String string) {
		// TODO Auto-generated method stub
		GuessResult.setText(string);
	}

	public void setGuess(String string) {
		// TODO Auto-generated method stub
		Guess.setText(string);
	}

	public void setTurn(Player computerPlayer, int i) {
		// TODO Auto-generated method stub
		PlayerTurn.setText(computerPlayer.getName());
		PlayerTurn.setBackground(computerPlayer.getColor());
		Roll.setText(""+i);
		repaint();
		revalidate();
		
	}

	/**
	 * Main to test the panel
	 * 
	 * @param args
	 */
	public static void main(String[] args) {
		GameControlPanel panel = new GameControlPanel(); // create the panel
		JFrame frame = new JFrame(); // create the frame
		// test filling in the data
		panel.setTurn(new ComputerPlayer("Col. Mustard", 0, 0, Color.YELLOW), 5);
		panel.setGuess("I have no guess!");
		panel.setGuessResult("So you have nothing?");
		
		frame.setContentPane(panel); // put the panel in the frame
		frame.setSize(WINDOW_WIDTH, WINDOW_HEIGHT); // size the frame
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE); // allow it to close
		frame.setVisible(true); // make it visible
		
	}
}
