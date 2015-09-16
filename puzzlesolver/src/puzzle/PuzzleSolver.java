/* Written by Jordan Williams */

package puzzle;

import java.util.Scanner;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.LinkedList;
import java.util.Iterator;
import java.util.Enumeration;
import java.util.concurrent.*;
import java.io.File;
import javax.swing.tree.TreeNode;
import org.apache.commons.cli.Options;
import org.apache.commons.cli.Option;
import org.apache.commons.cli.OptionBuilder;
import org.apache.commons.cli.HelpFormatter;
import org.apache.commons.cli.CommandLine;
import org.apache.commons.cli.CommandLineParser;
import org.apache.commons.cli.GnuParser;
import org.apache.commons.cli.ParseException;
import org.apache.commons.lang3.StringUtils;

public class PuzzleSolver {
	public PuzzleSolver(Puzzle p) {
		initialPuzzle = p;
	}
		
	public static void main(String[] args) {	
		Puzzle.updateSolution();	
		int rParameter = 0;
		int maxLevel = 10;
		int[] puzzleConfig = new int[Table.size];
		String filename = new String();
		Puzzle p = new Puzzle();
		
		// Create command line flag options
		Options options = new Options();
		
		Option level  = OptionBuilder.withArgName("int").hasArgs(1).hasArg(true)
			.withDescription("use value for maximum depth for Iterative-Deepening-Search")
			.create("l");
		
		Option randomize  = OptionBuilder.withArgName("int").hasArgs(1).hasArg(true)
			.withDescription("use value to generate a problem Puzzle with a specified number of randomized moves")
			.create("r");
		
		Option file  = OptionBuilder.withArgName("file").hasArgs(1).hasArg(true)
			.withDescription("generate problem Puzzle using a text file")
			.create("f");
		
		Option custom  = OptionBuilder.withArgName("int[" + Table.size + "]")
			.hasArgs(Table.size).hasArg(true).withValueSeparator(',')
			.withDescription("generate problem Puzzle using a custom sequence. Separate values with','")
			.create("c");
		
		Option iDSearch  = OptionBuilder.hasArg(false)
			.withDescription("solve Puzzle using Iterative-Deepening-Search")
			.create("i");
		
		Option bFSearch  = OptionBuilder.hasArg(false)
			.withDescription("solve Puzzle using Breadth-First-Search")
			.create("b");
		
		Option test  = OptionBuilder.hasArg(false)
			.withDescription("run the PuzzleSolver test suite")
			.create("t");
		
		Option puzzleSize  = OptionBuilder.withArgName("int").hasArg(true)
			.withDescription("change the Puzzle dimensions to n by n").create("size");
		
		Option aStarSearch = OptionBuilder.hasArg(false)
			.withDescription("solve Puzzle with the A* Search algorithm")
			.create("a");
		
		Option heuristic = OptionBuilder.hasArg(false)
			.withDescription("change the heuristic for A* to displaced tiles. Default is Manhattan Score")
			.create("h1");
		
		// Option grid = OptionBuilder.hasArg(false)
		// 	.withDescription("print Puzzle's as a n by n grid")
		// 	.create("grid");
		
		options.addOption(level);
		options.addOption(randomize);
		options.addOption(file);
		options.addOption(custom);
		options.addOption(iDSearch);
		options.addOption(bFSearch);
		options.addOption(test);
		options.addOption(puzzleSize);
		options.addOption(aStarSearch);
		options.addOption(heuristic);
		// options.addOption(grid);
		options.addOption(OptionBuilder.withLongOpt("help").create('h'));
 
		String header = "\nPuzzle Solver -  Written by Jordan Williams\n\n";
		HelpFormatter formatter = new HelpFormatter();
		
		// Parse Command Line
		CommandLineParser parser = new GnuParser();
		CommandLine line;
	    try {
	        line = parser.parse(options, args);
			
			// Change Table Dimensions
			if (line.hasOption("size")) {
				int tableSize = Integer.parseInt(line.getOptionValue("size"));
				System.out.println("Changing Puzzle dimensions to " + tableSize + "x" + tableSize + "\n");
			    Table.rows = tableSize;
				Table.cols = tableSize;
				Table.size = tableSize * tableSize;
				Puzzle.updateSolution();
				puzzleConfig = new int[Table.size];
			}
			
			// Heuristic flag
			if (line.hasOption("h1")) {
				// System.out.println("Changing A* heuristic to displaced Tiles");
			    PuzzleSolver.manhattan = false;
			}
			
			// Print Puzzle's as a grid
			// if (line.hasOption("grid")) {
			// 	Table.grid = true;
			// }
			
			// Test flag
			if (line.hasOption("t")) {
				PuzzleSolver.test();
			}
			
			// Depth limit flag
			if(line.hasOption("l")) {
			    maxLevel = Integer.parseInt(line.getOptionValue("l"));
			}
			
			// Randomize flag
			if (line.hasOption("r")) {
				rParameter = Integer.parseInt(line.getOptionValue("r"));
				System.out.println("Generating Puzzle with " + rParameter + " randomized moves\n");
			    p = Puzzle.randomize(rParameter);
			}
			
			// Custom Puzzle flag
			else if (line.hasOption("c")) {
				String[] vals = line.getOptionValues("c")[0].split(",");
		
				if (!PuzzleSolver.puzzleValidator(vals)) {
					System.err.println("Invalid Puzzle: " + Arrays.toString(vals) + "\n");
					System.out.println("Generating Puzzle with 8 randomized moves");
					p = Puzzle.randomize(8);
				}
				
				else {
					for(int i = 0; i < vals.length; i++) {
					    puzzleConfig[i] = Integer.parseInt(vals[i]);
					}
					p = new Puzzle(puzzleConfig);
				}
				System.out.println();
			}
			
			// Puzzle configuration is stored in a file
			else if (line.hasOption("f")) {			
				File fileF = new File(line.getOptionValue("f"));
			
				// Open file
				try {
					Scanner f = new Scanner(fileF);
					ArrayList<Integer> vals = new ArrayList<Integer>();
					while(f.hasNextInt()) {
						vals.add(f.nextInt());
					}
			
					puzzleConfig = PuzzleSolver.convertIntegers(vals);
				
					// If Puzzle is invalid
					if (!PuzzleSolver.puzzleValidator(puzzleConfig)) {
						System.err.println("Invalid Puzzle: " + Arrays.toString(puzzleConfig));		
					} else p = new Puzzle(puzzleConfig);
			
				// File cannot be opened
				} catch(java.io.FileNotFoundException e) {
					System.err.println("File '" + fileF + "' not found! \nGenerating randomized Puzzle...");
					p = Puzzle.randomize(8);
				}
				System.out.println();
			}
			
			// Use Default random Puzzle
			else {
				System.out.println("Generating Puzzle with 8 randomized moves\n");
				p = Puzzle.randomize(8);
			}
			
			// Solve Puzzle with A* Search
			if (line.hasOption("a")) {
				String aStarOutput = new String();
				PuzzleSolver ps = new PuzzleSolver(p);
				try {
					Stopwatch sw = new Stopwatch();
					String h = (PuzzleSolver.manhattan ? "Manhattan Score" : "Displaced Tiles");
					System.out.println("Running A* Search using " + h + " heuristic...");
					
					if (AStar.search(ps)) {
						ps.display(sw);
					}
					else {
						aStarOutput = "Solution not found";
					}
		
				} catch(java.lang.OutOfMemoryError e) {
					aStarOutput = "A* results:\n  iterations: " + ps.iterations 
							  + "\n  Solvable? No. Memory full";			
					try { 
					    Thread.sleep(10000);
					} catch(InterruptedException ex) {
					    Thread.currentThread().interrupt();
					}
				}
				
				// Print BFS results
				System.out.println(aStarOutput + "\n");
			}
			
			// Solve Puzzle using Iterative Deepening Search
			if (line.hasOption("i")) {
				String idfsOutput = new String();
				PuzzleSolver ps = new PuzzleSolver(p);				
				try {
					Stopwatch sw = new Stopwatch();
					System.out.println("Running Iterative Deepening Search with MAX depth " + maxLevel + "...");
					
					if (IterativeDeepeningSearch.search(ps, maxLevel)) {
						ps.display(sw);
					}
					else {
						idfsOutput = "Solution not found";
					}
					
				} catch(java.lang.OutOfMemoryError e) {
					idfsOutput = "IDS results:\n  iterations: " + ps.iterations 
							   + "\n  Solvable? No. Memory full";			
					try {
					    Thread.sleep(5000);
					} catch(InterruptedException ex) {
					    Thread.currentThread().interrupt();
					}
				}
				
				// Print IDS results
				System.out.println(idfsOutput + "\n");
			}
			
			// Solve Puzzle with Breadth-First Search
			if (line.hasOption("b")) {
				String bfsOutput = new String();
				PuzzleSolver ps = new PuzzleSolver(p);
				try {
					Stopwatch sw = new Stopwatch();
					System.out.println("Running Breadth First Search...");
					
					if (BreadthFirstSearch.search(ps)) {
						ps.display(sw);
					}
					else {
						bfsOutput = "Solution not found";
					}
		
				} catch(java.lang.OutOfMemoryError e) {
					bfsOutput = "BFS results:\n  iterations: " + ps.iterations 
							  + "\n  Solvable? No. Memory full";			
					try { 
					    Thread.sleep(10000);
					} catch(InterruptedException ex) {
					    Thread.currentThread().interrupt();
					}
				}
				
				// Print BFS results
				System.out.println(bfsOutput + "\n");
			}
			
			// No search algorithm given
			if (!line.hasOption("i") && !line.hasOption("b") && !line.hasOption("a")) {
				System.out.println();
				formatter.printHelp("java -jar jwill49PuzzleSolver.jar", header, options, "", true);
				System.err.println("\nFlag for A*, Breadth-First, Iterative-Deepening Search is required!\n");
			}
			
	    } catch(ParseException exp) {
	        System.err.println("Parsing failed.  Reason: " + exp.getMessage());
	    }
	}
	
	
	/**
	* Increments iteration counter
	* @param
	* @return
	*/
	public synchronized void incrementCounter() {
		this.iterations++;
	}
	
	
	/**
	* Validates a given array of integers for a Puzzle configuration.
	*	The array must contain every number from 0 to Table.size-1
	* @param: Array<int> of 
	* @return: <boolean>
	*/
	public static boolean puzzleValidator(int[] input) {
		if (input.length != Table.size) return false;
		ArrayList<Integer> vals = new ArrayList<Integer>();
		
		for(int i = 0; i < Table.size; i++) {
			if (input[i] < Table.size && input[i] >= 0 && !vals.contains(input[i]))
				vals.add(input[i]);
		}
		if (vals.size() != Table.size) return false;
		return true;
	}
	
