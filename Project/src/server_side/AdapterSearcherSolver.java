package server_side;

public class AdapterSearcherSolver<Pro, Sol> implements Solver<Pro, Sol> {

	private Searcher search1;

	public AdapterSearcherSolver(Searcher s1) {
		this.search1 = s1;
	}


	@Override
	public Sol solve(Pro p1) {
		return (Sol)this.search1.search((Searchable)p1);
	}
}