package server_side;

import java.util.Objects;

public class State {
	protected String state;

	private double cost;
	private double totalCost;
	private State cameFrom;
	private int rowS,colS;

	public State(String state) {
		this.state = state;
	}
	
	public State(State s1) {
		this.cameFrom = s1.getCameFrom();
		this.state = s1.getState();
		this.cost = s1.getCost();
		this.totalCost = s1.getTotalCost();
	}

	public boolean equals(State s) {
		return this.colS==s.colS && this.rowS==s.rowS;
	}

	public String getState() {
		return state;
	}

	public void setState(String state) {
		this.state = state;
	}

	public double getCost() {
		return cost;
	}

	public void setCost(double cost) {
		this.cost = cost;
	}

	public State getCameFrom() {
		return cameFrom;
	}

	public void setCameFrom(State cameFrom) {
		this.cameFrom = cameFrom;
	}

	@Override
	public int hashCode() {
		return Objects.hash(rowS,colS);
		
	}

	@Override
	public String toString() {
		return "State [state=" + state + ", cost=" + cost + ", cameFrom=" + cameFrom + "]";
	}

	public int getColS() {
		return colS;
	}

	public void setColS(int colS) {
		this.colS = colS;
	}

	public int getRowS() {
		return rowS;
	}

	public void setRowS(int rowS) {
		this.rowS = rowS;
	}
	public double getTotalCost() {
		return totalCost;
	}

	public void setTotalCost(double totalCost) {
		this.totalCost = totalCost;
	}



}
