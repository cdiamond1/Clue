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
	private boolean human;
	
	private ArrayList<Card> hand = new ArrayList<Card>();
	
	public void updateHand(Card card) {
		
	}

	public boolean isHuman() {
		return human;
	}

	public ArrayList<Card> getHand() {
		return hand;
	}
}
