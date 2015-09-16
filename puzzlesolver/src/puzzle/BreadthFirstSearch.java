package puzzle;

import java.util.LinkedList;
import java.util.HashSet;

public class BreadthFirstSearch {
	/**
	* Searches for a solution to the root Puzzle using breadth-first search
	* @param: None
	* @return: <boolean> true if a solution is possible. false if no solution, or if heap is maxed out
	*/
	public static boolean search(PuzzleSolver ps) {
		Puzzle p = ps.initialPuzzle;
		SearchTree st = new SearchTree(p);
		LinkedList<Node> fringe = new LinkedList<Node>();
		HashSet<String> visited = new HashSet<String>();
		
		// Enqueue root Puzzle
		fringe.add(st.root);
		
		// While the queue isn't empty
		while (!fringe.isEmpty()) {
			ps.incrementCounter();
			
			// Remove first node from the fringe and mark as visited
			Node current = fringe.removeFirst();
			visited.add(current.getState());
			
			// If Solution is found, return true
			if (current.getState().equals(Puzzle.solutionStr)) {
				// SearchTree.printSolutionPath(current, ps.displayPuzzle);
				ps.solutionNode = current;
				ps.solutionLevel = current.getLevel();
				return true;
			}
			
			// Else, add Children of node to fringe
			current.expand(fringe, visited, true);
		}
		return false;
	}
}