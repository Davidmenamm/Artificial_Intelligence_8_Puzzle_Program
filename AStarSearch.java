package com.company;

import java.util.*;

public class AStarSearch{
	private static ArrayList<AStarState> randBoards;

	private ArrayList<AStarState> genBoards(){
		// Generate 100 Random boards, that are able to be solved
		Fisher_Yates_Array_Shuffling fyas = new Fisher_Yates_Array_Shuffling();
		ArrayList<AStarState> randBoards = new ArrayList<>();
		int numBoards = 100;
		// Init board of all, 0,1,2,3,4,5,6,7,8
		int numTiles = 9;
		Integer[] initBoard = new Integer[numTiles];
		for (int i = 0; i < numTiles; i++) {
			initBoard[i] = i;
		}
		// Add generated boards to list
		for (int i = 0; i < numBoards; i++){
			// Get array of int to initiate a new AStarState
			Integer[] shuffledArrayInt = fyas.fisherYatesShuffling(initBoard, numTiles);
			int[] shuffledArray = Arrays.stream(shuffledArrayInt).mapToInt(Integer::intValue).toArray();
			AStarState newBoardInitState = new AStarState(shuffledArray);
			randBoards.add(newBoardInitState);
		}

		return randBoards;
	}

	/**
	 * Constructor
	 */
	public AStarSearch(){
		this.randBoards = genBoards();
	}

	/**
	 * Main method to run and test program
	 * @param args
	 */
	public static void main(String[] args){
		AStarSearch program;
		try {
			program = new AStarSearch();
			Scanner kbd = new Scanner(System.in);
			int[] initialBoard = new int[]
			{ 3,2,0,6,1,5,7,4,8 };
			program.run(initialBoard, 2);
		} catch (Exception e) {
			e.printStackTrace();
		}

	}

	/**
	 * to run the the algorithm
	 * @param initialBoard
	 * @param option
	 */
	public void run(int[] initialBoard, int option){
		solvePuzzle(initialBoard,option);
	}


	/**
	 * Calculate the coefficients for the learned heuristic
	 */
	private static double [] getCoefficients(){
		// get x1, and x2 metrics for each of the random boards
		int numBoards = 100;
		double [] x1Arr = new double[numBoards];
		double [] x2Arr = new double[numBoards];
		double [] yMDistArr = new double[numBoards];
		for(int i = 0; i < numBoards; i++){
			AStarState currentBoard = randBoards.get(0);
			x1Arr[i] = currentBoard.getX1Idx(); // x1 of all boards
			x2Arr[i] = currentBoard.getX2Idx(); // x2 of all boards
			yMDistArr[i] = currentBoard.getManhattanDistance(); // m distance of all boards
		}
		// generate Matrix
		Regresion matrix = new Regresion(x1Arr, x2Arr, yMDistArr);
		// get betta coefficients for learned heuristic algorithm
		double [] betaArr = new double[3];
		betaArr = matrix.getCoefficients();
		// return
		return betaArr;
	}

	/**
	 * Learned heuristic: This method uses the beta coefficients for the learned heuristic
	 * which was obtained with operations of 100 random boards.
	 * The formula it uses for the learned heuristic which is a regresion is
	 * learned heuristic = Β0 + Β1*x1 + Β2*x2, where B is are the beta coefficients
	 */
	public static double learnedHeuristic(AStarState currState){
		// get betta coefficients
		double [] betaArr = getCoefficients();
		double currX1 = currState.getX1Idx();
		double currX2 = currState.getX2Idx();
		// apply formula regression to get heuristic
		double lh = betaArr[0]+betaArr[1]*currX1+betaArr[2]*currX2;
		// return
		return lh;
	}


	/**
	 * Method that solves the puzzle
	 */
	public static void solvePuzzle(int[] board, int option){
		Queue<Node> frontier = new LinkedList<Node>();
		Node initial = new Node(new AStarState(board));
		frontier.add(initial);
		int timeComplexity = 1; //counter for number of nodes visited
		int spaceComplexity = frontier.size(); //counter for maximum space of the frontier

		do {
			Node currentNode = (Node) frontier.poll();
			if (!currentNode.getCurrentState().isGoal()){
				ArrayList<AStarState> tempChildren = currentNode.getCurrentState().generateSuccessors();
				ArrayList<Node> nodeChildren = new ArrayList<Node>();
				spaceComplexity = Math.max(frontier.size(), spaceComplexity);//Gets the maximum size of the frontier

				Node visitedNode;
				// option for not using a heuristic
				if(option == 1){
					for (int i = 0; i < tempChildren.size(); i++){
						visitedNode = new Node(currentNode,
								tempChildren.get(i),
								currentNode.getActualCost() + 1);

						if (!AStarState.isRepeatedState(visitedNode)){
							nodeChildren.add(visitedNode);
						}
					}
				}
				// option for manhattan distance heuristic
				if(option == 2){
					for (int i = 0; i < tempChildren.size(); i++){
						visitedNode = new Node(currentNode,
									tempChildren.get(i),
									currentNode.getActualCost() + 1,
									((AStarState) tempChildren.get(i)).getManhattanDistance());
						if (!AStarState.isRepeatedState(visitedNode)){
							nodeChildren.add(visitedNode);

						}
					}
				}
				// option for learned heuristic
				if(option == 3){
					for (int i = 0; i < tempChildren.size(); i++){
						visitedNode = new Node(currentNode,
								tempChildren.get(i),
								currentNode.getActualCost() + 1,
								learnedHeuristic((AStarState)tempChildren.get(i)));
						if (!AStarState.isRepeatedState(visitedNode)){
							nodeChildren.add(visitedNode);
						}
					}
				}

				if (nodeChildren.size() == 0) continue;
				Node cheapestNode = nodeChildren.get(0);
				for (int i = 0; i < nodeChildren.size(); i++){
					if (cheapestNode.getFCost() > nodeChildren.get(i).getFCost()){
						cheapestNode = nodeChildren.get(i);
					}
				}

				int cheapestCost = (int) cheapestNode.getFCost();
				for (int i = 0; i < nodeChildren.size(); i++){
					if (nodeChildren.get(i).getFCost() == cheapestCost){
						frontier.add(nodeChildren.get(i));
					}
				}
				timeComplexity++;
			}
			else{
				Stack<Node> solution = new Stack<Node>();
				solution.push(currentNode);
				currentNode = currentNode.getParent();

				while (currentNode.getParent() != null){
					solution.push(currentNode);
					currentNode = currentNode.getParent();
				}
				solution.push(currentNode);
				int noOfLoops = solution.size();
				for (int i = 0; i < noOfLoops; i++){
					currentNode = solution.pop();
					currentNode.getCurrentState().printBoard();
					System.out.println();
				}
				System.out.println("Cost: " + currentNode.getActualCost());
				System.out.println("Time Complexity: "+ timeComplexity);
				System.out.println("Space Complexity: "+ spaceComplexity);
				System.exit(0);
			}
		} while (!frontier.isEmpty());
		System.out.println("No solution!");
	}
}
