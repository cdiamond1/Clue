package clueGame;

import java.awt.Color;
import java.util.ArrayList;
import java.util.Random;

/*
 * ComputerPlayer Class - extends player class, includes methods
 * used by computer players
 *  
 * @author Carson Diamond
 * @author Charlie Dupras
 * @date 4/1/2024
 */

public class ComputerPlayer extends Player {

	private static Board board = Board.getInstance();
	private ArrayList<Card> totalPeople = board.getPlayerCardList();
	private ArrayList<Card> totalWeapons = board.getWeaponsList();
	private ArrayList<Card> totalRooms = board.getRoomsList();
	private ArrayList<BoardCell> visitedList = new ArrayList<BoardCell>();
	private int row, column;

	public ComputerPlayer(String name, int row, int col) {
		super(name, row, col);
		this.row = row;
		this.column = col;
	}
	
	public ComputerPlayer(String name, int row, int col, Color color) {
		super(name, row, col, color);
		this.row = row;
		this.column = col;
	}
	
	public ComputerPlayer(String name, int row, int col, String color) {
		super(name, row, col, color);
		this.row = row;
		this.column = col;
	}

	public ComputerPlayer(String name) {
		super(name);
	}

	@Override
	public boolean isHuman() {
		return false;
	}

	public BoardCell getCurrCell() {
		return board.getCell(row, column);
	}
	
	public void addVisited(BoardCell cell) {
		visitedList.add(cell);
	}

	public BoardCell selectTarget(int roll) {
		Random r = new Random();
		int tempRow = 0; 
		int tempCol = 0;
		board.calcTargets(board.getCell(row, column), roll);
		for (BoardCell B : board.getTargets()) {
			if (B.isRoomCenter() && !(visitedList.contains(B))) {
				this.column = B.getColumn();
				this.row = B.getRow();
				visitedList.add(B);
				return board.getCell(row, column);
			}
		}
		// If no room on list (or all rooms previously visited) roll a random cell
		for (BoardCell B : board.getTargets()) {
			if (r.nextBoolean()) {
				this.column = B.getColumn();
				this.row = B.getRow();
				visitedList.add(B);
				return board.getCell(row, column);
			}
			tempRow = B.getRow();
			tempCol = B.getColumn();
			
		}
		// If all rolls are false, return the last one to avoid null errors
		this.column = tempCol;
		this.row = tempRow;
		visitedList.add(board.getCell(tempRow, tempCol));
		return board.getCell(tempRow, tempCol);
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
			Random r = new Random();
			int low = 0;
			int high = 0;
			// pick random person and weapon, check against seen lists
			do {
				high = totalPeople.size();
				suggPerson = totalPeople.get(r.nextInt(high - low) + low);
			} while (super.getSeenPeopleStr().contains(suggPerson.getCardName()));

			do {
				high = totalWeapons.size();
				suggWeapon = totalWeapons.get(r.nextInt(high - low) + low);
			} while (super.getSeenWeaponsStr().contains(suggWeapon.getCardName()));

			return new Solution(suggRoom, suggPerson, suggWeapon);
		}

		return null;
	}

	public void setPos(int i, int j) {
		this.row = i;
		this.column = j;
	}
}
