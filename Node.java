public class Node{
	private AStarState currentState;
	private Node parent;
	private double actualCost; // g(n)
	private double heuristicCost; // heuristic cost / h(n)
	private double fCost; // f(n)cost

	/**
	 * Constructor for Node
	 */
	public Node(AStarState s){
		currentState = s;
		parent = null;
		actualCost = 0;
		heuristicCost = 0;
		fCost = 0;
	}

	/**
	 * Constructor with parameters
	 */
	public Node(Node parent, AStarState s, double c, double h){
		this.parent = parent;
		currentState = s;
		actualCost = c;
		heuristicCost = h;
		fCost = actualCost + heuristicCost;
	}

	public Node(Node parent, AStarState s, double c){
		this.parent = parent;
		currentState = s;
		actualCost = c;
		heuristicCost = 0;
		fCost = actualCost + heuristicCost;
	}

	/**
	 * returns the parent
	 */
	public Node getParent(){
		return parent;
	}

	/**
	 * returns the value of currentState
	 */
	public AStarState getCurrentState(){
		return currentState;
	}

	/**
	 * returns the actualCost g(n)
	 */
	public double getActualCost(){
		return actualCost;
	}

	/**
	 * returns the heuristic cost
	 */
	public double getHeuristicCost(){
		return heuristicCost;
	}

	/**
	 * returns the f(n) cost
	 */
	public double getFCost(){
		return fCost;
	}
}
