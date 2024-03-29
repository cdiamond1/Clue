package clueGame;

import java.util.ArrayList;

/*
 * HumanPlayer Class - 
 *  
 * @author Carson Diamond
 * @author Charlie Dupras
 * @date 3/25/2024
 */

public class HumanPlayer extends Player{

	private ArrayList<Card> hand = new ArrayList<Card>();
	
	public HumanPlayer(String name) {
		super(name);
	}

	@Override
	public void updateHand(Card card) {
		hand.add(card);
	}

	@Override
	public ArrayList<Card> getHand() {
		return hand;
	}

	@Override
	public boolean isHuman() {
		return true;
	}
	
}
