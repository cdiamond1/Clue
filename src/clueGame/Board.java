package clueGame;

import java.awt.Graphics;
import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Random;
import java.util.Scanner;
import java.util.Set;

import javax.swing.JFrame;
import javax.swing.JPanel;

/* Board class - contains our board
 * 
 * @Author: Carson D.
 * @Author: Charlie D.
 * 
 * @Date: 4/1/2024
 */

public class Board extends JPanel {

	private Set<BoardCell> targets = new HashSet<BoardCell>();
	private Set<BoardCell> boardCells = new HashSet<BoardCell>();

	// C14A-1 additional variables
	public BoardCell[][] grid;
	private Set<BoardCell> visited = new HashSet<BoardCell>();

	private static int boardCols = 30;
	private static int boardRows = 30;

	// C14A-2 additional variables
	private String initial;
	private Map<Character, Room> roomMap = new HashMap<Character, Room>();
	private File csv;
	private File txt;
	
	private ArrayList<Card> deck = new ArrayList<Card>();
	private ArrayList<Player> playerList = new ArrayList<Player>();
	private ArrayList<Card> playerCardList = new ArrayList<Card>();
	private ArrayList<Card> roomsList = new ArrayList<Card>();
	private ArrayList<Card> weaponsList = new ArrayList<Card>();
	private ArrayList<Boolean> suggestionCheck = new ArrayList<Boolean>();
	private Solution Solution = new Solution();
	
	private static int panelWidth = 570;
	private static int panelHeight = 570;

