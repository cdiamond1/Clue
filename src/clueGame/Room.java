package clueGame;

import java.util.ArrayList;

/*
 * Room Class - Stores data for rooms 
 *  
 * @author Carson Diamond
 * @author Charlie Dupras
 * @date 2/28/2024
 */

public class Room {
	private String name;
	private String symbol;
	private BoardCell centerCell;
	private BoardCell labelCell;

	private ArrayList<BoardCell> doors = new ArrayList<BoardCell>();
	// Removed two unused vars (BoardCell secretPassageCell, char secretPassageTargetKey)

	public Room() {
		name = "Unused";
	}
	
	public Room(String name) {
		this.name = name;
	}
	
	public void addDoorCell(BoardCell door) {
		doors.add(door);
	}
	
	public String getSymbol() {
		return symbol;
	}
	
	public String getName() {
		return name;
	}

	public BoardCell getLabelCell() {
		return labelCell;
	}
	
	public void setLabelCell(BoardCell cell) {
		this.labelCell = cell;
	}
	
	public BoardCell getCenterCell() {
		return centerCell;
	}
	
	public void setCenterCell(BoardCell cell) {
		this.centerCell = cell;
	}
	
	public ArrayList<BoardCell> getDoors() {
		return doors;
	}
}
