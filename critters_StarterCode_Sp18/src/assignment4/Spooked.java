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
 * This is a very scared critter. It runs away from everything other than Algea, and only has the courage to move every 4 motionless turns.
 * After finding an Algae it'll reproduce.
 */

public class Spooked extends Critter{
	private boolean found_food = false;
	private int eventless_turns = 0;
	
	public void doTimeStep() {
		if(found_food) {
			Spooked babe = new Spooked();
			reproduce(babe, getRandomInt(7));
			found_food = false;
		}
		if(eventless_turns > 4) {
			walk(getRandomInt(7));
			eventless_turns = 0;
		} else {
			eventless_turns ++;
		}
	}
	public boolean fight(String oponent) {
		if(oponent == "A") {
			found_food = true;
			return true;
		}
		eventless_turns = 0;
		run(getRandomInt(7));
		return false;
	}
	
	public String toString() {
		return "S";
	}
}