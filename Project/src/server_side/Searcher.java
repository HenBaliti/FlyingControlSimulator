package server_side;

public interface Searcher<Sol>{
	
	public Sol search(Searchable s); 
	public int getNumberOfNodesEvaluated(); 

}
