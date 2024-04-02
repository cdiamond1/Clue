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
		Card suggPerson = null;
		Card suggWeapon = null;
		Card suggRoom = null;
		
		if (board.getCell(row, column).getRoom().getName() != "Walkway") {
			// find card for current room
			for (Card C : totalRooms) {
				if (board.getCell(row, column).getRoom().getName().equals(C.getCardName())) {
					suggRoom = C;
				}
			}
			
			// pick random person and weapon, check against seen lists
			do {
				Random r = new Random();
				int low = 0;
				int high = totalPeople.size();
				suggPerson = totalPeople.get(r.nextInt(high-low) + low);
			}
			while(super.getSeenPeopleStr().contains(suggPerson.getCardName()));
			
			do {
				Random r = new Random();
				int low = 0;
				int high = totalWeapons.size();
				suggWeapon = totalWeapons.get(r.nextInt(high-low) + low);
			}
			while (super.getSeenWeaponsStr().contains(suggWeapon.getCardName()));
			
			
			return new Solution(suggRoom, suggPerson, suggWeapon);			
		}		
		
		return null;
	} 
	
	

}
