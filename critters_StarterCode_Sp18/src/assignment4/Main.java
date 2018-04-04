package assignment4;
/* CRITTERS Main.java
 * EE422C Project 4 submission by
 * Replace <...> with your actual data.
 * Hadar Rozenberg
 * hr7358
 * 15466
 * Brandon Pham
 * bp23792
 * 15460
 * Slip days used: <0>
 * Fall 2016
 */

import java.util.Scanner;
import java.awt.List;
import java.io.*;
import java.lang.reflect.Method;

import javafx.application.Application;
import javafx.collections.FXCollections;
import javafx.event.*;
import javafx.scene.*;
import javafx.scene.input.*;
import javafx.stage.Stage;
import javafx.scene.canvas.*;
import javafx.scene.control.*;
import javafx.scene.layout.*;
import javafx.scene.paint.*;

public class Main extends Application{
	// getting package
	private static String myPackage;	// package of Critter file.  Critter cannot be in default pkg. 
	static {
	        myPackage = Critter.class.getPackage().toString().split(" ")[1];
	}
	
	public static void main(String[] args) {
		launch(args);
	}
	
	@Override
	public void start(Stage primaryStage) {
		/*final Canvas canvas = new Canvas(100,100);
		GraphicsContext gc = canvas.getGraphicsContext2D();
		gc.setFill(Color.BLUE);
		gc.fillRect(0,0,100,100);*/
		GridPane gridpane = new GridPane();
		//GridPane gridpane = new GridPane();
		
		
		// map of critters
		StackPane world = new StackPane();
		Critter.displayWorld(world); // remove once finished with testing?
		gridpane.add(world,0,0);
		
		// ------Creation menu
		// choicebox content
		javafx.collections.ObservableList<String> c_types = FXCollections.observableArrayList();
		c_types.addAll("Craig","Algae", "Trap", "Wild Card", "Zombie", "Spooked");
		javafx.collections.ObservableList<Integer> c_num = FXCollections.observableArrayList();
		c_num.addAll(1,10,100);
		
		GridPane creation = new GridPane();
		creation.add(new Label("New Critter:"), 0, 0);
		creation.add(new Label("Type:"), 0, 1);
		creation.add(new Label("Number:"), 2, 1);
		ChoiceBox critter_types = new ChoiceBox(c_types);
		critter_types.setValue("Craig");
		creation.add(critter_types,1,1);
		ChoiceBox critter_num = new ChoiceBox(c_num);
		critter_num.setValue(1);
		creation.add(critter_num,3,1);
		Button create_critter = new Button("Create");
		creation.add(create_critter, 0, 2);
		gridpane.add(creation, 0,1);
		
		// time step
		GridPane time_step = new GridPane();
		time_step.add(new Label("Time Step"), 0, 0);
		time_step.add(new Label("Number of steps:"), 0, 1);
		Button time_step_1 = new Button("1");
		Button time_step_10 = new Button("10");
		Button time_step_100 = new Button("100");
		Button time_step_1000 = new Button("1000");
		time_step.add(time_step_1, 1, 1);
		time_step.add(time_step_10, 2, 1);
		time_step.add(time_step_100, 3, 1);
		time_step.add(time_step_1000, 4, 1);
		gridpane.add(time_step, 0, 2);
		
		
		Scene scene = new Scene(gridpane, 1600, 1000);
		primaryStage.setScene(scene);
		primaryStage.show();
		
		// -----Controller
		/**
		 * Controller for "create" button, creates critter
		 */
		create_critter.setOnMouseClicked(new EventHandler<MouseEvent>(){
			@Override
			public void handle(MouseEvent e) {
				String type = (String) critter_types.getValue();
				int num = (int) critter_num.getValue();
				
				try {
    					for(int i = 0; i < num; i++) {
    						Critter.makeCritter(myPackage + "." + type);
    					}
    				}
    			catch (InvalidCritterException err) {} 
				Critter.displayWorld(world);
			}
		});
		
		/**
		 * Controller for "1" button, executes one time step
		 */
		time_step_1.setOnMouseClicked(new EventHandler<MouseEvent>() {
			@Override
			public void handle(MouseEvent e) {
				
			}
		});
		
		/**
		 * Controller for "10" button, executes 10 time steps
		 */
		time_step_10.setOnMouseClicked(new EventHandler<MouseEvent>() {
			@Override
			public void handle(MouseEvent e) {
				
			}
		});
		
		/**
		 * Controller for "100" button, executes 100 time steps
		 */
		time_step_100.setOnMouseClicked(new EventHandler<MouseEvent>() {
			@Override
			public void handle(MouseEvent e) {
				
			}
		});
		
		/**
		 * Controller for "1000" button, executes 1000 time steps
		 */
		time_step_1000.setOnMouseClicked(new EventHandler<MouseEvent>() {
			@Override
			public void handle(MouseEvent e) {
				
			}
		});
	}
}



/*
 * Usage: java <pkgname>.Main <input file> test
 * input file is optional.  If input file is specified, the word 'test' is optional.
 * May not use 'test' argument without specifying input file.
 */
