package Commands;

import java.util.ArrayList;
import java.util.List;

import Expression.ShuntingYard;


public class ConditionCommand implements Command{

	int numOfArgs;
	protected String leftExp="";
	protected String rightExp="";
	protected String operator;
	
	public ConditionCommand() {
		super();
		this.numOfArgs = 0;
		this.operator = null;
		
	}
	@Override
	public int doCommand(List<String> tokens) {
		// TODO Auto-generated method stub
		int index = 0;
		boolean flag = true;
		while(flag) {
			switch(tokens.get(index)) {
			case "<":	flag = false; operator="<"; break;
			
			case ">":	flag = false; operator=">"; break;	

			case "=":	flag = false; operator="="; break;

			case "!":	flag = false; operator="!"; break;
			 
			default: 	this.leftExp+=tokens.get(index); 
						index++;
						break;
			}
			}
		
		while(true) {
			index++;
			this.rightExp+=tokens.get(index); 
			if(index==tokens.size()-1) {		
				break;
			}
		}
		
		while(checkCondition()) {
						
		}
		
		return 0;
	}
	protected boolean checkCondition() {
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
	
	public static void makeCommands(List<String> tokens) {
		
	}
	@Override
	public void testArgs(String args) {
		// TODO Auto-generated method stub
		
	}

}
