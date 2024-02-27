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
	
	public BoardCell(int row, int column) {
		this.row = row;
		this.column = column;
	}
	
	public Set<BoardCell> getAdjList() {
		return adjList;
	}
	
	// add cell to adjacency list
	public void addAdjacency(BoardCell cell) {
		adjList.add(cell);
	}

	public boolean isRoom() {
		return isRoom;
	}

	public void setRoom(boolean isRoom) {
		this.isRoom = isRoom;
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

}
