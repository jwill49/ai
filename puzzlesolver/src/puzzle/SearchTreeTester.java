package puzzle;

import javax.swing.tree.DefaultMutableTreeNode;
import javax.swing.tree.MutableTreeNode;
import javax.swing.tree.TreeNode;
import java.util.Enumeration;

class SearchTreeTester {
	public String testNode() {
		Puzzle p = new Puzzle();
		SearchTree st = new SearchTree(p);
		Node rt = st.root;
		
		// Test Root node Assignment
		if (rt.getPuzzle() != p) return "Fail - Root Assignment";
		
		// Test Child Assignment after add()
		MutableTreeNode c1 = new Node(p.moveRight());
		MutableTreeNode c2 = new Node(p.moveDown());
		st.root.add(c1);
		st.root.add(c2);
		if (st.root.getChildCount() != 2) return "Fail - Child Assignment";
		Enumeration children = st.root.children();
		Node child1 = (Node) children.nextElement();
		Node child2 = (Node) children.nextElement();
		Puzzle p2 = child1.getPuzzle();
		Puzzle p3 = child2.getPuzzle();
		if (!p2.state.equals(p.moveRight().state)) return "Fail - Child Assignment";
		if (!p3.state.equals(p.moveDown().state)) return "Fail - Child Assignment";
		
		// Test Parent Assignment after add()
		Node parent1 = child1.parent();
		Node parent2 = child2.parent();
		if (parent1 != parent2) return "Fail - Parent Assignment";
		
		// Test getRoot() from MutableTreeNode
		Node root = (Node) child1.getRoot();
		if (root != st.root) return "Fail - MTN getRoot()";
		
		// Test getPath() from MutableTreeNode
		DefaultMutableTreeNode child3 = new Node(p2.moveDown());  // Move child 1 Down
		child1.add(child3);
		TreeNode[] path = child3.getPath();
		if (path[0] != root) return "Fail - MTN getPath()";
		if (path[1] != child1) return "Fail - MTN getPath()";
		if (path[2] != child3) return "Fail - MTN getPath()";
		
		// Test getLevel() from MutableTreeNode
		if (root.getLevel() != 0) return "Fail - MTN getLevel()";
		if (child1.getLevel() != 1) return "Fail - MTN getLevel()";
		if (child2.getLevel() != 1) return "Fail - MTN getLevel()";
		if (child3.getLevel() != 2) return "Fail - MTN getLevel()";
		
		return  "Pass";
	}
	
	public String testEquals() {
		int s[] = new int[Table.size];
		
		for(int i = 0, k = Table.size-1; i < Table.size; i++, k-- ) {
			s[i] = k;
		}
		
		Puzzle p1 = new Puzzle(s);
		Puzzle p2 = new Puzzle(s);
		Node n1 = new Node(p1);
		Node n2 = new Node(p2);
		
		if (!n1.equals(n2)) return "Fail";
		return "Pass";
	}
	
	public String testHashCode() {
		int s[] = new int[Table.size];
		
		for(int i = 0, k = Table.size-1; i < Table.size; i++, k-- ) {
			s[i] = k;
		}
		
		Puzzle p1 = new Puzzle(s);
		Puzzle p2 = new Puzzle(s);		
		Node n1 = new Node(p1);
		Node n2 = new Node(p2);
		
		if (n1.hashCode() != n2.hashCode()) return "Fail";
		return "Pass";
	}
	
	public void test() {		
		System.out.println("Testing Search Tree Class...");
		String output = "Testing SearchTree Node... " + this.testNode() + "\n"
			 		  + "Testing SearchTree equals()... " + this.testEquals() + "\n"
					  + "Testing SearchTree hashcode()... " + this.testHashCode() + "\n";
		System.out.println(output);
	}
}