package puzzle;

class TableTester {
	/** 
	* Tests the Table contructor
	* @param None
	* @return "Pass" if test passes, "Fail" if test fails
	*/
	public String testConstructor() {
		Table t = new Table();
		int rows = t.getRows(), cols = t.getCols();
		
		for(int i = 0, k = 0; i < rows; i++) {
			for(int j = 0; j < cols; j++, k++) {
				if (t.getValue(i, j) != k) return "Fail";
			}
		}
		return "Pass";
	}
	
	
	/** 
	* Tests the implementation of setValue(<int>, <int>)
	* @param None
	* @return "Pass" if test passes, "Fail" if test fails
	*/
	public String testSetValue() {
		Table t = new Table();
		
		// Assign values in descending order
		for(int i = 0, k = Table.size; i < Table.rows; i++) {
			for (int j = 0; j < Table.cols; j++) {
				t.setValue(i, j, --k);
			}
		}
		
		// Test values
		for(int i = 0, k = Table.size; i < Table.rows; i++) {
			for (int j = 0; j < Table.cols; j++) {
				if (t.getValue(i, j) != --k) return "Fail";
			}
		}
		
		return "Pass";
	}
	
	
	/** 
	* Tests the flattenPosition()
	* @param None
	* @return
	*/
	public String testFlattenPosition() {
		Table t = new Table();
		
		for(int i = 0, k = 0; i < Table.rows; i++) {
			for(int j = 0; j < Table.cols; j++, k++) {
				if (t.flattenPosition(i, j) != k) return "Fail";
			}
		}
		return "Pass";
	}
	
	
	/** 
	* Executes the Table test suite
	* @param None
	* @return void
	*/
	public void test() {
		System.out.println("Testing Table Class... ");
		System.out.println("Testing Table constructor... " + this.testConstructor());
		System.out.println("Testing Table setValue(<int>, <int>)... " + this.testSetValue());
		System.out.println();
	}
}