package clueGame;

import java.util.HashSet;
import java.util.Set;

/* BoardCell class - contains our board cells
 * 
 * @Author: Carson D.
 * @Author: Charlie D.
 * 
 * @Date: 2/27/2024
 * 
 */

public class BoardCell {

	private int row, column;
	private boolean isOccupied = false;
	private boolean isRoom = false;
	private Set<BoardCell> adjList = new HashSet<BoardCell>();

	// C14A-2 additional variables
	private String intial;
	private DoorDirection doorDirection;
	private boolean roomLabel;
	private boolean roomCenter;
	private boolean doorway;
	private boolean secretPass;
	private char seceretPassage;
	private Room room;

	public BoardCell(int row, int column, String intial) {
		this.row = row;
		this.column = column;
		this.intial = intial;
	}

	public Set<BoardCell> getAdjList() {
		return adjList;
	}

	// add cell to adjacency list
	public void addAdjacency(BoardCell cell) {
		adjList.add(cell);
	}

	// Getters and Setters
	public boolean isRoom() {
		return isRoom;
	}

	public void setRoom(boolean isRoom) {
		this.isRoom = isRoom;
	}
	
	public Room getRoom() {
		return room;
	}
	
	public void setRoomLoc(Room room) {
		this.room = room;
	}
	
	public void setRoomLabel(boolean isLabel) {
		this.roomLabel = isLabel;
	}
	
	public boolean isOccupied() {
		return isOccupied;
	}

	public void setOccupied(boolean isOccupied) {
		this.isOccupied = isOccupied;
	}

	public int getRow() {
		return row;
	}

	public int getColumn() {
		return column;
	}

	public boolean isDoorway() {
		return doorway;
	}

	public void setDoorway(boolean door) {
		this.doorway = door;
	}

	public DoorDirection getDoorDirection() {
		return doorDirection;
	}

	public void setDoorDirection(DoorDirection direction) {
		this.doorDirection = direction;
	}

	public boolean isLabel() {
		return roomLabel;
	}

	public void setLabel(boolean label) {
		this.roomLabel = label;
	}

	public boolean isRoomCenter() {
		return roomCenter;
	}

	public void setRoomCenter(boolean roomCenter) {
		this.roomCenter = roomCenter;
	}

	public char getSecretPassage() {
		return seceretPassage;
	}

	public boolean isSecretPassage() { // Might be able to get rid of this one
		return secretPass;
	}

	public void setSecretPassage(char room) {
		secretPass = true;
		this.seceretPassage = room;
	}
	
	public String getSymbol() {
		return intial;
	}

}
