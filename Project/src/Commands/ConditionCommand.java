package Commands;

import java.util.ArrayList;
import java.util.List;

import Expression.ShuntingYard;
import Interpeter.Lexer;
import Interpeter.Parser;


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
	//check which operator is it and adding to the leftext
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
		
		while(checkCond()) {
			makeCommands(Parser.CommandForWhile);
		}
		
		return 0;
	}
	//check if the condition happend
	protected boolean checkCond() {
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
		int index=0;
		String[] arr = new String[1];
		for(int j=0;j<tokens.size();j++) {
			arr[0] = tokens.get(j);
			Lexer lexer = new Lexer();
			ArrayList<String> tokensCommand = lexer.lexer(arr);
			
			if(Utilities.isSymbolExist(tokensCommand.get(index))) {//if there is a symbol like this already
			int TempInd = index;
			index+=2;
			///////////////////////////////////////////////////
			String leftExpS="";
			String rightExpS="";
			String operatorS = null;
			double ans = 0;
			int indexBB = index;
			boolean flag = true;
			while(flag) {
				switch(tokensCommand.get(indexBB)) {
				case "+":	flag = false; operatorS="+"; break;
				
				case "-":	flag = false; operatorS="-"; break;	

				case "*":	flag = false; operatorS="*"; break;

				case "/":	flag = false; operatorS="/"; break;
				 
				default: 	leftExpS+=tokensCommand.get(indexBB); 
				indexBB++;
							break;
				}
				}
			
			while(true) {
				indexBB++;
				if(indexBB==tokensCommand.size()) {		
					break;
				}
				rightExpS+=tokensCommand.get(indexBB); 
			}
			
			double leftResultS = ShuntingYard.calc(leftExpS.toString());
			double rightResultS = ShuntingYard.calc(rightExpS.toString());
				
			switch (operatorS) {
			case "+": ans = (leftResultS + rightResultS); break;
			case "-": ans = (leftResultS - rightResultS); break;
			case "/": ans = (leftResultS / rightResultS); break;
			case "*": ans = (leftResultS * rightResultS); break;
			}
			
			
			//putting in symbolTable the ans
			Utilities.symbolTable.get(tokensCommand.get(TempInd)).setV(ans);
			index=0;
			////////////////////////////////////////////////////
			
		}
			
			
		}
	}
	

}
