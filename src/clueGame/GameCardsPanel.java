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
	private JPanel panel = new JPanel();
	private ArrayList<Card> hand = new ArrayList<Card>();
	private ArrayList<Card> seen = new ArrayList<Card>();
	private int cardCount;

	// constructor
	public GameCardsPanel() {
		setLayout(new GridLayout(3, 0));
		setBorder(new TitledBorder(new EtchedBorder(), "Known Cards"));
		
		add(panel);
		
		this.createAll();
	}

	// create all subpanels
	public void createAll() {
		panel = createPeoplePanel();
		add(panel);
		panel = createRoomPanel();
		add(panel);
		panel = createWeaponPanel();
		add(panel);
	}
	
	public void updateHand(ArrayList<Card> inHand) {
		this.hand = inHand;
	}
	
	public void updateSeen(ArrayList<Card> inSeen) {
		this.seen = inSeen;
	}
	
	public void addSeenCardTest(Card c) {
		this.seen.add(c);
	}
	
	// test function - not used in final implementation
	public void addTestCards() {
		hand.add(new Card("Professor Plum", CardType.PERSON));
		hand.add(new Card("Miss Scarlet", CardType.PERSON));
		hand.add(new Card("Wrench", CardType.WEAPON));
		
		seen.add(new Card("Reverand Green", CardType.PERSON));
		seen.add(new Card("Kitchen", CardType.ROOM));
		seen.add(new Card("Knife", CardType.WEAPON));
	}
	
	// people card panel
	public JPanel createPeoplePanel() {
		cardCount = 0;
		// panel setup
		panel.setLayout(new GridLayout(2, 0));
		panel.setBorder(new TitledBorder(new EtchedBorder(), "People"));
		
		JPanel panel2 = new JPanel();
		panel2.setBorder(new TitledBorder(new EtchedBorder(), "In Hand:"));
		panel2.setLayout(new GridLayout(0, 1));
		
		JLabel Card = new JLabel();
		Card.setBorder(BorderFactory.createLineBorder(Color.GRAY));
		
		// iterate through every card in hand
		for (Card C : hand) {
			if (C.getCardType() == CardType.PERSON) {
				Card = new JLabel(C.getCardName());
				cardCount++;
				panel2.add(Card);
			}
		}
		
		// say none if no cards
		if (cardCount == 0) {
			Card = new JLabel("None");
			panel2.add(Card);
		}
		
		// seen cards
		cardCount = 0;
		panel.add(panel2);
		panel2 = new JPanel();
		panel2.setBorder(new TitledBorder(new EtchedBorder(), "Seen:"));
		panel2.setLayout(new GridLayout(0,1));
		Card = new JLabel();
		Card.setBorder(BorderFactory.createLineBorder(Color.GRAY));

		// iterate through every card that's been seen
		for (Card C : seen) {
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

	// room panel - works similarly to player panel
	public JPanel createRoomPanel() {
		cardCount = 0;
		panel = new JPanel();
		panel.setLayout(new GridLayout(2, 0));
		panel.setBorder(new TitledBorder(new EtchedBorder(), "Rooms"));
		JPanel panel2 = new JPanel();
		panel2.setBorder(new TitledBorder(new EtchedBorder(), "In Hand:"));
		panel2.setLayout(new GridLayout(0, 1));
		JLabel Card = new JLabel();
		Card.setBorder(BorderFactory.createLineBorder(Color.GRAY));
		
		// iterate through every card in hand
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
		panel2.setLayout(new GridLayout(0, 1));
		Card = new JLabel();
		Card.setBorder(BorderFactory.createLineBorder(Color.GRAY));
		
		// iterate through every card that's been seen
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
	
	// weapons panel
	public JPanel createWeaponPanel() {
		cardCount = 0;
		panel = new JPanel();
		panel.setLayout(new GridLayout(2, 0));
		panel.setBorder(new TitledBorder(new EtchedBorder(), "Weapons"));
		JPanel panel2 = new JPanel();
		panel2.setLayout(new GridLayout(0, 1));
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
		panel2.setLayout(new GridLayout(0, 1));
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
	
	// removes everything, remakes panel, and adds everything again
	public void updateAll() {
		
		this.removeAll();
		
		panel = new JPanel();
		
		this.createAll();
		this.repaint();
		this.revalidate();
	}
}
