package assignment4;

/*
 * The wild card critter does everything in random. Nothing is set in stone, not its actions and not its parameters. 
 */

public class WildCard extends Critter{
	public void doTimeStep() { 
		switch(getRandomInt(4)) {
		case 0:
			walk(getRandomInt(7));
			break;
		case 1:
			run(getRandomInt(7));
			break;
		case 2:
			WildCard babe = new WildCard();
			reproduce(babe, getRandomInt(7));
			break;
		case 3: 
			break;
		}
	}
	public boolean fight(String oponent) {
		if(getRandomInt(2)==0) {
			return true;
		} else {
			return false;
		}
	}
	
	public String toString() {
		return "W";
	}
}
