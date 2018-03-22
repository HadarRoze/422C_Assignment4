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
import java.io.*;


/*
 * Usage: java <pkgname>.Main <input file> test
 * input file is optional.  If input file is specified, the word 'test' is optional.
 * May not use 'test' argument without specifying input file.
 */
public class Main {

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

    /**
     * Main method.
     * @param args args can be empty.  If not empty, provide two parameters -- the first is a file name, 
     * and the second is test (for test output, where all output to be directed to a String), or nothing.
     */
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

        /* Do not alter the code above for your submission. */
        /* Write your code below. */
        
        // System.out.println(3/2);
        while(true) {
        	System.out.print("critters> ");
        	String command = kb.next();
        	if(command.equals("quit")) { // do we need to account for writing stuff after quit?
        		break;
        	} else if(command.equals("show")) {
        		Critter.displayWorld();
        	} else if(command.equals("step")) { // need to account for additional things from stage 2
        		if(kb.hasNextInt()) {
        			int count = kb.nextInt();
        			for(int x = 0; x < count; x++) {
        				Critter.worldTimeStep();
        			}
        		} else {
        			Critter.worldTimeStep();
        		}
        	} else if(command.equals("make")) { 
        		try{
        			/*for(int x = 0; x<100; x++) {					// this is only for stages 1 and 2 
        				Critter.makeCritter(myPackage+".Craig");
        			}
        			for(int x = 0; x<25; x++) {
        				Critter.makeCritter(myPackage+".Algae");
        			}*/
        			if(kb.hasNext()) {
            			try {
            				String className = kb.next();
            				if(kb.hasNextInt()) {
            					int amount = kb.nextInt();
            					for(int i = 0; i < amount; i++) {
            						Critter.makeCritter(myPackage + "." + className);
            					}
            				} else {
            					Critter.makeCritter(myPackage + "." + className);
            				}
            			}
            			catch (InvalidCritterException e) {System.out.println("oops2");}
            		} else {
            			System.out.println("Please specify critter name");
            		}
        		} 
        		catch (InvalidCritterException e) {System.out.print("oops");}
        	} else if(command.equals("seed")) {
        		if(kb.hasNextLong()) {
        			Long seed = kb.nextLong();
        			Critter.setSeed(seed);
        		} else {
        			System.out.println("Please specify a number when invoking seed.");
        		}
        	} else if(command.equals("stats")) {
        		if(kb.hasNext()) {
        			try {
        				String className = kb.next();
        				Craig.runStats(Critter.getInstances(className));
        			}
        			catch (InvalidCritterException e) {System.out.println("oops2");}
        		} else {
        			System.out.println("Please specify critter name");
        		}
        	}
        }
        
        
        /* Write your code above */
        System.out.flush();

    }
}
