package puzzle;

import java.util.Enumeration;

public class IterativeDeepeningSearch {
	/**
	* Searches for a solution to the root Puzzle using Iterative deepening Search. 
	*	This will ivoke Limited Depth-First Search on the root puzzle.
	* @param: The maximum depth<int> of the decision tree to traverse
	* @return: <boolean> true if a solution is possible. false if no solution
	*/
	public static boolean search(PuzzleSolver ps, int maxLevel) {
		int level = -1;
		
		while((++level) <= maxLevel) {
			// System.out.println("Searching with depth: " + level);
			Puzzle p = ps.initialPuzzle;
			SearchTree st = new SearchTree(p);
			boolean result = IterativeDeepeningSearch.searchR(ps, st.root, 0, level);
			if (result) return true;
		}
		return false;
	}
	
	
	/**
	* Searches for a solution to the the given Puzzle recursively using Limited Depth-First Search
	* @param: The current <SearchTree>, <Node>, level<int>, and the maximum level<int>
	* @return: <boolean> true if a solution is possible. false if no solution
	*/
	public static boolean searchR(PuzzleSolver ps, Node current, int currentLevel, int maxLevel) {
		if (currentLevel > maxLevel) return false;
		ps.incrementCounter();
		
		// If solution found return true
		if (current.getState().equals(Puzzle.solutionStr)) {
			ps.solutionLevel = currentLevel;
			ps.solutionNode = current;
			
			// SearchTree.printSolutionPath(current, ps.displayPuzzle);
			return true;
		}
		
		current.expand(null, null, false);
		
		Enumeration children = current.children();
		
		boolean result = false;
		while(children.hasMoreElements() && !(currentLevel+1 > maxLevel)) {
			result = IterativeDeepeningSearch.searchR(ps, (Node)children.nextElement(), currentLevel+1, maxLevel);
			if (result == true) return true;
		}
		
		return result;
	}
}