	public static boolean puzzleValidator(String[] input) {
		if (input.length != Table.size) return false;
		
		int puzzleConfig[] = new int[Table.size];
		
		for(int i = 0; i < Table.size; i++) {
		    puzzleConfig[i] = Integer.parseInt(input[i]);
		}
		
		return PuzzleSolver.puzzleValidator(puzzleConfig);
	}
	
	
	/**
	* Converts an ArrayList<int> to an array of ints
	* @param: <ArrayList> of ints
	* @return: <int[]>
	*/
	public static int[] convertIntegers(List<Integer> intList)
	{
	    int[] ret = new int[intList.size()];
		Iterator<Integer> iterator = intList.iterator();
		
	    for (int i = 0; i < ret.length; i++)
	    {
	        ret[i] = iterator.next().intValue();
	    }
	    return ret;
	}
	
	
	public void display(Stopwatch sw) {
		String output = new String();
		String space = "      ";
		output += "        board" + space + space + space + space + space + space
				+ "| # of moves | " 
				+ "solution\n";
		
		for(int i = 0; i < Table.rows; i++) {
			for(int j = 0; j < Table.cols; j++) {
				output += String.format("%02d", this.initialPuzzle.getValue(i, j)) + " ";
			}
		}
		
		output += space + this.solutionLevel + space + "  " + SearchTree.printSolutionPath(this.solutionNode, this.displayPuzzle);
		output += "\n iterations: " + this.iterations;
		output += "\n time: " + sw.elapsedTime();
		System.out.println(output);
	}
	
	
	/**
	* Runs the PuzzleSolver test suite
	* @param None
	* @return None
	*/
	public static void test() {
		TableTester tt = new TableTester();
		tt.test();

		PuzzleTester pt = new PuzzleTester();
		pt.test();

		SearchTreeTester stt = new SearchTreeTester();
		stt.test();
	}
	
	protected Puzzle initialPuzzle = new Puzzle();
	protected int solutionLevel = -1;
	protected int iterations = 0;
	protected Node solutionNode;
	protected static boolean manhattan = true;
	protected boolean displayPuzzle = false;
}

class Stopwatch { 
    private final long start;

    public Stopwatch() {
        start = System.currentTimeMillis();
    } 

    public double elapsedTime() {
        long now = System.currentTimeMillis();
        return (now - start) / 1000.0;
    }
} 