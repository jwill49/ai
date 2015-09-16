/* Written by Jordan Williams */

package puzzle;

import java.util.HashSet;
import java.util.Set;

class PuzzleTester {
	/**
	* Tests the swap(...) method
	* @param: None
	* @return: "Pass" if test passes, "Fail" if test fails
	*/
	public String testSwap() {
		Puzzle p = new Puzzle();
		
		int x = p.getValue(p.getBlankCol(), p.getBlankRow());
		int y = p.getValue(0,1);
		if(p.swap(0,1) != true) return "Fail";
				
		if (p.getValue(0,0) != y) return "Fail";
		if (p.getValue(0,1) != x) return "Fail";
		return "Pass";
	}
	
	
	/**
	* Tests the isValidSwap(...) method
	* @param: None
	* @return: "Pass" if test passes, "Fail" if test fails
	*/
	public String testIsValidSwap() {
		Puzzle p = new Puzzle();
		
		// Boundary Cases
		for (int i = 0; i < Table.rows; i++) {
			if (p.isValidSwap(i,-1,i,0)) return "Fail";
			if (p.isValidSwap(i,Table.cols,i,Table.cols-1)) return "Fail";
		}
		for (int j = 0; j < Table.cols; j++) {
			if (p.isValidSwap(-1,j,0,j)) return "Fail";
			if (p.isValidSwap(Table.rows,j,Table.rows-1,j)) return "Fail";
		}
		
		// Valid Cases on interior nodes
		for (int i = 1; i < Table.rows-1; i++) {
			for (int j = 1; j < Table.cols-1; j++) {
				if (p.isValidSwap(i,j,i+1,j) != true) return "Fail"; // Down
				if (p.isValidSwap(i,j,i-1,j) != true) return "Fail"; // Up
				if (p.isValidSwap(i,j,i,j+1) != true) return "Fail"; // Right
				if (p.isValidSwap(i,j,i,j-1) != true) return "Fail"; // Left
			}
		}
		return "Pass";
	}
	
	
	/**
	* Tests the shuffle() method
	* @param: None
	* @return: "Pass" if test passes, "Fail" if test fails
	*/
	public String testShuffle() {
		Puzzle p1 = new Puzzle();
		Puzzle p2 = new Puzzle();
		int discrepancies = 0;
		Set<Integer> vals = new HashSet<Integer>();
		
		p1.shuffle();
		
		// Every value 0 to Puzzle.size-1 should be accounted for
		for(int i = 0; i < Table.rows; i++) {
			for(int j = 0; j < Table.cols; j++) {
				vals.add(p1.getValue(i, j));
			}
		}
		for(int i=0; i < Table.size; i++) {
			if (!vals.contains(i)) return "Fail";
		}
		
		// Count the number of discrepancies
		for(int i = 0; i < Table.rows; i++) {
			for(int j = 0; j < Table.cols; j++) {
				if (p1.getValue(i, j) != p2.getValue(i, j)) discrepancies++;
			}
		}
		
		// The discrepancies should be > half of the Puzzle size
		if (discrepancies < (Table.size / 2)) return "Fail";
		
		return "Pass";
	}
	
	
	/**
	* Tests the getState() method
	* @param: None
	* @return: "Pass" if test passes, "Fail" if test fails
	*/
	public String testGetState() {
		int[] input = {2,1,3,5,4,6,8,7,9,11,10,0,12,14,13,15}, testResult;
		
		Puzzle p = new Puzzle(input);
		testResult = p.getState();
		
		for(int i = 0; i < Table.size; i++) {
			if (input[i] != testResult[i]) return "Fail";
		}
		
		return "Pass";
	} 
	
	
	/**
	* Tests the clone() method
	* @param: None
	* @return: "Pass" if test passes, "Fail" if test fails
	*/
	public String testClone() {
		Puzzle p = new Puzzle();
		Puzzle p2 = p.clone();
		
		if (p2.getBlankRow() != p.getBlankRow()) return "Fail";
		if (p2.getBlankCol() != p.getBlankCol()) return "Fail";
		if (p2.getValue(0,0) != p.getValue(0,0)) return "Fail";
		p2.swap(0,1);
		if (p2.getValue(0,0) == p.getValue(0,0)) return "Fail";
		
		return "Pass";
	}
	
	
	/**
	* Tests the clone() method
	* @param: None
	* @return: "Pass" if test passes, "Fail" if test fails
	*/
	public String testMoveUp() {
		int[] input = {1,3,4,9,0,10,11,12,15,14,13,2,5,6,8,7};
		
		Puzzle p = new Puzzle(input), p2, p3;
				
		p2 = p.moveUp();
		if (p2.getBlankRow() != 0) return "Fail";
		if (p2.getBlankCol() != 0) return "Fail";
				
		p3 = p2.moveUp();
		if (p3 != null) return "Fail";
		
		return "Pass";
	}
	
	
	/**
	* Tests the moveLeft() method
	* @param: None
	* @return: "Pass" if test passes, "Fail" if test fails
	*/
	public String testMoveLeft() {
		int[] input = {1,3,4,9,10,0,11,12,15,14,13,2,5,6,8,7};
		
		Puzzle p = new Puzzle(input), p2, p3;
				
		p2 = p.moveLeft();
		if (p2.getBlankRow() != 1) return "Fail";
		if (p2.getBlankCol() != 0) return "Fail";
				
		p3 = p2.moveLeft();
		if (p3 != null) return "Fail";
		
		return "Pass";
	}
	
	
	/**
	* Tests the moveRight() method
	* @param: None
	* @return: "Pass" if test passes, "Fail" if test fails
	*/
	public String testMoveRight() {
		int[] input = {1,3,4,9,10,11,0,12,15,14,13,2,5,6,8,7};
		
		Puzzle p = new Puzzle(input), p2, p3;
				
		p2 = p.moveRight();
		if (p2.getBlankRow() != 1) return "Fail";
		if (p2.getBlankCol() != 3) return "Fail";
				
		p3 = p2.moveRight();
		if (p3 != null) return "Fail";
		
		return "Pass";
	}
	
	
	/**
	* Tests the moveRight() method
	* @param: None
	* @return: "Pass" if test passes, "Fail" if test fails
	*/
	public String testMoveDown() {
		int[] input = {1,3,4,9,10,15,11,12,0,14,13,2,5,6,8,7};
		
		Puzzle p = new Puzzle(input), p2, p3;
				
		p2 = p.moveDown();
		if (p2.getBlankRow() != 3) return "Fail";
		if (p2.getBlankCol() != 0) return "Fail";
				
		p3 = p2.moveDown();
		if (p3 != null) return "Fail";
		
		Puzzle p4 = new Puzzle().moveDown().moveDown().moveDown().moveRight().moveUp().moveRight().moveUp().moveLeft();
		if (p4.getBlankRow() != 1) return "Fail";
		if (p4.getBlankCol() != 1) return "Fail";
		
		return "Pass";
	}
	
	
	public String testUpdateManhattanScore() {
		Puzzle p1 = new Puzzle(Puzzle.solutionArr);
		if (p1.getManhattanScore() != 0) return "Fail " + p1.getManhattanScore();
		
		return "Pass";
	}
	
	
	/**
	* Executes the Puzzle test suite
	* @param: None
	* @return: None
	*/
	public void test() {
		System.out.println("Testing Puzzle Class...");
		
		String output = "Testing Puzzle swap(<int>, <int>, <int>, <int>)... " + this.testSwap() + "\n"
					  + "Testing Puzzle isValidSwap(<int>, <int>, <int>, <int>)... " + this.testIsValidSwap() + "\n"
					  + "Testing Puzzle shuffle()... " + this.testShuffle() + "\n"
					  + "Testing Puzzle getState()... " + this.testGetState() + "\n"
					  + "Testing Puzzle clone()... " + this.testClone() + "\n"
					  + "Testing Puzzle moveUp()... " + this.testMoveUp() + "\n"
					  + "Testing Puzzle moveLeft()... " + this.testMoveLeft() + "\n"
					  + "Testing Puzzle moveRight()... " + this.testMoveRight() + "\n"
					  + "Testing Puzzle moveDown()... " + this.testMoveDown() + "\n"
					  + "Testing Puzzle manhattanScore()... " + this.testUpdateManhattanScore() + "\n";
		
		System.out.println(output);
	}
}