package clueGame;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Map;
import java.util.Scanner;
import java.util.Set;

/* Board class - contains our board
 * 
 * @Author: Carson D.
 * @Author: Charlie D.
 * 
 * @Date: 2/27/2024
 * 
 */

public class Board {

	private Set<BoardCell> targets = new HashSet<BoardCell>();;
	private Set<BoardCell> boardCells = new HashSet<BoardCell>();
	private BoardCell test;

	// C14A-1 additional variables
	public BoardCell[][] grid;
	private Set<BoardCell> visited = new HashSet<BoardCell>();
	private final static int COLS = 30;
	private final static int ROWS = 30;

	// C14A-2 additional variables
	private String layoutConfigFile;
	private String setupConfigFile;
	private String initial;
	private Map<Character, Room> roomMap;
	private static Board theInsrance;
	private Scanner in;
	private File csv;
	private File txt;

	// Rooms
	protected Room Engine_Room = new Room("Engine Room");
	protected Room Navigation = new Room("Navigation");
	protected Room Shields = new Room("Shields");
	protected Room Life_Support = new Room("Life Support");
	protected Room Weapons = new Room("Weapons");
	protected Room Medical_Bay = new Room("Medical Bay");
	protected Room Cargo = new Room("Cargo");
	protected Room Hydroponics = new Room("Hydroponics");
	protected Room Reactor = new Room("Reactor");
	protected Room Walkway = new Room("Walkway");
	protected Room Unused = new Room();

	
	// Temp Vars
	private BoardCell temp;

	/*
<<<<<<< HEAD
	 * variable and methods used for singleton pattern
	 */
	private static Board theInstance = new Board();

	// constructor is private to ensure only one can be created
	private Board() {
		super();
	}

	// this method returns the only Board
	public static Board getInstance() {
		return theInstance;
	}

	/*
	 * initialize the board (since we are using singleton pattern)
	 */
	public void initialize() {
		try {
			loadLayoutConfig();
			loadSetupConfig();
		} catch (BadConfigFormatException e) {
			e.printStackTrace();
		}
	}
	
     public void readData(String fileName) {
    	 ArrayList<String[]> dataList = new ArrayList<>();
    	 
    	 // read file
    	 try {
    		 File file = new File(fileName);
    		 Scanner scanner = new Scanner(file);
    		 
    		 int rows = 0;
    		 String currLine;
    		 while(scanner.hasNextLine()) {
    			 rows++;
    			 // add all file data to string
    			 currLine = scanner.nextLine();
    			 dataList.add(currLine.split(","));
    		 }
    		 
    		 scanner.close();
    	 }
    	 catch (FileNotFoundException e) {
    		 e.printStackTrace();
    	 }
     }

	public void calcTargets(BoardCell startCell, int pathLength) {
		visited.add(startCell);

		// iterate through every cell of startCell adjacent list
		for (BoardCell cell : startCell.getAdjList()) {
			// if already visited/isRoom/isOccupied, skip this cell
			if (visited.contains(cell) || cell.isOccupied() || cell.isRoom())
				continue;

			// add cell to visited list
			visited.add(cell);

			// add cell to targets if out of moves
			if (pathLength == 1) {
				targets.add(cell);
			}
			// recursive call if moves left
			else {
				this.calcTargets(cell, pathLength - 1);
			}

			visited.remove(cell);
		}
	}

	// getCell: returns cell given row+column. If cell doesn't exist, returns null
	public BoardCell getCell(int col, int row) {
		// iterate through each cell in targets list
		for (BoardCell cell : boardCells) {
			if (cell.getColumn() == col && cell.getRow() == row) {
				return cell;
			}
		}
		// if no cell returned, return null (temp for now)
		BoardCell temp = new BoardCell(row, col, "X");
		return temp;
	}

	public Set<BoardCell> getTargets() {
		return targets;
	}

	public void loadSetupConfig() throws BadConfigFormatException {
		String line;
		String[] lineSplit = new String[3];
		String[] symbols = new String[11];
		try {
			in = new Scanner(txt);
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}
		while(in.hasNextLine()) {
			line = in.nextLine();
			if(line.charAt(0) == '/') {
				continue;
			}
			lineSplit = line.split(", ");
			if(!lineSplit[0].equals("Room") || !lineSplit[0].equals("Space")) {
				throw new BadConfigFormatException("Format error in setup file: " + lineSplit[0]);
			}
		}
	}

