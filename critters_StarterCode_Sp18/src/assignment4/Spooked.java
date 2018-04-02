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
 * This is a very scared critter. It runs away from everything other than Algea, and only has the courage to move every 4 motionless turns.
 * After finding an Algae it'll reproduce.
 */

public class Spooked extends Critter{
	private boolean found_food = false;
	private int eventless_turns = 0;
	
	/**
     * This method simulates what the critter does during a time step
     */
	public void doTimeStep() {
		if(found_food) {
			Spooked babe = new Spooked();
			reproduce(babe, getRandomInt(8));
			found_food = false;
		}
		if(eventless_turns > 4) {
			walk(getRandomInt(8));
			eventless_turns = 0;
		} else {
			eventless_turns ++;
		}
	}
	
	/**
     * This method determines whether the critter will fight or run
     * @param String oponenet - the conflicting critter
     * @return boolean - true if the critter will fight
     */
	public boolean fight(String oponent) {
		if(oponent == "A") {
			found_food = true;
			return true;
		}
		eventless_turns = 0;
		run(getRandomInt(8));
		return false;
	}
	
	/**
     * This method returns the string version of the critter
     * @return String - the string version of the critter
     */
	public String toString() {
		return "S";
	}
	
	// for Project 5
	public javafx.scene.paint.Color viewOutlineColor() { 
		return javafx.scene.paint.Color.YELLOW;
	}
	public javafx.scene.paint.Color viewFillColor() { 
		return javafx.scene.paint.Color.YELLOW; 
	}
			
	public CritterShape viewShape() {
		return CritterShape.TRIANGLE;
	}
}