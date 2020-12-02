package server_side;

import java.util.HashSet;
import java.util.Iterator;
import java.util.List;

public class BFS extends CommonSearcher {
	
	public BFS() {
		super();
	}
	@Override
	public Solution search(Searchable s) {

		openList.add(s.getInitialState());
		HashSet<State> closedSet = new HashSet<State>();

		while (openList.size() > 0) {
			State n = popOpenList();
			closedSet.add(n);

			if (s.isGoalState(n))
				return backTrace(n, s.getInitialState());

			List<State> succesors = s.getAllPossibleStates(n);
			for (State state : succesors) {
				if (!closedSet.contains(state) && !openList.contains(state)) {
					state.setCameFrom(n);
					state.setTotalCost(n.getTotalCost() + state.getCost());
					addToOpenList(state);
				}  else if (state.getTotalCost() > state.getCost() + n.getTotalCost()) {
					state.setTotalCost(state.getCost() + n.getTotalCost());
					state.setCameFrom(n);
					if (!openList.contains(state)) {
						openList.add(state);
					} else {// Update priority queue
						State x = popOpenList();
						openList.add(x);
					}

				}
			}
		}
		
		System.out.println("There is no solution to the Maze .....");
		return null;
	}

	private boolean openListContains(State state) {
		return this.openList.contains(state);
	}

	private void addToOpenList(State state) {
		this.openList.add(state);

	}

}