package clueGame;

import java.awt.Color;
import java.util.ArrayList;

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
	
	public Player(String name) {
		this.name = name;
	}
	
	public Player(String name, int row, int col) {
		this.name = name;
		this.row = row;
		this.column = col;
	}
	
	public abstract void updateHand(Card card);
	
	public abstract void updateSeen(Card card);

	public abstract boolean isHuman();

	public abstract ArrayList<Card> getHand();
	
	public  abstract Card disproveSuggestion(String room, String person, String weapon);
}
