package com.company;

import java.util.Arrays;
import java.util.ArrayList;
//import javafx.util;

public class AStarState{

	private int sizeOfPuzzle = 9;
	private int manhattanDistance = 0;
	private int[] goalState = new int[]
	{ 1, 2, 5, 3, 4, 6, 7, 8, 0 };
	private int[] currentBoard;

	/**
	 * Constructor for State
	 */
	public AStarState(int[] board){
		currentBoard = board;
		manhattanDistance = calculateManhattanDistance();
	}

	/**
	 * Getter for the currentBoard
	 */
	public int[] getCurrentBoard(){
		return currentBoard;
	}

	/**
	 * Getter for the manhattanDistance value
	 */
	public int getManhattanDistance(){
		return manhattanDistance;
	}

	/**
	 * Calculate x2, which is the number of pairs horizontal and vertical
	 * that are in the initial board, but not in the goal board
	 */
	public int getX2Idx() {
		ArrayList<ArrayList<Integer>> adjacentTilesCurrent = new ArrayList<ArrayList<Integer>>();
		ArrayList<ArrayList<Integer>> adjacentTilesGoal = new ArrayList<ArrayList<Integer>>();
		int count = 0;
		for (int i = 0; i < currentBoard.length; i++) {
			if (i != 2 && i != 5 && i != 8) {
				// pair in current board
				ArrayList<Integer> pairCurrent = new ArrayList<>();
				pairCurrent.add(currentBoard[i]);
				pairCurrent.add(currentBoard[i + 1]);
				adjacentTilesCurrent.add(pairCurrent);
				// pairs for goal state
				ArrayList<Integer> pairGoal = new ArrayList<>();
				pairGoal.add(goalState[i]);
				pairGoal.add(goalState[i + 1]);
				adjacentTilesGoal.add(pairGoal);
			}
			if (i < 5) {
				// pair in current board
				ArrayList<Integer> pairCurrent = new ArrayList<>();
				pairCurrent.add(currentBoard[i]);
				pairCurrent.add(currentBoard[i + 3]);
				adjacentTilesCurrent.add(pairCurrent);
				// pairs for goal state
				ArrayList<Integer> pairGoal = new ArrayList<>();
				pairGoal.add(goalState[i]);
				pairGoal.add(goalState[i + 3]);
				adjacentTilesGoal.add(pairGoal);
			}
		}
		for (int i = 0; i < currentBoard.length; i++) {
			if (adjacentTilesCurrent.get(i) != adjacentTilesGoal.get(i)) {
				++count;
			}
		}
		return count;
	}

	/**
	 * Calculate x1, the number of tiles not in place, in other words
	 * 	they are not in the ideal position 1,2,3,4,5,6,7,8.
	 */
	public int getX1Idx(){
		int count = 0;
		for (int i = 0; i < currentBoard.length; i++){
			if (currentBoard[i] != goalState[i]){
				count++;
			}
		}
		return count++;
	}

	/*
	 * Calculates the Manhattan Distance
	 */
	private int calculateManhattanDistance(){
		int count = 0;
		int index = -1;

		for (int y = 0; y < 3; y++){
			for (int x = 0; x < 3; x++){
				index++;
				// subtract 1 from the value of the current board at index to assign
				// -1 to value when the tile is blank
				int value = (currentBoard[index] - 1);

				/*
				 * Checks the manhattan distance of tiles excluding the blank tile
				 * since value of blank tile = -1
				 */
				if (value != -1){
					int horizontalMove = value % 3;
					int verticalMove = value / 3;
					count += Math.abs(verticalMove - (y)) + Math.abs(horizontalMove - (x));
				}
			}
		}
		return count;
	}

	/*
	 * Locates the blank tile and returns the index of it
	 */
	private int getIndexOfBlankTile(){
		int blankIndex = -1;

		for (int i = 0; i < sizeOfPuzzle; i++){
			if (currentBoard[i] == 0)
				blankIndex = i;
		}
		return blankIndex;
	}

	/**
	 * Prints the current state of the board.
	 */
	public void printBoard(){
		int counter=1;
		for(int x=0; x<currentBoard.length; x++){
			if(counter<3){
				System.out.print(currentBoard[x]+" ");
				counter++;
			} else{
				System.out.println(currentBoard[x]+" ");
				counter=1;
			}

		}
	}

	/*
	 * Makes a copy of the board
	 */
	private int[] copyBoard(int[] board){
		int[] duplicate = new int[sizeOfPuzzle];
		for (int i = 0; i < sizeOfPuzzle; i++)
		{
			duplicate[i] = board[i];
		}
		return duplicate;
	}

	/*
	 * Switches the data at index1 and index2 in a copy of the current board
	 */
	private void moveTile(int index1, int index2, ArrayList<AStarState> state){
		int[] duplicate = copyBoard(currentBoard);
		int temp = duplicate[index1];
		duplicate[index1] = currentBoard[index2];
		duplicate[index2] = temp;
		state.add((new AStarState(duplicate)));
	}

	/**
	 * Returns all the Successors
	 */
	public ArrayList<AStarState> generateSuccessors(){
		ArrayList<AStarState> successors = new ArrayList<AStarState>();
		int blankIndex = getIndexOfBlankTile();

		if (blankIndex != 0 && blankIndex != 3 && blankIndex != 6){
			moveTile(blankIndex - 1, blankIndex, successors);
		}
		if (blankIndex != 6 && blankIndex != 7 && blankIndex != 8){
			moveTile(blankIndex + 3, blankIndex, successors);
		}
		if (blankIndex != 0 && blankIndex != 1 && blankIndex != 2){
			moveTile(blankIndex - 3, blankIndex, successors);
		}
		if (blankIndex != 2 && blankIndex != 5 && blankIndex != 8)
		{
			moveTile(blankIndex + 1, blankIndex, successors);
		}
		return successors;
	}

	/**
	 * Checks if the current state is the goal state.
	 */
	public boolean isGoal(){
		if (Arrays.equals(currentBoard, goalState)){
			return true;
		} else{
			return false;
		}
	}

	/**
	 * Equals method to compare two states.
	 */
	public boolean equals(AStarState s){
		if (Arrays.equals(currentBoard, ((AStarState) s).getCurrentBoard())){
			return true;
		}
		else{
			return false;
		}
	}

	/*
	 * Checks if a Node has already been checked.
	 */
	public static boolean isRepeatedState(Node n){
		boolean flag = false;
		Node node = n;

		while (n.getParent() != null && !flag){
			if (n.getParent().getCurrentState().equals(node.getCurrentState())){
				flag = true;
			}
			n = n.getParent();
		}
		return flag;
	}
}
