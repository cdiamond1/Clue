package clueGame;

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
	
	
	public Room() {
		name = "Unused";
	}
	
	public Room(String name) {
		this.name = name;
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
}
