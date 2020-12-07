package Commands;

import java.util.ArrayList;
import java.util.List;

import Expression.ShuntingYard;


public class ConditionCommand implements Command{

	int numOfArgs;
	protected ArrayList<String> leftExp;
	protected ArrayList<String> rightExp;
	protected String operator;
	
	public ConditionCommand() {
		super();
		this.numOfArgs = 0;
		this.leftExp = new ArrayList<String>();
		this.rightExp = new ArrayList<String>();
		this.operator = null;
		
	}
	@Override
	public int doCommand(List<String> tokens) {
		// TODO Auto-generated method stub
		int index = 0;
		boolean flag = true;
		while(flag) {
			switch(tokens.get(index)) {
			case "<":	flag = false; break;
			
			case ">":	flag = false; break;	

			case "=":	flag = false; break;

			case "!":	flag = false; break;
			 
			default: 	this.leftExp.add(tokens.get(index)); 
						index++;
						break;
			}
			}
		
		while(true) {
			if(tokens.get(index).equals("{")) {		
				break;
			}
		}
		return 0;
	}
	protected boolean checkCondtion() {
		double leftResult = ShuntingYard.calc(leftExp.toString());
		double rightResult = ShuntingYard.calc(rightExp.toString());
			
		switch (this.operator) {
		case ">": return (leftResult > rightResult);
		case "<": return (leftResult < rightResult);
		case "==": return (leftResult == rightResult);
		case "!=": return (leftResult != rightResult);
		case "<=": return (leftResult <= rightResult);
		case ">=": return (leftResult >= rightResult);
		}
		
		return false;
	}
	
	public void makeCommands(List<String> tokens) {
		
	}
	@Override
	public void testArgs(String args) {
		// TODO Auto-generated method stub
		
	}

}
