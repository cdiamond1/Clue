package clueGame;

import java.util.ArrayList;

/*
 * ComputerPlayer Class - 
 *  
 * @author Carson Diamond
 * @author Charlie Dupras
 * @date 3/25/2024
 */

public class ComputerPlayer extends Player {

	private ArrayList<Card> hand = new ArrayList<Card>();
	
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
		return false;
	}
	
}
