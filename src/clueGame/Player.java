package clueGame;

import java.awt.Color;
import java.util.ArrayList;
import java.util.Random;

/*
 * Player Class - 
 *  
 * @author Carson Diamond
 * @author Charlie Dupras
 * @date 3/25/2024
 */

public abstract class Player {
	private String name;
	private Color color;
	private int row, column;
	
	private ArrayList<Card> hand = new ArrayList<Card>();
	private ArrayList<Card> seenPeople = new ArrayList<Card>();
	private ArrayList<Card> seenWeapons = new ArrayList<Card>();
	private ArrayList<String> seenPeopleStr = new ArrayList<String>();
	private ArrayList<String> seenWeaponsStr = new ArrayList<String>();
	
	public Player(String name) {
		this.name = name;
	}
	
	public Player(String name, int row, int col) {
		this.name = name;
		this.row = row;
		this.column = col;
	}
	
	public Card disproveSuggestion(String room, String person, String weapon) {
		ArrayList<Card> matching = new ArrayList<Card>();
		
		// iterate through hand
		for (Card C : hand) {
			if (room.equals(C.getCardName()) || person.equals(C.getCardName()) || weapon.equals(C.getCardName())) {
				matching.add(C);
			}
		}
		
		// if multiple matches, pick one randomly
		if (matching.size() > 1) {
			Random r = new Random();
			int low = 0;
			int high = matching.size();
			return matching.get(r.nextInt(high-low) + low);
		}
		if (matching.size() == 1) {
			return matching.get(0);
		} 
		return null;
	}

	public void updateSeen(Card card) {
		if (card.getCardType() == CardType.PERSON) {
			seenPeople.add(card);
			seenPeopleStr.add(card.getCardName());
		}
		if (card.getCardType() == CardType.WEAPON) {
			seenWeapons.add(card);
			seenWeaponsStr.add(card.getCardName());
		}
	}
	
	public abstract boolean isHuman();
	
	public void updateHand(Card card) {
		hand.add(card);
	}
	
	public ArrayList<Card> getHand() {
		return hand;
	}
	
	public ArrayList<Card> getSeenWeapons() {
		return seenWeapons;
	}

	public ArrayList<Card> getSeenPeople() {
		return seenPeople;
	}
	
	public void setSeenPeople(ArrayList<Card> seenPeople) {
		this.seenPeople = seenPeople;
	}
	
	public int getRow() {
		return row;
	}

	public int getColumn() {
		return column;
	}
	
	public void setPos(int row, int col) {
		this.row = row;
		this.column = col;
	}

	public ArrayList<String> getSeenPeopleStr() {
		return seenPeopleStr;
	}

	public ArrayList<String> getSeenWeaponsStr() {
		return seenWeaponsStr;
	}
}