	public void loadLayoutConfig() throws BadConfigFormatException {
		// Intializes and fills the grid with cells
				grid = new BoardCell[ROWS][COLS];
				try {
					in = new Scanner(csv);
					in.useDelimiter(",");
				} catch (FileNotFoundException e) {
					e.printStackTrace();
				}
				for (int rows = 0; rows < ROWS; rows++) {
					for (int cols = 0; cols < COLS - 1; cols++) {
						if (in.hasNext()) {
							initial = in.next();
							if(initial.equals("") || initial.equals(" ") || initial.equals(null)) {
								throw new BadConfigFormatException();
							}
							temp = new BoardCell(rows, cols, initial);
							
							// Reading first char
							switch (initial.charAt(0)) {
							case 'E':
								temp.setRoomLoc(Engine_Room);
								temp.setRoom(true);
								break;

							case 'N':
								temp.setRoomLoc(Navigation);
								temp.setRoom(true);
								break;

							case 'S':
								temp.setRoomLoc(Shields);
								temp.setRoom(true);
								break;

							case 'L':
								temp.setRoomLoc(Life_Support);
								temp.setRoom(true);
								break;

							case 'P':
								temp.setRoomLoc(Weapons);
								temp.setRoom(true);
								break;

							case 'M':
								temp.setRoomLoc(Medical_Bay);
								temp.setRoom(true);
								break;

							case 'C':
								temp.setRoomLoc(Cargo);
								temp.setRoom(true);
								break;

							case 'H':
								temp.setRoomLoc(Hydroponics);
								temp.setRoom(true);
								break;

							case 'R':
								temp.setRoomLoc(Reactor);
								temp.setRoom(true);
								break;

							case 'W':
								temp.setRoomLoc(Walkway);
								temp.setRoom(true);
								break;
								
							default:
								if(initial.charAt(0) != 'X') {
									throw new BadConfigFormatException("Bad room symbol: " + initial.charAt(0));
								}
								temp.setRoomLoc(Unused);
								temp.setRoom(false);
								temp.setOccupied(true);
								break;
							}

							// Reading second char if there is one
							if (initial.length() > 1) {
								switch (initial.charAt(1)) {
								case '<':
									temp.setDoorway(true);
									temp.setDoorDirection(DoorDirection.LEFT);
									break;
								case '^':
									temp.setDoorway(true);
									temp.setDoorDirection(DoorDirection.UP);
									break;
								case '>':
									temp.setDoorway(true);
									temp.setDoorDirection(DoorDirection.RIGHT);
									break;
								case 'v':
									temp.setDoorway(true);
									temp.setDoorDirection(DoorDirection.DOWN);
									break;
								case '*':
									temp.setRoomCenter(true);
									temp.getRoom().setCenterCell(temp);
									break;
								case '#':
									temp.setRoomLabel(true);
									temp.getRoom().setLabelCell(temp);
									break;
								default:
									temp.setSecretPassage(initial.charAt(1));
									break;
								}
							}
							grid[rows][cols] = temp;
							boardCells.add(temp);
						}
					}
				}

				// Generates each cells adjacency lists (Attempted to do in a switch/case but
				// couldn't get it to compile)
				for (int y = 0; y < ROWS; y++) {
					for (int x = 0; x < COLS; x++) {
						test = getCell(x, y);
						temp = getCell(x + 1, y); // Tests adjacent cell to the right
						if (temp != null) {
							if (temp.isOccupied() == false && temp.isRoom() == false) {
								test.addAdjacency(temp);
							}
						}
						temp = getCell(x - 1, y); // Tests adjacent cell to the left
						if (temp != null) {
							if (temp.isOccupied() == false && temp.isRoom() == false) {
								test.addAdjacency(temp);
							}
						}
						temp = getCell(x, y + 1); // Tests adjacent cell above
						if (temp != null) {
							if (temp.isOccupied() == false && temp.isRoom() == false) {
								test.addAdjacency(temp);
							}
						}
						temp = getCell(x, y - 1); // Tests adjacent cell below
						if (temp != null) {
							if (temp.isOccupied() == false && temp.isRoom() == false) {
								test.addAdjacency(temp);
							}
						}
					}
				}
	}

	public void setConfigFiles(String string, String string2) {
		csv = new File(string);
		txt = new File(string2);

	}

	public Room getRoom(char c) {
		Room room = new Room();
		switch (c) {
		case 'E':
			room = new Room("Engine Room");
			break;

		case 'N':
			room = new Room("Navigation");
			break;

		case 'S':
			room = new Room("Shields");
			break;

		case 'L':
			room = new Room("Life Support");
			break;

		case 'P':
			room = new Room("Weapons");
			break;

		case 'M':
			room = new Room("Medical Bay");
			break;

		case 'C':
			room = new Room("Cargo");
			break;

		case 'H':
			room = new Room("Hydroponics");
			break;

		case 'R':
			room = new Room("Reactor");
			break;

		case 'W':
			room = new Room("Walkway");
			break;

		default:
			break;
		}
		return room;
	}

	public Room getRoom(BoardCell cell) {
		return cell.getRoom();
	}

	public int getNumRows() {
		return ROWS;
	}

	public int getNumColumns() {
		return COLS;
	}

}
