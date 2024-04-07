package clueGame;

import java.awt.Color;
import java.awt.Graphics;
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
	private boolean isRoomLabel;
	private boolean isRoomCenter;
	private boolean isDoorway;
	private boolean isSecretPassage;
	private boolean isWalkway = false;
	private boolean isWall = false;

	private char seceretPassage;
	private Room room;
	
	private Color color;

	// default constructor
	public BoardCell() {
		this.row = 0;
		this.column = 0;
		this.intial = null;
	}
	
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
	
	public void drawCell(Graphics g, int width, int height, int x, int y) {
		g.setColor(Color.BLACK);
		g.drawRect(x, y, width, height);
		
		if (this.isWalkway()) {
			g.setColor(Color.YELLOW);
		} 
		else if (this.isWall()) {
			g.setColor(Color.BLACK);
		}
		else if (this.isRoom()) {
			g.setColor(Color.GRAY);
		}
		else {
			g.setColor(Color.BLACK);
		}
		
		g.fillRect(x, y, width, height);
		
		// TODO: add room labels
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
		this.isRoomLabel = isLabel;
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
		return isDoorway;
	}

	public void setDoorway(boolean door) {
		this.isDoorway = door;
	}

	public DoorDirection getDoorDirection() {
		return doorDirection;
	}

	public void setDoorDirection(DoorDirection direction) {
		this.doorDirection = direction;
	}

	public boolean isLabel() {
		return isRoomLabel;
	}

	public void setLabel(boolean label) {
		this.isRoomLabel = label;
	}

	public boolean isRoomCenter() {
		return isRoomCenter;
	}

	public void setRoomCenter(boolean isRoomCenter) {
		this.isRoomCenter = isRoomCenter;
	}

	public char getSecretPassage() {
		return seceretPassage;
	}

	public boolean isSecretPassage() { // Might be able to get rid of this one
		return isSecretPassage;
	}

	public void setSecretPassage(char room) {
		isSecretPassage = true;
		this.seceretPassage = room;
	}
	
	public String getSymbol() {
		return intial;
	}
	
	public boolean isWalkway() {
		return isWalkway;
	}

	public void setWalkway(boolean isWalkway) {
		this.isWalkway = isWalkway;
	}
	
	public boolean isWall() {
		return isWall;
	}

	public void setWall(boolean isWall) {
		this.isWall = isWall;
	}

}
