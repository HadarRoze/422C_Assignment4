package assignment4;

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
			reproduce(babe, getRandomInt(7));
		} else {
			walk(getRandomInt(7));
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