package assignment4;

/*
 * Hadar Rozenberg
 * hr7358
 * 15466
 * Brandon Pham
 * bp23792
 * 15460
 */

/* Traps don't move and don't run away from a fight. They stay in place and wait for another critter to step on them. 
 * Since this critter can't walk, it spreads out instead, reproducing every turn even if their energy is very low.
 * Traps also have the element of surprise, so when they enter a fight they virtually have more energy than they can spend. 
 */

public class Trap extends Critter{
	public void doTimeStep() {
		int currentEnergy = getEnergy();
		if(currentEnergy > 2) {
			Trap babe = new Trap();
			reproduce(babe, getRandomInt(8));
		} 
	}
	public boolean fight(String oponent) {
		return true;
	}
	
	protected int getEnergy() { return super.getEnergy()*20; }
	
	public String toString() {
		return "T";
	}
}
