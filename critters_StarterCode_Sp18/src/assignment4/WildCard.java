package assignment4;

import assignment4.Critter.CritterShape;

/*
 * Hadar Rozenberg
 * hr7358
 * 15466
 * Brandon Pham
 * bp23792
 * 15460
 */

/*
 * The wild card critter does everything in random. Nothing is set in stone, not its actions and not its parameters. 
 */

public class WildCard extends Critter{
	
	/**
     * This method simulates what the critter does during a time step
     */
	public void doTimeStep() { 
		switch(getRandomInt(4)) {
		case 0:
			walk(getRandomInt(8));
			break;
		case 1:
			run(getRandomInt(8));
			break;
		case 2:
			WildCard babe = new WildCard();
			reproduce(babe, getRandomInt(8));
			break;
		case 3: 
			break;
		}
	}
	
	/**
     * This method determines whether the critter will fight or run
     * @param String oponenet - the conflicting critter
     * @return boolean - true if the critter will fight
     */
	public boolean fight(String oponent) {
		if(getRandomInt(2)==0) {
			return true;
		} else {
			return false;
		}
	}
	
	/**
     * This method returns the string version of the critter
     * @return String - the string version of the critter
     */
	public String toString() {
		return "W";
	}
	
	// for Project 5
	public javafx.scene.paint.Color viewOutlineColor() { 
		return javafx.scene.paint.Color.RED; 
	}
	public javafx.scene.paint.Color viewFillColor() { 
		return javafx.scene.paint.Color.BLUE; 
	}
				
	public CritterShape viewShape() {
		return CritterShape.STAR;
	}
}
