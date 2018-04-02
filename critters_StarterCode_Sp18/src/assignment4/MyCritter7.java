package assignment4;

import assignment4.Critter.CritterShape;
import assignment4.Critter.TestCritter;

public class MyCritter7 extends TestCritter {
	
	@Override
	public void doTimeStep() {
	}

	@Override
	public boolean fight(String opponent) {

		return true;
	}

	@Override
	public String toString () {
		return "7";
	}
	
	// for Project 5
		public javafx.scene.paint.Color viewOutlineColor() { return viewColor(); }
		public javafx.scene.paint.Color viewFillColor() { return viewColor(); }
		
		public CritterShape viewShape() {
			return null;
		}
}
