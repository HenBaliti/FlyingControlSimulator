package server_side;

import java.util.List;

public interface Searchable {
	State getInitialState();
	boolean isGoalState(State s);
	List<State> getAllPossibleStates(State s);
}
