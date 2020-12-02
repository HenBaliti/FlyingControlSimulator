package server_side;

import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Deque;
import java.util.LinkedList;
import java.util.List;

public class MatrixProb implements Searchable {

	private int[][] matrixStr;
	private State GoalState;
	private State initState;
	protected State[][] matrixStates;

	
	public MatrixProb() {
		matrixStr=null;
		GoalState=null;
		initState=null;
	}

	public MatrixProb(int[][] prob) {
		this.matrixStr = prob;
		matrixStates = new State[matrixStr.length][matrixStr[0].length];
		for (int i = 0; i < matrixStr.length; i++) {
			for (int j = 0; j < matrixStr[i].length ; j++) {
				matrixStates[i][j] = new State(i + "," + j);
				matrixStates[i][j].setRowS(i);
				matrixStates[i][j].setColS(j);
				matrixStates[i][j].setCost(matrixStr[i][j]);
			}
		}
	}

	@Override
	public State getInitialState() {
		return this.initState;
	}

	// Returning if this is the goalState
	@Override
	public boolean isGoalState(State s) {
		State goalState = getGoalState();
		return s.equals(goalState);
	}

	@Override
	public List<State> getAllPossibleStates(State s) {

		ArrayList<State> PossibleStatesList;

		String[] arrState = (s.getState().toString()).split(","); 
		int row = Integer.parseInt(arrState[0]);
		int col = Integer.parseInt(arrState[1]);
		State Up=null,Down=null,Left=null,Right=null;
		//default State
		if(row!=0 && row!=this.matrixStates.length-1 && col!=0 && col!=this.matrixStates[0].length - 1) {
			Up=this.matrixStates[row-1][col];
			Left=this.matrixStates[row][col-1];
			Right=this.matrixStates[row][col+1];
			Down=this.matrixStates[row+1][col];
		}
		//If the state is [0][0]
		if(row==0&&col==0) {
			Right = this.matrixStates[row][col+1];
			Down = this.matrixStates[row+1][col];
		}
		//If the state is [n][n]
		if(row==this.matrixStates.length-1&&col==this.matrixStates[0].length-1) {
			Left = this.matrixStates[row][col-1];
			Up = this.matrixStates[row-1][col];
		}
		//Top Regular State
		if(row==0&&col!=0&&col!=this.matrixStates[0].length-1) {
			Left = this.matrixStates[row][col-1];
			Down = this.matrixStates[row+1][col];
			Right = this.matrixStates[row][col+1];
		}
		//Left Regular State
		if(col ==0 && row!=0 && row!=this.matrixStates.length-1) {
			Right=this.matrixStates[row][col+1];
			Down=this.matrixStates[row+1][col];
			Up=this.matrixStates[row-1][col];
		}
		//Top Bottom State
		if(row == this.matrixStates.length-1 && col!=0 && col!=this.matrixStates[0].length-1) {
			Right=this.matrixStates[row][col+1];
			Up=this.matrixStates[row-1][col];
			Left=this.matrixStates[row][col-1];
		}
		//Right Regular State
		if(col == this.matrixStates[0].length - 1 && row!=0 && row!=this.matrixStates.length-1) {
			Up=this.matrixStates[row-1][col];
			Left=this.matrixStates[row][col-1];
			Down=this.matrixStates[row+1][col];
		}
		//Top Right State
		if(col == this.matrixStates[0].length -1 && row == 0) {
			Left=this.matrixStates[row][col-1];
			Down=this.matrixStates[row+1][col];
		}
		//Bottom Left State
		if(row == this.matrixStates.length -1 && col == 0) {
			Right = this.matrixStates[row][col+1];
			Up = this.matrixStates[row-1][col];
		}
		
		PossibleStatesList = new ArrayList<State>();
		State[] PosState = {Right,Left,Up,Down};
		for (State ss : PosState) {
			if(ss!=null) {
				PossibleStatesList.add(ss);
			}
		}
		return PossibleStatesList;
		
	}

	public State getGoalState() {
		return GoalState;
	}

	public void setGoalState(String goalState) {
		String[] goal=goalState.split(",");
		int row,col;
		row=Integer.parseInt(goal[0]);
		col=Integer.parseInt(goal[1]);
		this.GoalState=this.matrixStates[row][col];
	}
	public void setInitlState(String initlState) {
		String[] Init=initlState.split(",");
		int row,col;
		row=Integer.parseInt(Init[0]);
		col=Integer.parseInt(Init[1]);
		this.initState=this.matrixStates[row][col];
	}


	public State getInitlState() {
		return this.initState;
	}
	
	
	// Returns array of string with the directions of solution.
	public String[] getDirection(Deque<State> pathStates) {
		
		LinkedList<String> path = new LinkedList<>();
		
		for(State p : pathStates) {
			
			if(p.getCameFrom() == null) {
				break;
			}
			
			if(p.getColS() > p.getCameFrom().getColS()) {
				path.add("Right");
			}
			
			if(p.getColS() < p.getCameFrom().getColS()) {
				path.add("Left");
			}
			
			if(p.getRowS() > p.getCameFrom().getRowS()) {
				path.add("Down");
			}
			
			if(p.getColS() < p.getCameFrom().getColS()) {
				path.add("Up");
			}
			
		}
		
		Collections.reverse(path);
		return (String[]) path.toArray(new String[0]);
	}
	
	
	@Override
	public String toString() {
		return "MatrixStates : InitState= " + initState + ", Goalstate= " + GoalState +  "Matrix= "
				+ Arrays.toString(matrixStr);
	}

}