/*public class Main {

    static Scanner kb;	// scanner connected to keyboard input, or input file
    private static String inputFile;	// input file, used instead of keyboard input if specified
    static ByteArrayOutputStream testOutputString;	// if test specified, holds all console output
    private static String myPackage;	// package of Critter file.  Critter cannot be in default pkg.
    private static boolean DEBUG = false; // Use it or not, as you wish!
    static PrintStream old = System.out;	// if you want to restore output to console


    // Gets the package name.  The usage assumes that Critter and its subclasses are all in the same package.
    static {
        myPackage = Critter.class.getPackage().toString().split(" ")[1];
    }
    
    
    private static boolean isInteger(String s) {
		try {
			Integer.parseInt(s);
		}
		catch (NumberFormatException e) {
			return false;
		}
		catch (NullPointerException e) {
			return false;
		}
    	return true;
    }
    
    
    private static boolean isLong(String s) {
    	try {
			Long.parseLong(s);
		}
		catch (NumberFormatException e) {
			return false;
		}
		catch (NullPointerException e) {
			return false;
		}
    	return true;
    }
    
    
    private static void StatRunner(String s) throws InvalidCritterException{
    	try {
    		Class<?> c = Class.forName(myPackage + "." + s);
    		Method m = c.getMethod("runStats",java.util.List.class);
    		java.util.List list = (java.util.List) Critter.getInstances(s);
    		 m.invoke(c.newInstance(), list);
    	}
    	catch(Exception e) {
    		throw new InvalidCritterException(myPackage + "." + s);
    	}
    }

    
    public static void main(String[] args) { 
        if (args.length != 0) {
            try {
                inputFile = args[0];
                kb = new Scanner(new File(inputFile));			
            } catch (FileNotFoundException e) {
                System.out.println("USAGE: java Main OR java Main <input file> <test output>");
                e.printStackTrace();
            } catch (NullPointerException e) {
                System.out.println("USAGE: java Main OR java Main <input file>  <test output>");
            }
            if (args.length >= 2) {
                if (args[1].equals("test")) { // if the word "test" is the second argument to java
                    // Create a stream to hold the output
                    testOutputString = new ByteArrayOutputStream();
                    PrintStream ps = new PrintStream(testOutputString);
                    // Save the old System.out.
                    old = System.out;
                    // Tell Java to use the special stream; all console output will be redirected here from now
                    System.setOut(ps);
                }
            }
        } else { // if no arguments to main
            kb = new Scanner(System.in); // use keyboard and console
        }

        
        
        // System.out.println(3/2);
        while(true) {
        	
        	System.out.print("critters>");
        	String command = kb.nextLine();
        	String cArray[] = command.split(" ");	// Take entire line and split into sting arrays
        	
        	if(cArray[0].equals("quit")) { 			// do we need to account for writing stuff after quit?
        		if(cArray.length == 1) {
        			break;
        		} else {
        			System.out.println("error processing: " + command);
        		}
        	} 
        	
        	else if(cArray[0].equals("show")) {
        		if(cArray.length == 1) {
        			Critter.displayWorld();
        		} else {
        			System.out.println("error processing: " + command);
        		}
        	} 
        	
        	else if(cArray[0].equals("step")) { 	// need to account for additional things from stage 2
        		if(cArray.length == 2 && isInteger(cArray[1])) {
        			int count = Integer.parseInt(cArray[1]);
        			for(int x = 0; x < count; x++) {
        				Critter.worldTimeStep();
        			}
        		} else if(cArray.length == 1) {
        			Critter.worldTimeStep();
        		} else {
        			System.out.println("error processing: " + command);
        		}
        	} 
        	
        	else if(cArray[0].equals("make")) { 
    			
    			if(cArray.length > 1 && cArray.length <= 3) {	// make <class_name> [<count>]
        			try {
        				String className = cArray[1];
        				if(cArray.length == 3 && isInteger(cArray[2])) { 	// make <class_name> [<count>]
        					int amount = Integer.parseInt(cArray[2]);
        					for(int i = 0; i < amount; i++) {
        						Critter.makeCritter(myPackage + "." + className);
        					}
        				} else if(cArray.length == 2){						// make <class_name>
        					Critter.makeCritter(myPackage + "." + className);
        				} else {
        					System.out.println("error processing: " + command);
        				}
        			}
        			catch (InvalidCritterException e) {System.out.println("error processing: " + command);}
    			} else {
    				System.out.println("error processing: " + command);
    			}
        	} 
        	
        	else if(cArray[0].equals("seed")) {
        		if(cArray.length == 2 && isLong(cArray[1])) {
        			Long seed = Long.parseLong(cArray[1]);
        			Critter.setSeed(seed);
        		} else {
        			System.out.println("error processing: " + command);
        		}
        	} 
        	
        	else if(cArray[0].equals("stats")) {
        		if(cArray.length == 2) {
        			try {
        				String className = cArray[1];
        				StatRunner(className);
        			}
        			catch (InvalidCritterException e) {
        				System.out.println("error processing: " + command);
        			}
        		} else {
        			System.out.println("error processing: " + command);
        		}
        	}
        	
        	else {
        		System.out.println("invalid command: " + command);
        	}
        }
        
        
        
        System.out.flush();

    }
}*/
