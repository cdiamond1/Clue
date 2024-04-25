package clueGame;

/* Card class - contains info about game cards
 * 
 * @Author: Carson D.
 * @Author: Charlie D.
 * 
 * @Date: 4/24/2024
 */

public class Card {
	private String cardName;
	private CardType type;
	
	// constructor
	public Card(String cardName, CardType type) {
		super();
		this.cardName = cardName;
		this.type = type;
	}
	
	// equals to compare two cards via name
	public boolean equals(Card Target) {
		if(this.cardName.equals(Target.getCardName())) {
			return true;
		}
		return false;
	}
	
	// getters
	public String getCardName() {
		return cardName;
	}
	
	public CardType getCardType() {
		return type;
	}
}