package assignment4;
/* CRITTERS Critter.java
 * EE422C Project 4 submission by
 * Replace <...> with your actual data.
 * <Student1 Name>
 * <Student1 EID>
 * <Student1 5-digit Unique No.>
 * <Student2 Name>
 * <Student2 EID>
 * <Student2 5-digit Unique No.>
 * Slip days used: <0>
 * Fall 2016
 */


import java.util.List;

/* see the PDF for descriptions of the methods and fields in this class
 * you may add fields, methods or inner classes to Critter ONLY if you make your additions private
 * no new public, protected or default-package code or data can be added to Critter
 */


public abstract class Critter {
	private static String myPackage;
	private	static List<Critter> population = new java.util.ArrayList<Critter>();
	private static List<Critter> babies = new java.util.ArrayList<Critter>();
	
	// additional fields (Hadar)
	private int prev_x;
	private int prev_y;
	private boolean movedThisTurn;
	// Gets the package name.  This assumes that Critter and its subclasses are all in the same package.
	static {
		myPackage = Critter.class.getPackage().toString().split(" ")[1];
	}
	
	private static java.util.Random rand = new java.util.Random();
	public static int getRandomInt(int max) {
		return rand.nextInt(max);
	}
	
	public static void setSeed(long new_seed) {
		rand = new java.util.Random(new_seed);
	}
	
	
	/* a one-character long string that visually depicts your critter in the ASCII interface */
	public String toString() { return ""; }
	
	private int energy = 0;
	protected int getEnergy() { return energy; }
	
	private int x_coord;
	private int y_coord;
	
	//Step 8
	private final void movement(int direction, int distance, int cost) {
		// Assumption: If you do not have the energy to walk or you try to walk into a wall, you do not walk
		// Assumption: If you cannot complete the move, you do not move
		if(energy >= cost) {
			int tempx = x_coord;
			int tempy = y_coord; 
			switch(direction) {
					
				case 0: if(x_coord < (Params.world_width-(distance+1))) {													//Right
							x_coord += distance;
						}break;
								
				case 1: if(x_coord < (Params.world_width-(distance+1)) && y_coord >= distance) {							//Right, Up
							x_coord += distance;
							y_coord -= distance;
						}break;
								
				case 2: if(y_coord >= distance) {																			//Up
							y_coord -= distance;
						}break;
								
				case 3: if(x_coord >= distance && y_coord >= distance) {													//Left, Up
							x_coord -= distance;
							y_coord -= distance;
						}break;
								
				case 4: if(x_coord >= distance) {																			//Left
							x_coord -= distance;
						}break;
								
				case 5: if(x_coord >= distance && y_coord < (Params.world_height-(distance+1))) {							//Left, Down
							x_coord -= distance;
							y_coord += distance;
						}break;
						
				case 6: if(y_coord < (Params.world_height-(distance+1))) {													//Down
							y_coord += distance;
						}break;
						
				case 7: if(x_coord < (Params.world_width-(distance+1)) && y_coord < (Params.world_height-(distance+1))) {	//Right, Down
							x_coord += distance;
							y_coord += distance;
						}break;
								
				default: break;																								//If invalid, do nothing
			}
					
			if(tempx != x_coord || tempy != y_coord) {
				energy -= cost;
			}
		}		
	}
	protected final void walk(int direction) {
		movement(direction,1,Params.walk_energy_cost);
	}
	
	protected final void run(int direction) {
		movement(direction,2,Params.run_energy_cost);
	}
	
	protected final void reproduce(Critter offspring, int direction) {
		// check parent energy
		if(this.energy<Params.min_reproduce_energy) {
			return;
		}
		// assign energy
		int energy_remainder = this.energy%2;
		offspring.energy = this.energy/2;
		this.energy = offspring.energy+energy_remainder;
		// assign position
		offspring.movement(direction, 1, 0);
		// add baby to babies
		babies.add(offspring);
	}

	public abstract void doTimeStep();
	public abstract boolean fight(String oponent);
	
	/**
	 * create and initialize a Critter subclass.
	 * critter_class_name must be the unqualified name of a concrete subclass of Critter, if not,
	 * an InvalidCritterException must be thrown.
	 * (Java weirdness: Exception throwing does not work properly if the parameter has lower-case instead of
	 * upper. For example, if craig is supplied instead of Craig, an error is thrown instead of
	 * an Exception.)
	 * @param critter_class_name
	 * @throws InvalidCritterException
	 */
	public static void makeCritter(String critter_class_name) throws InvalidCritterException {
		try {
			Class c = Class.forName(critter_class_name);
			
			Critter crit = (Critter) c.newInstance();
			crit.energy = Params.start_energy;
			crit.x_coord = getRandomInt(Params.world_width);
			crit.y_coord = getRandomInt(Params.world_height);
			
			population.add(crit);
		}
		catch (Exception e) {
			throw new InvalidCritterException(critter_class_name);
		}
	}
	
