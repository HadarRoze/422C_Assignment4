package assignment4;

import java.util.*;

import assignment4.Critter.CritterShape;

public class MyCritter1 extends Critter.TestCritter {

	@Override
	public void doTimeStep() {
		walk(0);
	}

	@Override
	public boolean fight(String opponent) {
		if (getEnergy() > 10) return true;
		return false;
	}
	
	public String toString() {
		return "1";
	}
	
	public void test (List<Critter> l) {
		
	}
	
	// for Project 5
		public javafx.scene.paint.Color viewOutlineColor() { return viewColor(); }
		public javafx.scene.paint.Color viewFillColor() { return viewColor(); }
		
		public CritterShape viewShape() {
			return null;
		}
}
