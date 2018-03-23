package assignment4;

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
	
	public Zombie() {
		prev_energy = getEnergy();
	}
	
	public void doTimeStep() { 
		if(prev_energy < getEnergy()) {
			Zombie babe = new Zombie();
			reproduce(babe, getRandomInt(8));
		} else {
			walk(getRandomInt(8));
		}
		prev_energy = getEnergy();
	}
	public boolean fight(String oponent) {
		if(oponent == "T") {
			return false;
		}
		return true;
	}
	
	public String toString() {
		return "Z";
	}
}