	/**
	 * Gets a list of critters of a specific type.
	 * @param critter_class_name What kind of Critter is to be listed.  Unqualified class name.
	 * @return List of Critters.
	 * @throws InvalidCritterException
	 */
	public static List<Critter> getInstances(String critter_class_name) throws InvalidCritterException {
		List<Critter> result = new java.util.ArrayList<Critter>();
		try {
			Class c = Class.forName(myPackage + "." + critter_class_name);
			
			for(Critter crit: population) {
				String critClassName = crit.getClass().getName();
				String targetClassName = c.getName();
				if(critClassName.equals(targetClassName)) {	// Compares the name of the classes
					result.add(crit);
				}
			}
			return result;
		}
		catch(Exception e){
			throw new InvalidCritterException(myPackage + "." + critter_class_name);
		}
	}
	
	/**
	 * Prints out how many Critters of each type there are on the board.
	 * @param critters List of Critters.
	 */
	public static void runStats(List<Critter> critters) {
		System.out.print("" + critters.size() + " critters as follows -- ");
		java.util.Map<String, Integer> critter_count = new java.util.HashMap<String, Integer>();
		for (Critter crit : critters) {
			String crit_string = crit.toString();
			Integer old_count = critter_count.get(crit_string);
			if (old_count == null) {
				critter_count.put(crit_string,  1);
			} else {
				critter_count.put(crit_string, old_count.intValue() + 1);
			}
		}
		String prefix = "";
		for (String s : critter_count.keySet()) {
			System.out.print(prefix + s + ":" + critter_count.get(s));
			prefix = ", ";
		}
		System.out.println();		
	}
	
	/* the TestCritter class allows some critters to "cheat". If you want to 
	 * create tests of your Critter model, you can create subclasses of this class
	 * and then use the setter functions contained here. 
	 * 
	 * NOTE: you must make sure that the setter functions work with your implementation
	 * of Critter. That means, if you're recording the positions of your critters
	 * using some sort of external grid or some other data structure in addition
	 * to the x_coord and y_coord functions, then you MUST update these setter functions
	 * so that they correctly update your grid/data structure.
	 */
	static abstract class TestCritter extends Critter {
		protected void setEnergy(int new_energy_value) {
			super.energy = new_energy_value;
		}
		
		protected void setX_coord(int new_x_coord) {
			super.x_coord = new_x_coord;
		}
		
		protected void setY_coord(int new_y_coord) {
			super.y_coord = new_y_coord;
		}
		
		protected int getX_coord() {
			return super.x_coord;
		}
		
		protected int getY_coord() {
			return super.y_coord;
		}
		

		/*
		 * This method getPopulation has to be modified by you if you are not using the population
		 * ArrayList that has been provided in the starter code.  In any case, it has to be
		 * implemented for grading tests to work.
		 */
		protected static List<Critter> getPopulation() {
			return population;
		}
		
		/*
		 * This method getBabies has to be modified by you if you are not using the babies
		 * ArrayList that has been provided in the starter code.  In any case, it has to be
		 * implemented for grading tests to work.  Babies should be added to the general population 
		 * at either the beginning OR the end of every timestep.
		 */
		protected static List<Critter> getBabies() {
			return babies;
		}
	}

	/**
	 * Clear the world of all critters, dead and alive
	 */
	public static void clearWorld() {
		population.clear();
		babies.clear();
	}
	
	public static void worldTimeStep() {
		// execute timeStep for all living critters
		for(Critter crit: population) { 
			crit.recordPrevious();
			crit.doTimeStep();
			if((crit.prev_x==crit.x_coord)&&(crit.prev_y==crit.y_coord)) { // check for movement
				crit.movedThisTurn = false;
			} else {
				crit.movedThisTurn = true;
			}
		}
		processEncounters(); // process all of the encounters
		// remove dead critters
		List<Critter> deads = new java.util.ArrayList<Critter>(); // might go out of bounds idk yet
		for(Critter crit: population) {
			if(crit.getEnergy()<=0) {
				deads.add(crit);
			}
		}
		for(Critter dead: deads) { // could maybe do the thing where it erases it from dead as well because it came from pop but honestly i don't think java does that
			population.remove(dead);
		}
		// add babies to population
		for(Critter babe: babies) { // might result with an over bounds error
			population.add(babe);
		}
		babies.clear(); // not sure about the ins and outs of this method, it could result with an issue in the end
		// refresh algae count
		int init_size = population.size();
		for(int count = 0; count < Params.refresh_algae_count; count++) {
			population.add(new Algae());
			population.get(init_size+count).energy = Params.start_energy;
			population.get(init_size+count).x_coord = rand.nextInt(Params.world_width);
			population.get(init_size+count).y_coord = rand.nextInt(Params.world_height);
		}
	}
	
