package puzzle;

import java.util.PriorityQueue;
import java.util.Set;
import java.util.HashSet;

public class AStar {	
	public static int costFunction(Puzzle p) {
		if (PuzzleSolver.manhattan) {
			return p.getPathCost() + p.getManhattanScore();
		}
		return p.getPathCost() + p.getDisplacedTiles();
	}
	
	/**
	* Searches for a solution to the root Puzzle using A* search
	*
	* @param None
	*	
	* @return <boolean> the outcome of the search
	*/
	public static boolean search(PuzzleSolver ps) {
		Puzzle p = ps.initialPuzzle;
		SearchTree st = new SearchTree(p);
		Set<String> visited = new HashSet<String>();
		PriorityQueue<Node> fringe = new PriorityQueue<Node>();
		
		fringe.add(st.root);
		
		// While the queue isn't empty
		while (!fringe.isEmpty()) {
			ps.incrementCounter();
			
			// Remove first node from the fringe
			Node current = fringe.poll();
			
			// If Solution is found, return true
			if (current.getState().equals(Puzzle.solutionStr)) {
				ps.solutionNode = current;
				ps.solutionLevel = current.getLevel();
				// SearchTree.printSolutionPath(current, ps.displayPuzzle);
				return true;
			}
			
			// Mark Node as visited
			visited.add(current.getState());
			
			// Else, add Children of node to fringe
			current.expand(fringe, visited, true);
		}
		return false;
	}
}