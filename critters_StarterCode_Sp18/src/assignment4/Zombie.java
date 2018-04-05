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
 * Zombies mindlessly attack anything that moves (so not Traps)
 * They reproduce in the step after having killed another critter 
 */

public class Zombie extends Critter{
	private int prev_energy;
	
	/**
     * Zombie constructor
     */
	public Zombie() {
		prev_energy = getEnergy();
	}
	
	/**
     * This method simulates what the critter does during a time step
     */
	public void doTimeStep() { 
		if((prev_energy < getEnergy())&&(prev_energy!=0)) {
			Zombie babe = new Zombie();
			reproduce(babe, getRandomInt(8));
		} else {
			walk(getRandomInt(8));
		}
		prev_energy = getEnergy();
	}
	
	/**
     * This method determines whether the critter will fight or run
     * @param String oponenet - the conflicting critter
     * @return boolean - true if the critter will fight
     */
	public boolean fight(String oponent) {
		if(oponent == "T") {
			return false;
		}
		return true;
	}
	
	/**
     * This method returns the string version of the critter
     * @return String - the string version of the critter
     */
	public String toString() {
		return "Z";
	}
	
	// for Project 5
	public javafx.scene.paint.Color viewOutlineColor() { 
		return javafx.scene.paint.Color.YELLOWGREEN;
	}
	public javafx.scene.paint.Color viewFillColor() { 
		return javafx.scene.paint.Color.YELLOWGREEN; 
	}
				
	public CritterShape viewShape() {
		return CritterShape.CIRCLE;
	}
}