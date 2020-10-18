package com.company;

import java.io.IOException;
import java.util.Scanner;

public class PuzzleSearch {

	public static void main(String[] args) {
		PuzzleSearch program;
		try {
			program = new PuzzleSearch();
			program.run();
		}catch(Exception e) {
			e.printStackTrace();
		}
	}

	public void run() {
		Scanner sc = new Scanner(System.in);
		// test printing
		System.out.println("sc is ");
		System.out.println(sc);
		int choice = 0;
		System.out.println("------------------");
		System.out.println("8 Puzzle Search");
		System.out.println("------------------");
		System.out.println("[1] A* Search without heuristic\n"
				+ "[2] A* Search with Manhattan Distance heuristic\n"
				+ "[3] A* Search with learned heuristic\n");
		try {
			System.out.print("Enter Choice: ");
			choice = Integer.parseInt(sc.nextLine());
		} catch (Exception e) {
			System.out.println("Please enter a valid choice.");
		}

		sc.close();
		try {
			determineSearchAlgo(choice);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public void determineSearchAlgo(int c) throws IOException {
		// initial board
		// Scanner kbd = new Scanner(System.in);
		int[] initialBoard = new int[]
				{ 3,2,0,6,1,5,7,4,8 };

		// select option
		switch (c) {
		case 1:
			new AStarSearch().run(initialBoard, 1);
			break;
		case 2:
			new AStarSearch().run(initialBoard, 2);
			break;
		case 3:
			new AStarSearch().run(initialBoard, 3);
			break;
		default:
			System.out.println("Please enter one of the choices (1-3).");
		}
	}

}
