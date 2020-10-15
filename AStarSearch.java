import java.util.Scanner;
import java.util.Stack;
import java.util.Queue;
import java.util.LinkedList;
import java.util.ArrayList;

public class AStarSearch{
	private static boolean heuristic = true;

	public static void main(String[] args){
		AStarSearch program;
		try {
			program = new AStarSearch();
			program.run(heuristic);
		} catch (Exception e) {
			e.printStackTrace();
		}

	}

	public void run(boolean h){
		Scanner kbd = new Scanner(System.in);
		int[] initialBoard = new int[]
		{ 3,2,0,6,1,5,7,4,8 };

		solvePuzzle(initialBoard,h);
	}

	/**
	 * Method that solves the puzzle
	 */
	public static void solvePuzzle(int[] board, boolean heuristic){
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
				if(heuristic == false){
					for (int i = 0; i < tempChildren.size(); i++){
						visitedNode = new Node(currentNode,
								tempChildren.get(i),
								currentNode.getActualCost() + 1);

						if (!AStarState.isRepeatedState(visitedNode)){
							nodeChildren.add(visitedNode);
						}
					}
				}

				if(heuristic == true){
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
