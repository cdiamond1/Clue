package clueGame;

/*
 * Solution Class - stores data for Solution objects
 *  
 * @author Carson Diamond
 * @author Charlie Dupras
 * @date 3/25/2024
 */

public class Solution {
	private Card room;
	private Card person;
	private Card weapon;
	
	// default constructor
	public Solution() {
		this.room = null;
		this.person = null;
		this.weapon = null;
	}
	
	// constructor with inputs
	public Solution(Card room, Card person, Card weapon) {
		this.room = room;
		this.person = person;
		this.weapon = weapon;
	}
	
	public Card getSolRoom() {
		return room;
	}
	
	public void setSolRoom(Card room) {
		this.room = room;
	}
	
	public String getSolRoomName() {
		return room.getCardName();
	}
	
	public Card getSolPerson() {
		return person;
	}
	
	public void setSolPerson(Card person) {
		this.person = person;
	}
	
	public String getSolPersonName() {
		return person.getCardName();
	}

	public Card getSolWeapon() {
		return weapon;
	}
	
	public void setSolWeapon(Card weapon) {
		this.weapon = weapon;
	}
	
	public String getSolWeaponName() {
		return weapon.getCardName();
	}
}