	public static void displayWorld() {
		// Complete this method
		// create parameters for the grid
		int height = Params.world_height + 2;
		int width = Params.world_width + 2;
		boolean colEnd;
		boolean rowEnd; // these will hold values of whether the current point is at the end of the world or not
		String[][] viewedWorld = new String[height][width];
		// go thru population and mark them on the map
		for(Critter crit: population) {
			viewedWorld[crit.y_coord+1][crit.x_coord+1] = crit.toString();
		}
		// go thru entire world, replace content if needed, and print
		for(int y = 0; y < height; y++) {
			for(int x = 0; x < width; x++) {
				colEnd = ((x == 0)||(x == width-1));
				rowEnd = ((y == 0)||(y == height-1)); // determine whether the point is on the borders of the world
				
				if(colEnd&&rowEnd) {
					viewedWorld[y][x] = "+";
				} else if(colEnd) {
					viewedWorld[y][x] = "|";
				} else if(rowEnd) {
					viewedWorld[y][x] = "-";
				} else if(viewedWorld[y][x] == null) { // i think this is the default but it might not be
					viewedWorld[y][x] = " ";
				}
				System.out.print(viewedWorld[y][x]);
			}
			System.out.print("\n"); // did they mean print or just out? need to check this
		}
	}
	
	// method to process encounters
	private static void processEncounters() {
		int length = population.size();
		if(length == 0) {
			return;
		}
		for(int first = 0; first < length; first++) { // loop thru population
			for(int second = first+1; second < length; second++) {
				if(population.get(first).getEnergy()<=0) { // if this critter is dead, there's no use comparing him to the others
					break;
				}
				if(shouldEncounter(population.get(first), population.get(second))) { // the 2 critters encountered one another
					// records previous locations to prevent more movement than is allowed
					population.get(first).recordPrevious();
					population.get(second).recordPrevious();
					// invoke fight function
					boolean firstFight = population.get(first).fight(population.get(second).toString());
					boolean secondFight = population.get(second).fight(population.get(first).toString());
					// fix location if needed
					population.get(first).validateMovement();
					population.get(second).validateMovement();
					if((population.get(first).getEnergy()<=0)&&(shouldEncounter(population.get(first), population.get(second)))) { // if both are alive and in the same spot
						int first_roll = 0;
						int second_roll = 0;
						if(firstFight) {
							first_roll = Critter.getRandomInt(population.get(first).getEnergy());
						}
						if(secondFight) {
							second_roll = Critter.getRandomInt(population.get(second).getEnergy());
						}
						if(first_roll>=second_roll) { // first critter wins
							population.get(first).energy += (population.get(second).energy/2);
							population.get(second).energy = 0;
						} else {
							population.get(second).energy += (population.get(first).energy/2);
							population.get(first).energy = 0;
						}
					}
				}
			}
		}
	}
	
	// method to determine if an encounter should happen or not
	// mostly needed to reduce "wordiness" in processEncounters 
	private static boolean shouldEncounter(Critter c1, Critter c2) {
		if((c1.x_coord==c2.x_coord)&&(c1.y_coord==c2.y_coord)&&(c2.getEnergy()<=0)) { // same location and second critter isn't dead (expected that critter 1 is alive)
			return true;
		}
		return false;
	}
	
	// records current location to previous location. This is mostly to reduce wordiness.
	private void recordPrevious() {
		prev_x = x_coord;
		prev_y = y_coord;
	}
	
	// checks if a movement is the first for the turn and reverts it if it isn't. If there is a movement and it's the first one, indicate that critter moved in this step
	private void validateMovement() {
		if(movedThisTurn && ((prev_x!=x_coord) || (prev_y!=y_coord))) { // return position to previous position
			x_coord = prev_x;
			y_coord = prev_y;
		} else if((prev_x!=x_coord) || (prev_y!=y_coord)) {
			for(Critter other: population) {
				if(this == other) {
					continue;
				} else if((x_coord==other.x_coord)&&(y_coord==other.y_coord)) {
					x_coord = prev_x;
					y_coord = prev_y;
					return;
				}
			}
			movedThisTurn = true;
		}
	}
}
