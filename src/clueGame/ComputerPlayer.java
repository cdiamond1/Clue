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

	
	
	
	private static Board board = Board.getInstance();
	private ArrayList<Card> totalPeople = board.getPlayerCardList();
	private ArrayList<Card> totalWeapons = board.getWeaponsList();
	private ArrayList<Card> totalRooms = board.getRoomsList();
	private String name;
	private Color color;
	private int row, column;

	public ComputerPlayer(String name) {
		super(name);
	}

	

	@Override
	public boolean isHuman() {
		return false;
	}

	@Override
	public Card disproveSuggestion(String room, String person, String weapon) {
		ArrayList<Card> matching = new ArrayList<Card>();
		for(Card C : super.getHand()) {
			if(room.equals(C.getCardName()) || person.equals(C.getCardName()) || weapon.equals(C.getCardName())) {
				matching.add(C);
			}
		}
		if(matching.size() > 1) {
			Random r = new Random();
			int low = 0;
			int high = matching.size();
			return matching.get(r.nextInt(high-low) + low);
		} if(matching.size() == 1) {
			return matching.get(0);
		} 
		return null;
	}

	public BoardCell selectTarget() {
		Random r = new Random();
		board.calcTargets(board.getCell(row, column), board.roll());
		for (BoardCell B : board.getTargets()) {
			if (B.isRoomCenter() && !(board.getVisited().contains(B))) {
				this.column = B.getColumn();
				this.row = B.getRow();
				return board.getCell(row, column);
			} else {
				if (r.nextBoolean()) {
					this.column = B.getColumn();
					this.row = B.getRow();
					return board.getCell(row, column);
				}
			}
		}
		return null;
	}

	public Solution createSuggestion() {
		Card tempPerson = null;
		Card tempWeapon = null;
		Card tempRoom = null;
		if (board.getCell(row, column).getRoom().getName() != "Walkway") {
			for(Card C : totalRooms) {
				if(board.getCell(row, column).getRoom().getName().equals(C.getCardName())) {
					tempRoom = C;
				}
			}
			for(Card C : totalPeople) {
				if(super.getSeenPeople().contains(C)) {
					continue;
				} else {
					tempPerson = C;
					break;
				}
			}
			for(Card C : totalWeapons) {
				if(super.getSeenWeapons().contains(C)) {
					continue;
				} else {
					
				}
			}
		}
		return null;
	}
	
	

}
