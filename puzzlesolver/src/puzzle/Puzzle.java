/* Written by Jordan Williams */

package puzzle;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Arrays;
import java.lang.Math;
import java.util.Random;

class Puzzle extends Table implements Cloneable {
	public Puzzle() {
		this.updateState();
	}
	
	public Puzzle(int[] input) {
		super(input);
		this.updateState();
	}
	
	public int getBlankRow() { return blankRow; }
	public int getBlankCol() { return blankCol; }
	public int getPathCost() { return pathCost; }
	public int getDisplacedTiles() { return displacedTiles; }
	public int getManhattanScore() { return manhattanScore; }
	public void setPathCost(int x) { pathCost = x; }
		
	
	/** 
	* Shuffles the square values arbitrarily
	* @param: None
	* @return: Void
	*/
	public void shuffle() {
		ArrayList<Integer> vals = new ArrayList<Integer>(Table.size);
		
		// Populate vaild numbers
		for(int i = 0; i < Table.size; i++) {
			vals.add(i);
		}
		
		// Shuffle numbers
		Collections.shuffle(vals);
		
		// Repopulate Puzzle
		for(int i = 0, k = 0; i < Table.rows; i++) {
			for(int j = 0; j < Table.cols; j++, k++) {
				this.setValue(i, j, vals.get(k));
			}
		}
		
		this.updateState();
	}
	
	
	/** 
	* Randomizes a limited number of movements to ensure a solution
	*	 can be found with at most a certain number of moves
	* @param: <int> The number of random moves to perform
	* @return: <Puzzle> A randomized Puzzle
	*/
	public static Puzzle randomize(int numMoves) {
		Random r = new Random();
		int[] s = Puzzle.getSolution();
		Puzzle p = new Puzzle(s);
		for(int i = 0; i < numMoves;) {
			String pState = p.state;
			int m = r.nextInt(4) % 4;
			switch(m) {
				case 0: p = (p.moveLeft()!=null ? p.moveLeft() : p);
						break;
				case 1: p = (p.moveRight()!=null ? p.moveRight() : p);
						break;
				case 2: p = (p.moveUp()!=null ? p.moveUp() : p);
						break;
				case 3: p = (p.moveDown()!=null ? p.moveDown() : p);
						break;
				default: p = (p.moveLeft()!=null ? p.moveLeft() : p);
			}
			if (!p.state.equals(pState)) i++;
		}
		return p; 
	}
	
	
	/**
	* Swaps values of blank and an adjacent square
	* @param: <int> <int>
	* @return: true if swap is successful
	*/
	public boolean swap(int row, int col) {
		if (isValidSwap(this.blankRow, this.blankCol, row, col)) {
			int val1 = this.getValue(this.blankRow, this.blankCol);
			int val2 = this.getValue(row, col);
			
			this.setValue(this.blankRow, this.blankCol, val2);
			this.setValue(row, col, val1);
			return true;
		}
		return false;
	}
	
	
	/** 
	* Determines whether or not a swap is a valid move within Puzzle dimensions
	* @param: <int>, <int>, <int>, <int>
	* @return: true for vaild moves, else false
	*/
	public boolean isValidSwap(int row1, int col1, int row2, int col2) {
		int rowMax = this.getRows() - 1;
		int colMax = this.getCols() - 1;
		int rowDiff = Math.abs(row1-row2);
		int colDiff = Math.abs(col1-col2);
		
		// Boundary Cases
		if (row1 < 0 || col1 < 0 || row2 < 0 || col2 < 0) return false;
		else if (row1 > rowMax || col1 > colMax || row2 > rowMax || col2 > colMax) return false;
		
		// Interior Cases
		else if (rowDiff + colDiff > 1) return false;
		else return true;
	}
	
	
	/** 
	* Moves the blank square up one square
	* @param: None
	* @return: the resulting <Puzzle>
	*/
	public Puzzle moveUp() {
		Puzzle p2 = this.clone();
		if (p2.swap(this.blankRow-1, this.blankCol)) {
			p2.blankRow--;
			p2.pathCost = this.pathCost + 1;
			p2.updateState();
			return p2;
		}
		
		return null;
	}
	
	
	/** 
	* Moves the blank square down one square
	* @param: None
	* @return: the resulting <Puzzle>
	*/
	public Puzzle moveDown() {
		Puzzle p2 = this.clone();
		if (p2.swap(this.blankRow+1, this.blankCol)) {
			p2.blankRow++;
			p2.pathCost = this.pathCost + 1;
			p2.updateState();
			return p2;
		}
		
		return null;
	}
	
	
	/** 
	* Moves the blank square left one square
	* @param: None
	* @return: the resulting <Puzzle>
	*/
	public Puzzle moveLeft() {
		Puzzle p2 = this.clone();
		if (p2.swap(this.blankRow, this.blankCol-1)) {
			p2.blankCol--;
			p2.pathCost = this.pathCost + 1;
			p2.updateState();
			return p2;
		}
		
		return null;
	}
	
	
	/** 
	* Moves the blank square right one square
	* @param: None
	* @return: the resulting <Puzzle>
	*/
	public Puzzle moveRight() {
		Puzzle p2 = this.clone();
		if (p2.swap(this.blankRow, this.blankCol+1)) {
			p2.blankCol++;
			p2.pathCost = this.pathCost + 1;
			p2.updateState();
			return p2;
		}
		
		return null;
	}
	
	
	/** 
	* Generates the current state of the Puzzle
	* @param: None
	* @return: an Array<int> of values for the puzzle ordered by row then column
	*/
	public int[] getState() {
		int[] stateArr = new int[Table.size];
		
		// Populate state array
		for(int i = 0, k = 0; i < Table.rows; i++) {
			for(int j = 0; j < Table.cols; j++, k++) {
				stateArr[k] = this.getValue(i, j);
				if (stateArr[k] == 0) {
					blankRow = i;
					blankCol = j;
				}
			}
		}
		return stateArr;
	}
	
	
	/** 
	* Updates the current state of the Puzzle. Should be run after every move
	* @param: None
	* @return: a <String> of values for the puzzle ordered by row then column
	*/
	public void updateState() {
		state = Arrays.toString(this.getState());
		this.updateManhattanScore();
	}
	
	
	/** 
	* Updates the solution to the Puzzle
	* @param
	* @return
	*/
	public static void updateSolution() {
		int s[] = new int[Table.size];
		
		// Store solution to puzzle
		for(int i = 0; i < Table.size; i++) {
			if (i < Table.size-1) s[i] = i+1;
			else s[i] = 0;
		}
		Puzzle.solution = new Table(s);
		Puzzle.solutionArr = s;
		Puzzle.solutionStr = Arrays.toString(s);
	}
	
	
	/** 
	* Updates the the Manhattan score heuristic for the Puzzle
	* @param None
	* @return a <String> of values for the puzzle ordered by row then column
	*/
	public void updateManhattanScore() {
		manhattanScore = 0;
		displacedTiles = 0;
		
		for(int k = 1; k< Table.size; k++) {
			int p[] = this.getPosition(k);
			int s[] = Puzzle.solution.getPosition(k);
			
			manhattanScore += (Math.abs(p[0] - s[0]) + Math.abs(p[1] - s[1]));
			if (p[0] != s[0] || p[1] != s[1]) displacedTiles++;
		}
	}
	
	
	/** 
	* Displays the current state of the Puzzle
	* @param: None
	* @return: <Puzzle>
	*/
	public Puzzle display() {
		super.display();
		return this;
	}
	
	
	/** 
	* Clones the current Puzzle object
	* @param: None
	* @return: the resulting <Puzzle>
	*/
	public Puzzle clone() {
		Puzzle p = new Puzzle(this.getState());
		return p;
	}
	
	
	/** 
	* Returns the solution to the Puzzle
	* @param: None
	* @return: <int[]> the solution
	*/
	public static int[] getSolution() {
		int[] s = new int[Table.size];
		
		// Store solution to puzzle
		for(int i = 0; i < Table.size; i++) {
			if (i < Table.size-1) s[i] = i+1;
			else s[i] = 0;
		}
		return s;
	}
	
	private int pathCost = 0;
	private int manhattanScore = 0;
	private int displacedTiles = 0;
	private int blankRow = 0;
	private int blankCol = 0;
	public String state = new String();
	protected static int[] solutionArr;
	protected static String solutionStr;
	protected static Table solution;
}