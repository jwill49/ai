/* Written by Jordan Williams */

package puzzle;

import java.util.Set;
import java.util.HashSet;
import java.util.Comparator;
import java.util.Collection;
import javax.swing.tree.TreeNode;
import javax.swing.tree.DefaultMutableTreeNode;

class Node extends DefaultMutableTreeNode implements Comparator<Node>, Comparable<Node> {
	public Node() {
		super();
	}
	
	public Node(Puzzle p) {
		super(p);
	}
	
	public Node(Puzzle p, char m) {
		super(p);
		previousMove = m;
	}
	
	public Puzzle getPuzzle() {
		return (Puzzle) this.getUserObject();
	}
	
	public String getState() {
		Puzzle p = (Puzzle) this.getUserObject();
		return p.state;
	}
	
	public Node parent() {
		return (Node) this.getParent();
	}
	
	@Override
	public int compareTo(Node n){
		Puzzle p1 = this.getPuzzle(), p2 = n.getPuzzle();
		
		return ((Integer)AStar.costFunction(p1)).compareTo((Integer)AStar.costFunction(p2));
	}

	@Override
	public int compare(Node n1, Node n2){
		Puzzle p1 = n1.getPuzzle(), p2 = n2.getPuzzle();
		
		return AStar.costFunction(p1) - AStar.costFunction(p2);
	}
	
	@Override
	public boolean equals(Object other) {
		if (other == null) return false;
		if (this.getClass() != other.getClass()) return false;
		if (this == other) return true;
	
		Puzzle p1 = this.getPuzzle(), p2 = ((Node)other).getPuzzle();
	
		if (p1 == null || p2 == null) return false;
		
	
		String s1 = p1.state, s2 = p2.state;
	
		if (!s1.equals(s2)) return false;

		return true;
	}
	
	@Override
	public int hashCode() {
		Puzzle p = this.getPuzzle();
		
		if (p != null) {
			int hash=7;
			int len = p.state.length();
				
			for (int i = 0; i < len; i++) {
				hash = hash * 31 + p.state.charAt(i);
			}
			return hash;
		}
		return super.hashCode();
	}
	
	/**
	* Adds a given Node's children to the SearchTree and enqueues or pushes
	*	each child to the fringe of the PuzzleSolver
	* @param
	* @return
	*/
	public void expand(Object o1, Object o2, boolean queue) {
		Puzzle pp = this.getPuzzle();
		
		@SuppressWarnings("unchecked")
		Collection<Node> fringe = (Collection<Node>)o1;
		
		@SuppressWarnings("unchecked")
		HashSet<String> visited = (HashSet<String>)o2;
		
		// If left move is valid and has not been seen, add to tree and queue
		Puzzle left = pp.moveLeft();
		if (left!=null) {
			Node l = new Node(left, 'L');
			if ( queue == false || (!fringe.contains(l) && !visited.contains(l.getState())) ) {
				this.add(l);
				if(queue) fringe.add(l);
			}
		}
		
		// If right move is valid and has not been seen, add to tree and queue
		Puzzle right = pp.moveRight();
		if (right!=null) {
			Node r = new Node(right, 'R');
			if ( queue == false || (!fringe.contains(r) && !visited.contains(r.getState())) ) {
				this.add(r);
				if(queue) fringe.add(r);
			}
		}
		
		// If up move is valid and has not been seen, add, to tree and queue
		Puzzle up = pp.moveUp();
		if (up!=null) {
			Node u = new Node(up, 'U');
			if ( queue == false || (!fringe.contains(u) && !visited.contains(u.getState())) ) {
				this.add(u);
				if(queue) fringe.add(u);
			}
		}
		
		// If down move is valid and has not been seen, add to tree and queue
		Puzzle down = pp.moveDown();
		if (down!=null) {
			Node d = new Node(down, 'D');
			if ( queue == false || (!fringe.contains(d) && !visited.contains(d.getState())) ) {
				this.add(d);
				if(queue) fringe.add(d);
			}
		}
	}
	
	protected char previousMove = ' ';;
}

class SearchTree {
	public SearchTree(Puzzle p) {
		root.setUserObject(p);
	}
	
	
	/**
	* Prints all of the Puzzles that precede the solution puzzle
	* @param: The solution puzzle node<Node>
	* @return: void
	*/
	public static String printSolutionPath(Node currentNode, boolean displayPuzzle) {
		TreeNode[] path = currentNode.getPath();
		if (displayPuzzle) System.out.println("	Printing solution path:");
		String moveSequence = new String();
		
		int length = path.length;
		for(int i = 0; i < length; i++) {
			Node n = (Node) path[i];
			Puzzle p2 = n.getPuzzle();
			moveSequence += (n.previousMove + " ");
			if (displayPuzzle) p2.display();
		}
		return moveSequence;
	}
	
	public Node root = new Node();
}