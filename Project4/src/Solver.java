import java.util.Collections;
import java.util.List;
import java.util.ArrayList;

public class Solver {
	private SearchNode goalNode;

	public Solver(Board initial) {
		// find a solution to the initial board (using the A* algorithm)
		MinPQ<SearchNode> minPQ = new MinPQ<SearchNode>();
		MinPQ<SearchNode> twinPQ = new MinPQ<SearchNode>();
		minPQ.insert(new SearchNode(initial, null, 0));
		twinPQ.insert(new SearchNode(initial.twin(), null, 0));
		SearchNode currNode = minPQ.delMin();
		SearchNode twinNode = twinPQ.delMin();
		while (!currNode.isGoal() && !twinNode.isGoal()) {
			Iterable<SearchNode> currNeighbors = currNode.neighbors();
			for (SearchNode node : currNeighbors) {
				minPQ.insert(node);
			}
			if (!minPQ.isEmpty()) {
				currNode = minPQ.delMin();
			}

			Iterable<SearchNode> twinNeighbors = twinNode.neighbors();
			for (SearchNode node : twinNeighbors) {
				twinPQ.insert(node);
			}
			if (!twinPQ.isEmpty()) {
				twinNode = twinPQ.delMin();
			}
		}
		if (currNode.isGoal()) {
			goalNode = currNode;
		} else {
			goalNode = null;
		}
	}

	public boolean isSolvable() {
		// is the initial board solvable?
		return goalNode != null;
	}

	public int moves() {
		// min number of moves to solve initial board; -1 if no solution
		if (!isSolvable()) {
			return -1;
		}
		return goalNode.getMoves();
	}

	public Iterable<Board> solution() {
		// sequence of boards in a shortest solution; null if no solution
		if (goalNode == null) {
			return null;
		}
		List<Board> solution = new ArrayList<Board>();
		SearchNode curr = goalNode;
		while (curr != null) {
			solution.add(curr.getBoard());
			curr = curr.prev;
		}
		Collections.reverse(solution);
		return solution;
	}

	private class SearchNode implements Comparable<SearchNode> {
		private SearchNode prev;
		private Board board;
		private int move;

		public SearchNode(Board board, SearchNode prev, int move) {
			this.prev = prev;
			this.move = move;
			this.board = board;
		}

		public Board getBoard() {
			return board;
		}

		public int getMoves() {
			return move;
		}

		public int compareTo(SearchNode that) {
			int m1 = this.board.manhattan() + move;
			int m2 = that.board.manhattan() + that.move;
			return m1 - m2;
		}

		public boolean isGoal() {
			return board.isGoal();
		}

		public SearchNode getPrev() {
			return prev;
		}

		public boolean equals(Object y) {
			// does this board equal y?
			if (y == this) {
				return true;
			}
			if (y == null) {
				return false;
			}
			if (y.getClass() != this.getClass()) {
				return false;
			}
			SearchNode that = (SearchNode) y;
			return this.board.equals(that.board);
		}

		public Iterable<SearchNode> neighbors() {
			Iterable<Board> neighbors = board.neighbors();
			List<SearchNode> searchNodes = new ArrayList<SearchNode>();
			for (Board neighborBoard : neighbors) {
				if (prev != null && neighborBoard.equals(prev.getBoard())) {
					continue;
				}
				searchNodes.add(new SearchNode(neighborBoard, this, move + 1));
			}
			return searchNodes;
		}

		public String toString() {
			return board.toString();
		}

	}

	public static void main(String[] args) {
		// create initial board from file
		In in = new In(args[0]);
		int N = in.readInt();
		int[][] blocks = new int[N][N];
		for (int i = 0; i < N; i++)
			for (int j = 0; j < N; j++)
				blocks[i][j] = in.readInt();
		Board initial = new Board(blocks);

		// solve the puzzle
		Solver solver = new Solver(initial);

		// print solution to standard output
		if (!solver.isSolvable())
			StdOut.println("No solution possible");
		else {
			StdOut.println("Minimum number of moves = " + solver.moves());
			for (Board board : solver.solution())
				StdOut.println(board);
		}
	}
}
