package tests;

import java.io.FileNotFoundException;
import java.util.Random;

import clueGame.Board;
import clueGame.Card;
import clueGame.ComputerPlayer;

/*
 * Code Testing Class - Used to print out results of methods to figure out what goes wrong
 * 
 * @Author Carson D.
 * 
 * @Date 2/27/2024
 */

public class code_testing {

	private static Board board;
		
	public static void main(String[] args) throws FileNotFoundException {
		board = Board.getInstance();
		board.setConfigFiles("ClueLayout.csv", "ClueSetup.txt");
		board.initialize();
		
		ComputerPlayer CPU = (ComputerPlayer) board.getPlayerList().get(1);
		Card CPUCard = board.getPlayerCardList().get(1);
		
		System.out.println(CPUCard.getCardName());
		System.out.println(CPU.getCurrCell().getColumn());
				
	}
}