	/*
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
			loadSetupConfig();
			loadLayoutConfig();
			calcAdjacencyList();
			generateSolution();
			//deal();
		} catch (BadConfigFormatException e) {
			e.printStackTrace();
		}
	}

	// readData gets passed a file name, and returns a 2D array of the
	// information in the file
	

	// Loads setup file, populating roomMap
	public void loadSetupConfig() throws BadConfigFormatException {
		Scanner in = null;
		String line;
		String[] lineSplit = new String[3];

		// read file
		try {
			// move global variables into methods if possible
			in = new Scanner(txt);
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}

		while (in.hasNextLine()) {
			line = in.nextLine();

			// skip comments
			if (line.charAt(0) == '/') {
				continue;
			}

			// split at comma
			lineSplit = line.split(", ");
			if(lineSplit[0].equals("Room") || lineSplit[0].equals("Space") || lineSplit[0].equals("Player") || lineSplit[0].equals("Weapon")) {
				
			} else {
				throw new BadConfigFormatException("Bad config file: " + lineSplit[0]);
			}
			
			if ((lineSplit[0].equals("Room") || lineSplit[0].equals("Space")) && lineSplit.length == 3) {
				// create room object
				Room temp = new Room(lineSplit[1]);
				roomMap.put(lineSplit[2].charAt(0), temp);
				
				if (lineSplit[0].equals("Room")) {
					deck.add(new Card(lineSplit[1], CardType.ROOM));
					roomsList.add(new Card(lineSplit[1], CardType.ROOM));
				}

			} 
			if (lineSplit[0].equals("Player")) {
				deck.add(new Card(lineSplit[1], CardType.PERSON));
				// add human player
				if (lineSplit[2].equals("human")) {
					HumanPlayer human = new HumanPlayer(lineSplit[1]);
					playerList.add(human);
					playerCardList.add(new Card(lineSplit[1], CardType.PERSON));
				}
				else {
					ComputerPlayer comp = new ComputerPlayer(lineSplit[1], Integer.parseInt(lineSplit[3]), Integer.parseInt(lineSplit[4]));
					playerList.add(comp);
					playerCardList.add(new Card(lineSplit[1], CardType.PERSON));
				}
				
				
			} 
			if (lineSplit[0].equals("Weapon")) {
				deck.add(new Card(lineSplit[1], CardType.WEAPON));
				weaponsList.add(new Card(lineSplit[1], CardType.WEAPON));
				
			}
		}
		in.close();
	}

	// loads game board layout
	public void loadLayoutConfig() throws BadConfigFormatException {
		ArrayList<String[]> dataList = new ArrayList<>();
		BoardCell temp; // Temp var

		// read data from file
		dataList = readData(csv);

		// create grid with proper size
		grid = new BoardCell[boardRows][boardCols];

		// iterate through every cell in game board
		for (int row = 0; row < boardRows; row++) {
			for (int col = 0; col < boardCols; col++) {
				initial = dataList.get(row)[col];
				// make temp variables in the method they are used in - it generally bad to have
				// global variables that can be moved into methods
				temp = new BoardCell(row, col, initial);

				// make initial.charAt(0) a variable for readability
				if (initial.charAt(0) == 'X') {
					temp.setRoomLoc(roomMap.get('X'));
					temp.setRoom(false);
					temp.setOccupied(true);
				} else if (initial.charAt(0) == 'W') {
					temp.setRoomLoc(roomMap.get('W'));
					temp.setRoom(true);
				} 
				if (roomMap.get(initial.charAt(0)) != null) {
					temp.setRoomLoc(roomMap.get(initial.charAt(0)));
					temp.setRoom(true);
				} else {
					throw new BadConfigFormatException("Bad room symbol: " + initial.charAt(0));
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

				// rename temp to be more descriptive
				grid[row][col] = temp;
				boardCells.add(temp);
			}
		}
	}
	
	public ArrayList<String[]> readData(File file) throws BadConfigFormatException {
		ArrayList<String[]> dataList = new ArrayList<>();

		// read file
		try {
			Scanner scanner = new Scanner(file);

			// reset boardRows count
			boardRows = 0;
			String currLine;
			// get lines from file one at a time - split each line at comma
			while (scanner.hasNextLine()) {
				// add all file data to string
				currLine = scanner.nextLine();
				if(currLine.charAt(currLine.length()-1) == ',') {
					throw new BadConfigFormatException();
				}
				String[] temp = currLine.split(",");
				dataList.add(temp);
				// check configuration
				for (String item : dataList.get(boardRows)) {
					if (item.equals("") || item.equals(" ") || item.equals(null)) {
						throw new BadConfigFormatException("Bad config file format: " + item);
					}
				}

				boardRows++;
			}
			// get column count by length of first list in array
			boardCols = dataList.get(0).length;

			scanner.close();
		}

		catch (FileNotFoundException e) {
			e.printStackTrace();
		} 
		//catch (BadConfigFormatException e) {
		//	e.printStackTrace();
		//}
		// return dataList at the end of the method
		return dataList;
	}
	
	// iterate through every cell and add doorways to their associated room
	public void addDoorwaysAndSecretPassages() {
		BoardCell currCell = new BoardCell();
		BoardCell currTargetCell = new BoardCell();
		Room currRoom = new Room();
		Room adjRoom = new Room();

		// iterate through each row
		for (int row = 0; row < boardRows; row++) {
			for (int col = 0; col < boardCols; col++) {
				currCell = getCell(row, col);

				// skip cells that aren't doorways
				if (currCell.isDoorway()) {
					// get direction
					switch (currCell.getDoorDirection()) {
					case LEFT:
						currTargetCell = getCell(row, col - 1);
						break;
					case RIGHT:
						currTargetCell = getCell(row, col + 1);
						break;
					case UP:
						currTargetCell = getCell(row - 1, col);
						break;
					case DOWN:
						currTargetCell = getCell(row + 1, col);
						break;
					default:
						continue;
					}

					adjRoom = currTargetCell.getRoom();
					// skip walkways
					if (adjRoom.getName().equals("Walkway"))
						continue;
					adjRoom.addDoorCell(currCell);

					// add room center to doorway
					currCell.addAdjacency(adjRoom.getCenterCell());

				} else if (currCell.isSecretPassage()) {
					currRoom = roomMap.get(currCell.getSymbol().charAt(0));
					adjRoom = roomMap.get(currCell.getSecretPassage());

					currTargetCell = adjRoom.getCenterCell();
					currRoom.addDoorCell(currTargetCell);
				}
			}
		}
	}
	
	public void calcTargets(BoardCell startCell, int pathLength) {
		targets = new HashSet<BoardCell>();
		visited = new HashSet<BoardCell>();
		recurseTarget(startCell, pathLength);
	}
	
	public void recurseTarget(BoardCell startCell, int pathLength) {
		visited.add(startCell);

		// iterate through every cell of startCell adjacent list
		for (BoardCell cell : startCell.getAdjList()) {
			// if already visited/isRoom/isOccupied, skip this cell
			if (visited.contains(cell) || (cell.isOccupied() && !cell.isRoomCenter())) {
				continue;
			}

			// add cell to visited list
			visited.add(cell);

			// add cell to targets if out of moves
			if (pathLength == 1 || cell.isRoomCenter()) {
				targets.add(cell);
			}
			// recursive call if moves left
			else {
				this.recurseTarget(cell, pathLength - 1);
			}

			visited.remove(cell);
		}
	}

	public void calcAdjacencyList() {
		BoardCell currCell = new BoardCell();
		BoardCell temp = new BoardCell();

		// add doorway cells to room objects
		addDoorwaysAndSecretPassages();

		// iterate through every cell
		for (int y = 0; y < boardRows; y++) {
			for (int x = 0; x < boardCols; x++) {
				currCell = getCell(y, x);

				if (currCell.isRoomCenter()) {
					for (BoardCell cell : currCell.getRoom().getDoors()) {
						currCell.addAdjacency(cell);
					}
				} else {
					if (x + 1 < boardCols) {
						temp = getCell(y, x + 1); // Tests adjacent cell to the right
						if (temp != null && !temp.isOccupied() && temp.getSymbol().contains("W")) {
							currCell.addAdjacency(temp);
						}
					}

					if (x - 1 >= 0) {
						temp = getCell(y, x - 1); // Tests adjacent cell to the left
						if (temp != null && !temp.isOccupied() && temp.getSymbol().contains("W")) {
							currCell.addAdjacency(temp);
						}
					}

					if (y + 1 < boardRows) {
						temp = getCell(y + 1, x); // Tests adjacent cell above
						if (temp != null && !temp.isOccupied() && temp.getSymbol().contains("W")) {
							currCell.addAdjacency(temp);
						}
					}

					if (y - 1 >= 0) {
						temp = getCell(y - 1, x); // Tests adjacent cell below
						if (temp != null && !temp.isOccupied() && temp.getSymbol().contains("W")) {
							currCell.addAdjacency(temp);
						}
					}
				}
			}
		}
	}	

	public void deal() {
		Random r = new Random();
		int low = 0;
		int high = deck.size();
		int result = r.nextInt(high-low) + low;
		
		// Deal cards for players and remove them from the deck (as opposed to the solution the player can have duplicate types)
		for (Player p : playerList) {
			for (int cardCount = 0 ; cardCount < 3 ; cardCount++) {	// Nested for loop but easier than writing 'add card and reroll random 3 times' also makes it variable if each player gets 4 cards etc. 
				high = deck.size();
				
//				if (high - low != 0) {
					result = r.nextInt(high-low) + low;
//				}
//				else {
//					result = 0;
//				}
				
				p.updateHand(deck.get(result));
				deck.remove(result);
			}
		}
	}
	
	public int roll() {
		Random r = new Random();
		int low = 1;
		int high = 7;
		return r.nextInt(high-low) + low;
	}
		
	public void generateSolution() {
		Random r = new Random();
		int low = 0;
		int high = deck.size();
		int result = r.nextInt(high-low) + low;
		
		//	Generating Solution
		while(deck.get(result).getCardType() != CardType.ROOM) {
			result = r.nextInt(high-low) + low;
		}
		Solution.setSolRoom(deck.get(result));
		deck.remove(result);
		high = deck.size();
		
		while(deck.get(result).getCardType() != CardType.PERSON) {
			result = r.nextInt(high-low) + low;
		}
		Solution.setSolPerson(deck.get(result));
		deck.remove(result);
		high = deck.size();
		
		while(deck.get(result).getCardType() != CardType.WEAPON) {
			result = r.nextInt(high-low) + low;
		}
		Solution.setSolWeapon(deck.get(result));
		deck.remove(result);
		high = deck.size();
	}
	
	public boolean accuse(Card person, Card room, Card weapon) {
		if(person.equals(Solution.getSolPerson()) && room.equals(Solution.getSolRoom()) && weapon.equals(Solution.getSolWeapon())) {
			return true;
		}
		return false;
	}
	
	public boolean checkAccusation(String person, String room, String weapon) {
		if (Solution.getSolPersonName() != person ||
			Solution.getSolRoomName() != room ||
			Solution.getSolWeaponName() != weapon) {
			return false;
		}
		return true;
	}
	
	public Card handleSuggestion(Solution sol, Player startingPlayer) {
		int startingPlayerIndex = 0;
		int count = 0;

		for (Player currPlayer: playerList) {
			if (currPlayer.getName().equals(startingPlayer.getName())) {
				startingPlayerIndex = count;
			}
			count++;
		}

		String suggRoom = sol.getSolRoomName();
		String suggPerson = sol.getSolPersonName();
		String suggWeapon = sol.getSolWeaponName();

		Card disproveCard = new Card(null, null);

		// iterate through every player, starting at current player index
		for (int i = 0; i < playerList.size(); i++) {
			Player currPlayer = playerList.get((startingPlayerIndex + i) % playerList.size());

			disproveCard = currPlayer.disproveSuggestion(suggRoom, suggPerson, suggWeapon);

			if (disproveCard != null) {
				return disproveCard;
			}
		}

		return null;
	}
	
	public void removeCardFromDeck(Card card) {
		for (int i = 0; i < deck.size(); i++) {
			if (deck.get(i).getCardName().equals(card.getCardName())) {
				deck.remove(i);
			}
		}
	}
	
	public void addCardtoDeck(Card card) {
		deck.add(card);
	}
	
	public void paintComponent(Graphics g) {
		super.paintComponent(g);
		
		int cellWidth = panelWidth / boardCols;   // Not sure how to get panelWidth and panelHeight
		int cellHeight = panelHeight / boardRows;
		
		int cellX = 0;
		int cellY = 0;
		
		// iterate through every cell in grid[][]
		for (BoardCell[] gridCol : grid) {
			cellX = 0;
			
			for (BoardCell cell : gridCol) {
				cell.drawCell(g, cellWidth, cellHeight, cellX, cellY);
				
				cellX += cellWidth;
			}
			
			cellY += cellHeight;
		}
	}
	
	
	
	// WARNING NOW ENTERING SETTERS/GETTERS

	public void setConfigFiles(String string, String string2) {
		csv = new File(string);
		txt = new File(string2);
	}
	// getCell: returns cell given row+column. If cell doesn't exist, returns null
	public BoardCell getCell(int row, int col) {
		return grid[row][col];
	}

	public Set<BoardCell> getTargets() {
		return targets;
	}
	
	public Set<BoardCell> getVisited() {
		return visited;
	}

	public Room getRoom(char c) {
		return roomMap.get(c);
	}

	public Room getRoom(BoardCell cell) {
		return cell.getRoom();
	}

	public int getNumRows() {
		return boardRows;
	}

	public int getNumColumns() {
		return boardCols;
	}

	public Set<BoardCell> getAdjList(int row, int col) {
		return getCell(row, col).getAdjList();
	}

	public ArrayList<Player> getPlayerList() {
		return playerList;
	}
	
	public ArrayList<Card> getPlayerCardList() {
		return playerCardList;
	}
	
	public ArrayList<Card> getWeaponsList(){
		return weaponsList;
	}
	
	public ArrayList<Card> getRoomsList(){
		return roomsList;
	}

	public Solution getSolution() {
		return Solution;
	}
	
	public void setSolution(Solution solution) {
		Solution = solution;
	}
	
	public ArrayList<Card> getDeck() {
		return deck;
	}
	
	public void setPanelDimensions(int w, int h) {
		panelWidth = w;
		panelHeight = h;
	}
	
	public static int getPanelWidth() {
		return panelWidth;
	}

	public static int getPanelHeight() {
		return panelHeight;
	}
	
	
	// MAIN

	public static void main(String[] args) {
		JFrame frame = new JFrame();
		
		Board board = Board.getInstance();
		board.setConfigFiles("ClueLayout.csv", "ClueSetup.txt");
		board.initialize();
		
		frame.setContentPane(Board.getInstance()); // put the panel in the frame
		frame.setSize(Board.getPanelWidth(), Board.getPanelHeight()); // size the frame
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE); // allow it to close
		frame.setVisible(true); // make it visible
	}
	

}
