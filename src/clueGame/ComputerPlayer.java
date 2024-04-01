package clueGame;

import java.awt.Color;
import java.util.ArrayList;
import java.util.Random;

/*
 * ComputerPlayer Class - 
 *  
 * @author Carson Diamond
 * @author Charlie Dupras
 * @date 3/25/2024
 */

public class ComputerPlayer extends Player {

	private ArrayList<Card> hand = new ArrayList<Card>();
	private ArrayList<Card> seenPeople = new ArrayList<Card>();
	private ArrayList<Card> seenWeapons = new ArrayList<Card>();
	private static Board board = Board.getInstance();
	private String name;
	private Color color;
	private int row, column;
	
	
	public ComputerPlayer(String name) {
		super(name);
	}

	@Override
	public void updateHand(Card card) {
		hand.add(card);
	}
	
	@Override
	public void updateSeen(Card card) {
		if(card.getCardType() == CardType.PERSON) {
			seenPeople.add(card);
		}
		if(card.getCardType() == CardType.WEAPON) {
			seenWeapons.add(card);
		}
	}


	@Override
	public ArrayList<Card> getHand() {
		return hand;
	}
	
	@Override
	public boolean isHuman() {
		return false;
	}
	
	@Override
	public Card disproveSuggestion(Card person, Card room, Card weapon) {
		Random r = new Random();
		int low = 0;
		int high = 0;
		for(Card C : hand) {
			if(C.equals(weapon) || C.equals(room) || C.equals(person)) {
				high++;
			}
		}
		if(high == 1 && hand.contains(weapon)) {
			return weapon;
		}
		if(high == 1 && hand.contains(room)) {
			return room;
		}
		if(high == 1 && hand.contains(person)) {
			return person;
		}
		
		
		
		return null;
	}
	
	public void selectTarget() {
		Random r = new Random();
		board.calcTargets(board.getCell(row, column), board.roll());
		for(BoardCell B : board.getTargets()) {
			if(B.isRoomCenter() && !(board.getVisited().contains(B))) {
				this.column = B.getColumn();
				this.row = B.getRow();
				break;
			} else {
				if(r.nextBoolean()) {
					this.column = B.getColumn();
					this.row = B.getRow();
					break;
				}
			}
		}
	}
	
	public void createSuggestion() {
		if(board.getCell(row, column).getRoom().getName() != "Walkway") {
			if(seenPeople.size() == 5) {
				
			}
			if(seenWeapons.size() == 5) {
				for(Card C : board.getWeaponsList()) {
					if(seenWeapons.contains(C)) {
						
					} else {
						
					}
				}
			}
		}
	}

	
}
