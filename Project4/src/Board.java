import java.util.Arrays;
import java.util.ArrayList;
import java.util.List;

public class Board {
	private int[][] matrix;
	private int N;

	public Board(int[][] blocks) {
		// construct a board from an N-by-N array of blocks
		N = blocks.length;
		matrix = new int[N][N];
		for (int i = 0; i < N; i++) {
			for (int j = 0; j < N; j++) {
				matrix[i][j] = blocks[i][j];
			}
		}
	}

	public int dimension() {
		// board dimension N
		return N;
	}

	public int hamming() {
		// number of blocks out of place
		int count = 0;
		for (int i = 0; i < N * N - 1; i++) {
			int val = matrix[i / N][i % N];
			if (val != i + 1)
				count++;
		}
		return count;
	}

	public int manhattan() {
		// sum of Manhattan distances between blocks and goal
		int totalDistance = 0;
		for (int i = 0; i < N; i++) {
			for (int j = 0; j < N; j++) {
				if (matrix[i][j] != 0) {
					int row = (matrix[i][j] - 1) / N;
					int col = (matrix[i][j] - 1) % N;
					totalDistance += Math.abs(i - row) + Math.abs(j - col);
				}
			}
		}
		return totalDistance;
	}

	public boolean isGoal() {
		// is this board the goal board?
		for (int i = 0; i < N - 1; i++) {
			for (int j = 0; j < N; j++) {
				if (matrix[i][j] != i * N + j + 1) {
					return false;
				}
			}
		}
		for (int j = 0; j < N - 1; j++) {
			if (matrix[N - 1][j] != (N - 1) * N + j + 1) {
				return false;
			}
		}
		return true;
	}

	private int[][] clone(int[][] blocks, int dimention) {
		int[][] newBlocks = new int[dimention][dimention];
		for (int i = 0; i < dimention; i++) {
			for (int j = 0; j < dimention; j++) {
				newBlocks[i][j] = blocks[i][j];
			}
		}
		return newBlocks;
	}

	public Board twin() {
		// a board obtained by exchanging two adjacent blocks in the same row
		for (int i = 0; i < N; i++) {
			for (int j = 0; j < N; j++) {
				if (matrix[i][j] != 0 && j < (N - 1) && matrix[i][j + 1] != 0) {
					int[][] newMatrix = clone(matrix, N);
					int tmp = newMatrix[i][j];
					newMatrix[i][j] = newMatrix[i][j + 1];
					newMatrix[i][j + 1] = tmp;
					return new Board(newMatrix);
				}
			}
		}
		throw new IllegalStateException("Impossible to twin the Board "
				+ toString());
	}

	@Override
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
		Board that = (Board) y;
		if (N != that.N) {
			return false;
		}
		if (!Arrays.deepEquals(matrix, that.matrix)) {
			return false;
		}
		return true;
	}

	public Iterable<Board> neighbors() {
		// all neighboring boards
		int i = 0;
		int j = 0;
		boolean found = false;
		for (i = 0; i < N; i++) {
			for (j = 0; j < N; j++) {
				if (matrix[i][j] == 0) {
					found = true;
					break;
				}
			}
			if (found) {
				break;
			}
		}
		List<Board> neighbors = new ArrayList<Board>();
		int[][] newMatrix = clone(matrix, N);
		if (j > 0) {
			newMatrix[i][j] = newMatrix[i][j - 1];
			newMatrix[i][j - 1] = 0;
			neighbors.add(new Board(newMatrix));
			newMatrix[i][j - 1] = newMatrix[i][j];
			newMatrix[i][j] = 0;
		}
		if (j < N - 1) {
			newMatrix[i][j] = newMatrix[i][j + 1];
			newMatrix[i][j + 1] = 0;
			neighbors.add(new Board(newMatrix));
			newMatrix[i][j + 1] = newMatrix[i][j];
			newMatrix[i][j] = 0;
		}
		if (i > 0) {
			newMatrix[i][j] = newMatrix[i - 1][j];
			newMatrix[i - 1][j] = 0;
			neighbors.add(new Board(newMatrix));
			newMatrix[i - 1][j] = newMatrix[i][j];
			newMatrix[i][j] = 0;
		}
		if (i < N - 1) {
			newMatrix[i][j] = newMatrix[i + 1][j];
			newMatrix[i + 1][j] = 0;
			neighbors.add(new Board(newMatrix));
			newMatrix[i + 1][j] = newMatrix[i][j];
			newMatrix[i][j] = 0;
		}
		return neighbors;
	}

	public String toString() {
		StringBuilder s = new StringBuilder();
		s.append(N + "\n");
		for (int i = 0; i < N; i++) {
			for (int j = 0; j < N; j++) {
				s.append(String.format("%2d ", matrix[i][j]));
			}
			s.append("\n");
		}
		return s.toString();
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

		System.out.println("Hamming: " + initial.hamming());
		System.out.println("Manhattan: " + initial.manhattan());
		System.out.println("toString:\n" + initial.toString());
		System.out.println("Twin:\n" + initial.twin().toString());
		List<Board> set = (List<Board>) initial.neighbors();
		for (Board b : set) {
			System.out.println(b);
		}

	}
}
