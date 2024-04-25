package clueGame;

/* BadConfigFormatException - Creates custom exception for bad config files
 * 
 * @Author Carson D.
 * @Author Charlie Dupras
 * 
 * @Date 3/1/2024
 * 
 */

public class BadConfigFormatException extends Exception {

	/*
	 * Copied code from C11A-2 Advanced Exceptions for referance
	 * 
	 */
	
	public BadConfigFormatException() { 
		super("Error: bad config file format"); 
	}
	
	public BadConfigFormatException(String message) {
		 super(message); 
	}

}
