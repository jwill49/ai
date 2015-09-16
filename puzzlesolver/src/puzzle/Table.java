/* Written by Jordan Williams */

package puzzle;

class Table {
	/** 
	* Creates a Table object and initializes values in ascending order from 0-15
	* @param: None
	*/
	public Table() {
		for(int i = 0, k = -1; i < rows; i++) {
			for (int j = 0; j < cols; j++) {
				matrix[i][j] = ++k;
			}
		}
	}
	
	
	/** 
	* Creates a Table using a predetermined set of values for each square
	* @param: An Array<int> with each index cooresponding to each square
	*/
	public Table(int[] input) {
		// Assign the squares based upon input
		for(int i = 0, k = 0; i < rows; i++) {
			for (int j = 0; j < cols; j++, k++) {
				matrix[i][j] = input[k];
			}
		}
	}
	
	public int getRows() { return rows; }
	public int getCols() { return cols; }
	public int getSize() { return Table.size; }
	
	
	/**
	* Returns the value of the desired square
	* @param: The row<int> and column<int> of the desired square
	* @return: The value of the square 1-15.
	*	If the square is blank, then return 0
	*/
	public int getValue(int row, int col) {
		return matrix[row][col];
	}
	
	
	/**
	* Finds a value in the Table and returns it's row and column position
	* @param val the value in question
	* @return <int[]>
	*/
	public int[] getPosition(int val) {
		int position[] = new int[2];
		
		for(int i = 0; i < rows; i++) {
			for(int j = 0; j < cols; j++) {
				if (getValue(i,j) == val) {
					position[0] = i;
					position[1] = j;
				}
			}
		}
		return position;
	}
	
	
	/**
	* Takes a row and column and converts it to a single value
	* @param 
	* @return int
	*/
	public int flattenPosition(int row, int col) {
		return (row * Table.cols) + col;
	}


	/** 
	* Sets the value of the specified square
	* @param: The row<int> and column<int> of the desired square
	*	and the value<int> that you want to change the square to
	* @return: Void
	*/
	public void setValue(int row, int col, int val) {
		matrix[row][col] = val;
	}


	/** 
	* Displays the current state of the Table as a grid
	* @param: None
	* @return: Void
	*/
	public Table display() {
		if (Table.grid) {
			for(int i = 0; i < rows; i++) {
				String output = new String();
			
				for(int j = 0; j < cols; j++) {
					output += String.format("%02d", this.getValue(i, j)) + " ";
				}
				System.out.println(output);
			}
			System.out.println();
		}
		
		else {
			String output = new String();
			for(int i = 0; i < rows; i++) {
				for(int j = 0; j < cols; j++) {
					output += String.format("%02d", this.getValue(i, j)) + " ";
				}
			}
			System.out.println(output);
		}
		return this;
	}
	
	private int[][] matrix = new int[rows][cols];
	protected static int rows = 4;
	protected static int cols = 4;
	protected static int size = 16;
	protected static boolean grid = false;
}