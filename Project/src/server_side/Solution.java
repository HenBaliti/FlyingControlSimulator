package server_side;

import java.util.ArrayDeque;
import java.util.Collection;
import java.util.Deque;
import java.util.Iterator;
import java.util.List;


public class Solution{
	
	// ---- A Path Deque ----
	private Deque<State> path;
	
	// Constractor for solution
	public Solution() {
		path = new ArrayDeque<State>();
	}
	
	// -- Adding a new state to the solution --
	public void add(State state)
	{
		if(state != null)
		path.add(state);
	}


	// -- Adding a new List to the solution --
	public void addList(Collection<? extends State> lst1)
	{
		this.path.addAll(lst1);
	}

	// -- ToString - printing the solution --
	public String toString()
	{
		Iterator<State> it = path.descendingIterator(); //printing the solution from the end to the start
		String sol = new String();
		while(it.hasNext())
		{
			sol+= it.next().getState().toString();
			sol += "\n";
		}
		return sol;
	}
}