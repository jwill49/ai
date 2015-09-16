# Puzzle Solver #
####Written by Jordan Williams####
____



PuzzleSolver will determine if a puzzle is solvable. If it is is solvable in deterministic time, the program will display the required actions to reach a solution as well as the number of possible action sequences examined. If a puzzle is not solvable in deterministic time, the program will report it as unsolvable.

Three algorithms are used in PuzzleSolver: **_A* Search_**, **_Breadth-First Search_** and **_Iterative Deepening Search_**.

###Running Puzzle Solver###
Run PuzzleSolver by navigating to this directory. Then enter `java -jar jwill49PuzzleSolver.jar`

If running on a 64-bit machine, include the 64-bit flag : `java -d64 -jar jwill49PuzzleSolver.jar`

PuzzleSolver accepts the following flags at command line:

* **` -a             solve Puzzle with the A* Search algorithm`**
* **`-b			  solve Puzzle using Breadth-First-Search`**
* `-c <int[16]>	generate problem Puzzle using a custom sequence. Separate values with','`
* `-f <file>	generate problem Puzzle using a text file`
* `-h,--help`
* `-h1           change the heuristic for A* to displaced tiles. Default is Manhattan Score`
* **`-i			  solve Puzzle using Iterative-Depth-Search`**
* `-l <int>		use value for maximum depth for Iterative-Deepening-Search`
* `-r <int>		use value to generate a problem Puzzle with a specified number of randomized moves*`
* `-size <int>  change the Puzzle dimensions to n by n. The default is 4 by 4`
* `-t           run the PuzzleSolver test suite`

\* **Note:** a randomized board is the solution plus a sequence of randomized swaps with the blank square and its neighbors. Because of boundary cases and the possibility of a move negating its predecessor, this only ensures that a solution **_is no deeper_** in the decision tree than this value.
##You must use any combination of `-a`, `-b`, and `-i` flags to solve a puzzle!##

###Recompiling###
1. Update java source files in src/puzzle
2. Run `javac -cp lib/*:. src/puzzle/*.java -d bin/`
3. Run `ant`