package clueGame;

import java.awt.Color;
import java.awt.Graphics;
import java.util.ArrayList;
import java.util.Random;

/*
 * Player Class - abstract class extended by ComputerPlayer
 * and HumanPlayer
 *  
 * @author Carson Diamond
 * @author Charlie Dupras
 * @date 4/1/2024
 */

public abstract class Player {
	private String name;
	private Color color;
	private int row, column;
	
	private ArrayList<Card> hand = new ArrayList<Card>();
	private ArrayList<Card> seen = new ArrayList<Card>();
	private ArrayList<Card> seenPeople = new ArrayList<Card>();
	private ArrayList<Card> seenWeapons = new ArrayList<Card>();
	private ArrayList<String> seenPeopleStr = new ArrayList<String>();
	private ArrayList<String> seenWeaponsStr = new ArrayList<String>();
	private Boolean done = true;	// For testing purposes
	private boolean shouldAccuse = false;
	
	private Board board = Board.getInstance();
	
	// constructors
	
	public Player(String name) {
		this.name = name;
	}
	
	public Player(String name, int row, int col) {
		this.name = name;
		this.row = row;
		this.column = col;
	}
	
	public Player(String name, int row, int col, Color color) {
		this.name = name;
		this.row = row;
		this.column = col;
		this.color = color;
	}
	
	public Player(String name, int row, int col, String color) {
		this.name = name;
		this.row = row;
		this.column = col;
		
		switch (color) {
		case "Green":
			this.color = Color.GREEN;
			break;
		case "White":
			this.color = Color.WHITE;
			break;
		case "Purple":
			this.color = Color.MAGENTA;
			break;
		case "Black":
			this.color = Color.BLACK;
			break;
		case "Yellow":
			this.color = Color.YELLOW;
			break;
		case "Red":
			this.color = Color.RED;
			break;
		}
	}
	
	// return card that disproves a suggestion made
	public Card disproveSuggestion(String room, String person, String weapon) {
		ArrayList<Card> matching = new ArrayList<Card>();
		
		// iterate through hand
		for (Card C : hand) {
			if (room.equals(C.getCardName()) || 
				person.equals(C.getCardName()) || 
				weapon.equals(C.getCardName())) {
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

	// updates seen cards
	public void updateSeen(Card card) {
		if (card.getCardType() == CardType.PERSON) {
			seen.add(card);
			seenPeople.add(card);
			seenPeopleStr.add(card.getCardName());
		}
		if (card.getCardType() == CardType.WEAPON) {
			seen.add(card);
			seenWeapons.add(card);
			seenWeaponsStr.add(card.getCardName());
		}
		if (card.getCardType() == CardType.ROOM) {
			seen.add(card);
		}
	}
	
	// draws player on board
	public void drawPlayer(Graphics g, int rad, int x, int y) {
		g.setColor(color);
		g.drawOval(x, y, rad, rad);
		g.fillOval(x, y, rad, rad);
	}
	
	public abstract Solution createSuggestion();
	
	public abstract boolean isHuman();
	
	public abstract BoardCell selectTarget(int roll, int newRow, int newCol);
	public abstract BoardCell selectTarget(int roll);
	
	public void updateHand(Card card) {
		hand.add(card);
	}
	
	// getters and setters
	
	public boolean isInRoom() {
		return board.getCell(row, column).getRoom().getName() != "Walkway";
	}
	
	public ArrayList<Card> getHand() {
		return hand;
	}
	
	public ArrayList<Card> getSeen() {
		return seen;
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
	
	public String getName() {
		return name;
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
	
	public Color getColor() {
		return color;
	}
	
	public boolean isDone() {
		return done;
	}

	public boolean shouldAccuse() {
		return shouldAccuse;
	}

	public void setShouldAccuse(boolean shouldAccuse) {
		this.shouldAccuse = shouldAccuse;
	}

	public abstract void createAccusation(Card room, Card person, Card weapon);

	public abstract Solution getAccusation();
	
}
