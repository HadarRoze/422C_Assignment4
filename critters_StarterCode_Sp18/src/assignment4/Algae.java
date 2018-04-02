package assignment4;

import assignment4.Critter.CritterShape;
/*
 * Do not change or submit this file.
 */
import assignment4.Critter.TestCritter;

public class Algae extends TestCritter {

	public String toString() { return "@"; }
	
	public boolean fight(String not_used) { return false; }
	
	public void doTimeStep() {
		setEnergy(getEnergy() + Params.photosynthesis_energy_amount);
	}
	
	// for Project 5
		public javafx.scene.paint.Color viewOutlineColor() { 
			return javafx.scene.paint.Color.GREEN; 
		}
		public javafx.scene.paint.Color viewFillColor() { 
			return javafx.scene.paint.Color.GREEN; 
		}
		
		public CritterShape viewShape() {
			return CritterShape.SQUARE;
		}
}
