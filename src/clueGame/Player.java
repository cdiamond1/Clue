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
	
	public abstract void updateHand(Card card);

	public abstract boolean isHuman();

	public abstract ArrayList<Card> getHand();
}
