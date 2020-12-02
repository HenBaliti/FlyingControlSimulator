package server_side;

import java.util.Comparator;
import java.util.PriorityQueue;

public abstract class CommonSearcher implements Searcher{
	protected PriorityQueue<State> openList;
	private int evaluatedNodes;

	public CommonSearcher() {
		Comparator<State> comp=new StateComparator();
		openList = new PriorityQueue<State>(comp);
		evaluatedNodes = 0;
	}
	
	final protected State popOpenList() {
		evaluatedNodes++;
		return openList.poll();
	}
	
	protected Solution backTrace(State n, State initialState) {

		Solution sol = new Solution();
		State stateNow = new State(n);
		sol.add(stateNow);

		// building the Backtrace
		while (stateNow.getCameFrom() != null) {
			stateNow = new State(stateNow.getCameFrom());
			sol.add(stateNow);
			stateNow = stateNow.getCameFrom();
		}
		return sol;

	}

	@Override
	public int getNumberOfNodesEvaluated() {
		return evaluatedNodes;
	}
	
}
