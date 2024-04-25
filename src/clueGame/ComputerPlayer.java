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
	private Solution accusation;

	// default constructor
	public ComputerPlayer(String name, int row, int col) {
		super(name, row, col);
		this.row = row;
		this.column = col;
	}

	// overloaded constructor
	public ComputerPlayer(String name, int row, int col, Color color) {
		super(name, row, col, color);
		this.row = row;
		this.column = col;
	}

	// overloaded constructor
	public ComputerPlayer(String name, int row, int col, String color) {
		super(name, row, col, color);
		this.row = row;
		this.column = col;
	}

	// overloaded constructor
	public ComputerPlayer(String name) {
		super(name);
	}

	// returns current player cell
	public BoardCell getCurrCell() {
		return board.getCell(row, column);
	}

	public void addVisited(BoardCell cell) {
		visitedList.add(cell);
	}

	// selects target from adjacency list
	@Override
	public BoardCell selectTarget(int roll, int newRow, int newCol) {
		Random r = new Random();
		int tempRow = 0;
		int tempCol = 0;
		
		board.calcTargets(board.getCell(newRow, newCol), roll);
		
		// look for room to move to
		for (BoardCell B : board.getTargets()) {
			if (B.isRoomCenter() && !(visitedList.contains(B))) {
				setPos(B.getRow(), B.getColumn());
				this.row = B.getRow();
				this.column = B.getColumn();
				visitedList.add(B);
				return board.getCell(row, column);
			}
		}
		// If no room on list (or all rooms previously visited) roll a random cell
		for (BoardCell B : board.getTargets()) {
			if (r.nextBoolean()) {
				setPos(B.getRow(), B.getColumn());
				this.row = B.getRow();
				this.column = B.getColumn();
				visitedList.add(B);
				return board.getCell(row, column);
			}
			tempRow = B.getRow();
			tempCol = B.getColumn();

		}
		// If all rolls are false, return the last one to avoid null errors
		setPos(tempRow, tempCol);
		this.row = tempRow;
		this.column = tempCol;
		visitedList.add(board.getCell(tempRow, tempCol));
		return board.getCell(tempRow, tempCol);
	}
	
	@Override
	public BoardCell selectTarget(int roll) {
		return selectTarget(roll, row, column);
	}

	// creates suggestion based on what's been seen and current room
	@Override
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
	
	// create and store accusation
	@Override
	public void createAccusation(Card room, Card person, Card weapon) {
		accusation = new Solution(room, person, weapon);
	}

	@Override
	public Solution getAccusation() {
		return accusation;
	}
	
	@Override
	public boolean isHuman() {
		return false;
	}

}
