package server_side;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.net.Socket;
import java.net.UnknownHostException;
import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Deque;
import java.util.List;

public class MyClientHandler<Pro,Sol> implements ClientHandler {

	CacheManager cm;
	Solver solver;

	public MyClientHandler(Solver<Pro,Sol> s, FileCacheManager<Pro,Sol> fileCacheManager) {
		// TODO Auto-generated constructor stub
		this.solver = s;
		this.cm = fileCacheManager;
	}

	@Override
	public void handleClient(InputStream inFromClient, OutputStream outToClient)  {
		// TODO Auto-generated method stub
		BufferedReader userInput = new BufferedReader(new InputStreamReader(inFromClient));
		PrintWriter outClient = new PrintWriter(outToClient);

		readInputAndSend(userInput, outClient);

	}

	// Function to read and write from a buffer
	private void readInputAndSend(BufferedReader in, PrintWriter out){
		String solutionMat = null;
		try {

			// Making the matrix - intMatrix
			String line;
			ArrayList<String[]> linesList = new ArrayList<>();
			
			while (!(line = in.readLine()).equals("end")) {
				linesList.add(line.split(","));
			}
			int j = 0;
			int[][] Mat = new int[linesList.size()][];
			for (int i = 0; i < Mat.length; i++) {
				String[] tmp = linesList.get(i);
				Mat[i] = new int[tmp.length];
				for (String s : tmp) {
					Mat[i][j] = Integer.parseInt(s);
					j++;
				}
				j = 0;
			}

			// Using the intMatrix and getting two extra Row - Starting position and Ending
			// position


			MatrixProb m = new MatrixProb(Mat);
//			Searcher searcher=new BFS();
//			solver=new AdapterSearcherSolver<>(searcher);
//			cm = new FileCacheManager<>();
			m.setInitlState(in.readLine());
			m.setGoalState(in.readLine());

			if (this.cm.existSolution(m.toString())) {
				// if the solution is available - return it
				System.out.println("We found the object in cache");
				solutionMat = this.cm.loadSolution(m.toString()).toString();
			} else {
				// create the solution
				Deque<State> path = new ArrayDeque<State>();
//				List<State> path = new ArrayList<State>();
				Solution sol = new Solution();
//				path.addAll((Collection<? extends State>) solver.solve(m));
				sol = (Solution) solver.solve(m);
//				String[] directionList = m.getDirection(path);
				
//				solutionMat = (String)String.join(",", directionList);
				
//				solutionMat = (String) solver.solve(m);
				// insert the new solution into the cache manager
				System.out.println("New solution Added to the cache");
//				this.cm.store(m.toString(), solutionMat);
			}
			// writing the solution to the output
//			out.println(solutionMat);

			System.out.println(solutionMat);
			out.flush();

		} catch (IOException e) {
			e.printStackTrace();
		}
	}
}