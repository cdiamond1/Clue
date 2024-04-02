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

	@Override
	public void updateSeen(Card card) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public Card disproveSuggestion(String room, String person, String weapon) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void setSeenPeople(ArrayList<Card> seenList) {
		// TODO Auto-generated method stub
		
	}
	
}
