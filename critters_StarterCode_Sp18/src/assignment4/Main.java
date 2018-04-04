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
import javafx.scene.text.Text;
import javafx.util.Duration;
import javafx.animation.Animation;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;


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
		GridPane gridpane = new GridPane();
		
		// map of critters
		GridPane world = new GridPane();
		Critter.displayWorld(world); // remove once finished with testing?
		gridpane.add(world,0,0);
		
		// ------Creation menu
		javafx.collections.ObservableList<Integer> c_num = FXCollections.observableArrayList();
		c_num.addAll(1,10,100);
		
		GridPane creation = new GridPane();
		creation.add(new Label("New Critter"), 0, 0);
		creation.add(new Label("   Seed: "), 5, 1);
		creation.add(new Label("Type:"), 0, 1);
		creation.add(new Label("   Number: "), 2, 1);
		TextField critter_types = new TextField();
		critter_types.setPromptText("Enter Critter Type");
		critter_types.setPrefWidth(150);
		creation.add(critter_types, 1, 1);
		TextField num_crit = new TextField();
		num_crit.setPromptText("Enter Number of Critters");
		num_crit.setPrefWidth(150);
		creation.add(num_crit, 3, 1);
		TextField seed = new TextField();
		seed.setPromptText("Enter Seed");
		seed.setPrefWidth(150);
		creation.add(seed, 6, 1);
		Button create_critter = new Button("Create");
		Button setSeed = new Button("Set");
		creation.add(create_critter, 4, 1);
		creation.add(setSeed, 7, 1);
		
		
		gridpane.add(creation, 0,1);
		
		// time step
		GridPane time_step = new GridPane();
		time_step.add(new Label(""), 0, 0);
		time_step.add(new Label("Time Step"), 0, 1);
		time_step.add(new Label("Number of steps:"), 0, 2);
		TextField num_steps = new TextField();
		num_steps.setPromptText("Enter Number of Steps");
		num_steps.setPrefWidth(150);
		time_step.add(num_steps, 1, 2);
		Button step = new Button("Step");
		time_step.add(step, 2, 2);
		gridpane.add(time_step, 0, 2);
		
		
	 	GridPane step_butts = new GridPane();
	 	step_butts.add(new Label("Quick steps:        "), 0, 0);
		Button time_step_1 = new Button("1");															
		Button time_step_10 = new Button("10");
		Button time_step_100 = new Button("100");
		Button time_step_1000 = new Button("1000");
		step_butts.add(time_step_1, 1, 0);
		step_butts.add(time_step_10, 2, 0);
		step_butts.add(time_step_100, 3, 0);
		step_butts.add(time_step_1000, 4, 0);
		gridpane.add(step_butts, 0, 3);
		
		GridPane animation = new GridPane();
		javafx.collections.ObservableList<Integer> ts_sizes = FXCollections.observableArrayList();		// Use text field instead of choice box
		ts_sizes.addAll(1,2,5,10,20,50,100);
		ChoiceBox frame_choice = new ChoiceBox(ts_sizes);
		frame_choice.setValue(1);
		animation.add(new Label("Animate"), 0, 0);
		animation.add(new Label("Choose animation speed: "), 0, 1);
		animation.add(frame_choice, 1, 1);
		Button startAnimation = new Button("Start Animation");
		Button stopAnimation = new Button("Stop Animation");
		animation.add(startAnimation, 2,1);
		animation.add(stopAnimation, 3, 1);
		gridpane.add(animation, 0, 4);
		
		
		GridPane RunStats = new GridPane();
		RunStats.add(new Label(""), 0, 0);
		RunStats.add(new Label("Run Stats"), 0, 1);
		RunStats.add(new Label("Critter: "), 0, 2);
		TextField critter_types2 = new TextField();
		critter_types2.setPromptText("Enter Critter Type");
		critter_types2.setPrefWidth(150);
		Button run = new Button("Manual Run Stats");
		RunStats.add(critter_types2, 1, 2);
		RunStats.add(run, 2,2);
		gridpane.add(RunStats, 0, 5);
		
		GridPane statsDisplay = new GridPane();
		Text stats = new Text();
		stats.setText("Stats will be displayed here");
		stats.setWrappingWidth(1560);
		statsDisplay.add(stats, 0, 0);
		Button quitter = new Button("Quit");
		statsDisplay.add(quitter, 1, 0);
		gridpane.add(statsDisplay, 0, 6);
		
		Text error = new Text();
		gridpane.add(error, 0, 7);
		error.setText("");
		int scale = Critter.getScale();

		Scene scene = new Scene(gridpane, Params.world_width*scale, Params.world_height*scale+300);
		primaryStage.setScene(scene);
		primaryStage.show();
		
		Timeline t1 = new Timeline();
		t1.setCycleCount(Animation.INDEFINITE);
		// -----Controller
		/**
		 * Controller for "create" button, creates critter
		 */
		create_critter.setOnMouseClicked(new EventHandler<MouseEvent>(){
			@Override
			public void handle(MouseEvent e) {
				error.setText("");
				String sNum = (String) num_crit.getText();
				if(isInteger(sNum)) {
					String type = critter_types.getText();
					int num = Integer.parseInt(sNum);
					try {
						for(int i = 0; i < num; i++) {
							Critter.makeCritter(myPackage + "." + type);
						}
					}
					catch (InvalidCritterException err) {
						error.setText("Error: Invalid value(s) for creating a critter");
					} 
					Critter.displayWorld(world);
				} else {
					error.setText("Error: Invalid value(s) for creating a critter");
				}
			}
		});
		
		/**
		 * Controller for "Set" button, sets Seed
		 */
		setSeed.setOnMouseClicked(new EventHandler<MouseEvent>(){
			@Override
			public void handle(MouseEvent e) {
				error.setText("");
				System.out.println("Seed Set: " + seed.getText());
				String sd = seed.getText();
				if(isInteger(sd)) {
					Critter.setSeed(Integer.parseInt(sd));
				} else {
					error.setText("Error: Invalid value for setting a seed");
				}
			}
		});
		
		/**
		 * Controller for "step" button, executes one time step
		 */
		step.setOnMouseClicked(new EventHandler<MouseEvent>() {
			@Override
			public void handle(MouseEvent e) {
				error.setText("");
				String steps = num_steps.getText();
				if(isInteger(steps)) {
					System.out.println("Stepping " + steps + " times");
					int stps = Integer.parseInt(steps);
					for(int x = 0; x < stps; x++) {
						Critter.worldTimeStep();
					}
					Critter.displayWorld(world);
				} else {
					error.setText("Error: Invalid value for time step");
				}
			}
		});
		
		/**
		 * Controller for "1" button, executes one time step
		 */
		time_step_1.setOnMouseClicked(new EventHandler<MouseEvent>() {
			@Override
			public void handle(MouseEvent e) {
				error.setText("");
				System.out.println("Stepping 1 time");
				Critter.worldTimeStep();
				Critter.displayWorld(world);
			}
		});
		
		/**
		 * Controller for "10" button, executes 10 time steps
		 */
		time_step_10.setOnMouseClicked(new EventHandler<MouseEvent>() {
			@Override
			public void handle(MouseEvent e) {
				error.setText("");
				System.out.println("Stepping 10 time");
				for(int x = 0; x < 10; x++) {
					Critter.worldTimeStep();
				}
				Critter.displayWorld(world);
			}
		});
		
		/**
		 * Controller for "100" button, executes 100 time steps
		 */
		time_step_100.setOnMouseClicked(new EventHandler<MouseEvent>() {
			@Override
			public void handle(MouseEvent e) {
				error.setText("");
				System.out.println("Stepping 100 time");
				for(int x = 0; x < 100; x++) {
					Critter.worldTimeStep();
				}
				Critter.displayWorld(world);
			}
		});
		
		/**
		 * Controller for "1000" button, executes 1000 time steps
		 */
		time_step_1000.setOnMouseClicked(new EventHandler<MouseEvent>() {
			@Override
			public void handle(MouseEvent e) {
				error.setText("");
				System.out.println("Stepping 1000 time");
				for(int x = 0; x < 1000; x++) {
					Critter.worldTimeStep();
				}
				Critter.displayWorld(world);
			}
		});
		
		/**
		 * Controller for "Manual Run Stats" button, displays stats
		 */
		run.setOnMouseClicked(new EventHandler<MouseEvent>() {
			@Override
			public void handle(MouseEvent e) {
				error.setText("");
				String name = critter_types2.getText();
				stats.setText("Running Stats for " + name);
			}
		});
		
		/**
		 * Controller for "Quit" button, exits program
		 */
		quitter.setOnMouseClicked(new EventHandler<MouseEvent>() {
			@Override
			public void handle(MouseEvent e) {
				System.exit(0);
			}
		});
		
		/**
		 * 
		 */
		startAnimation.setOnMouseClicked(new EventHandler<MouseEvent>() {
			@Override
			public void handle(MouseEvent e) {
				error.setText("");
				// disable all controls other than 'Stop Animation'
				creation.setDisable(true);
				time_step.setDisable(true);
				step_butts.setDisable(true);
				RunStats.setDisable(true);
				statsDisplay.setDisable(true);
				frame_choice.setDisable(true);
				startAnimation.setDisable(true);
				
				int speed = (int) frame_choice.getValue();
				KeyFrame animate = new KeyFrame(Duration.seconds(.5/speed), new EventHandler<ActionEvent>() {
					public void handle(ActionEvent event) {
						Critter.worldTimeStep();
						Critter.displayWorld(world);
					}
				});
				
				t1.getKeyFrames().add(animate);
				t1.play();
			}
		});
		
		stopAnimation.setOnMouseClicked(new EventHandler<MouseEvent>() {
			@Override
			public void handle(MouseEvent e) {
				creation.setDisable(false);
				time_step.setDisable(false);
				step_butts.setDisable(false);
				RunStats.setDisable(false);
				statsDisplay.setDisable(false);
				frame_choice.setDisable(false);
				startAnimation.setDisable(false);
				
				t1.stop();
				t1.getKeyFrames().clear();
			}
		});
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
