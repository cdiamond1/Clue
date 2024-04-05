package clueGame;

import java.awt.Color;
import java.awt.GridLayout;
import java.util.ArrayList;

import javax.swing.BorderFactory;
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
	JPanel panel = new JPanel();
	ArrayList<Card> hand = new ArrayList<Card>();
	ArrayList<Card> seen = new ArrayList<Card>();
	int cardCount;

	// constructor
	public GameCardsPanel() {
		setLayout(new GridLayout(3, 0));
		setBorder(new TitledBorder(new EtchedBorder(), "Known Cards"));
		
		addTestCards();
		
		add(panel);
		this.createAll();
	}

	public void addTestCards() {
		hand.add(new Card("Professor Plum", CardType.PERSON));
		hand.add(new Card("Miss Scarlet", CardType.PERSON));
		hand.add(new Card("Wrench", CardType.WEAPON));
		
		
		//Add these to test overlapping
		/*
		seen.add(new Card("Mrs. White", CardType.PERSON));
		seen.add(new Card("Colonel Mustard", CardType.PERSON));
		seen.add(new Card("Reverand Green", CardType.PERSON));
		seen.add(new Card("Mrs. Peacock", CardType.PERSON));
		seen.add(new Card("Colonel Mustard", CardType.PERSON));
		seen.add(new Card("Mrs. White", CardType.PERSON));
		
		seen.add(new Card("Colonel Mustard", CardType.PERSON));
		seen.add(new Card("Mrs. Peacock", CardType.PERSON));
		*/
		
		seen.add(new Card("Reverand Green", CardType.PERSON));
		seen.add(new Card("Kitchen", CardType.ROOM));
		seen.add(new Card("Knife", CardType.WEAPON));
	}
	
	public JPanel createPeoplePanel() {
		cardCount = 0;
		panel.setLayout(new GridLayout(2, 0));
		panel.setBorder(new TitledBorder(new EtchedBorder(), "People"));
		JPanel panel2 = new JPanel();
		panel2.setBorder(new TitledBorder(new EtchedBorder(), "In Hand:"));
		panel2.setLayout(new GridLayout(0, 1));
		JLabel Card = new JLabel();
		Card.setBorder(BorderFactory.createLineBorder(Color.GRAY));
		for (Card C : hand) {
			if (C.getCardType() == CardType.PERSON) {
				Card = new JLabel(C.getCardName());
				cardCount++;
				panel2.add(Card);
			}
		}
		if (cardCount == 0) {
			Card = new JLabel("None");
			panel2.add(Card);
		}
		cardCount = 0;
		panel.add(panel2);
		panel2 = new JPanel();
		panel2.setBorder(new TitledBorder(new EtchedBorder(), "Seen:"));
		panel2.setLayout(new GridLayout(0,1));
		Card = new JLabel();											// Tried JTextField and JPanel but JLabel seems to work the best
		Card.setBorder(BorderFactory.createLineBorder(Color.GRAY));		// Maybe there's a better way, but JLabel overlaps the least atm
		for (Card C : seen) {											// Assignment uses JTextFields I think
			if (C.getCardType() == CardType.PERSON) {
				Card = new JLabel(C.getCardName());
				cardCount++;
				panel2.add(Card);
			}
		}
		if (cardCount == 0) {
			Card = new JLabel("None");
			panel2.add(Card);
		}
		panel2.revalidate();
		panel.add(panel2);
		return panel;
	}

	public JPanel createRoomPanel() {
		cardCount = 0;
		panel = new JPanel();
		panel.setLayout(new GridLayout(2, 0));
		panel.setBorder(new TitledBorder(new EtchedBorder(), "Rooms"));
		JPanel panel2 = new JPanel();
		panel2.setBorder(new TitledBorder(new EtchedBorder(), "In Hand:"));
		JLabel Card = new JLabel();
		Card.setBorder(BorderFactory.createLineBorder(Color.GRAY));
		for (Card C : hand) {
			if (C.getCardType() == CardType.ROOM) {
				Card = new JLabel(C.getCardName());
				cardCount++;
				panel2.add(Card);
			}
		}
		if (cardCount == 0) {
			Card = new JLabel("None");
			panel2.add(Card);
		}
		cardCount = 0;
		panel.add(panel2);
		panel2 = new JPanel();
		panel2.setBorder(new TitledBorder(new EtchedBorder(), "Seen:"));
		Card = new JLabel();
		Card.setBorder(BorderFactory.createLineBorder(Color.GRAY));
		for (Card C : seen) {
			if (C.getCardType() == CardType.ROOM) {
				Card = new JLabel(C.getCardName());
				cardCount++;
				panel2.add(Card);
			}
		}
		if (cardCount == 0) {
			Card = new JLabel("None");
			panel2.add(Card);
		}
		panel.add(panel2);
		return panel;
	}
	
	public JPanel createWeaponPanel() {
		cardCount = 0;
		panel = new JPanel();
		panel.setLayout(new GridLayout(2, 0));
		panel.setBorder(new TitledBorder(new EtchedBorder(), "Weapons"));
		JPanel panel2 = new JPanel();
		panel2.setBorder(new TitledBorder(new EtchedBorder(), "In Hand:"));
		JLabel Card = new JLabel();
		Card.setBorder(BorderFactory.createLineBorder(Color.GRAY));
		for (Card C : hand) {
			if (C.getCardType() == CardType.WEAPON) {
				Card = new JLabel(C.getCardName());
				cardCount++;
				panel2.add(Card);
			}
		}
		if (cardCount == 0) {
			Card = new JLabel("None");
			panel2.add(Card);
		}
		cardCount = 0;
		panel.add(panel2);
		panel2 = new JPanel();
		panel2.setBorder(new TitledBorder(new EtchedBorder(), "Seen:"));
		Card = new JLabel();
		Card.setBorder(BorderFactory.createLineBorder(Color.GRAY));
		for (Card C : seen) {
			if (C.getCardType() == CardType.WEAPON) {
				Card = new JLabel(C.getCardName());
				cardCount++;
				panel2.add(Card);
			}
		}
		if (cardCount == 0) {
			Card = new JLabel("None");
			panel2.add(Card);
		}
		panel.add(panel2);
		return panel;
	}
	
	public void createAll() {
		panel = createPeoplePanel();
		add(panel);
		panel = createRoomPanel();
		add(panel);
		panel = createWeaponPanel();
		add(panel);
	}
	
	public void updateAll() {
		
		//	Cards to test
		seen.add(new Card("Mrs. White", CardType.PERSON));
		seen.add(new Card("Colonel Mustard", CardType.PERSON));
		seen.add(new Card("Reverand Green", CardType.PERSON));
		
		this.removeAll();
		
		panel = new JPanel();
		
		this.createAll();
		this.repaint();
		this.revalidate();
		
	}
		
	public static void main(String[] args) {
		GameCardsPanel panel = new GameCardsPanel(); // create the panel
		JFrame frame = new JFrame();
		
		panel.updateAll();
		
		frame.setContentPane(panel); // put the panel in the frame
		frame.setSize(180, 750); // size the frame
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE); // allow it to close
		frame.setVisible(true); // make it visible
	}
}
