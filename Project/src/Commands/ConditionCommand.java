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
			makeCommands(Parser.CommandForWhile);
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
	
//	public void makeCommands(List<String> tokens) {
//		int index=0;
//		Command cmdStam;
//		while(index<tokens.size()&&tokens.get(index)!=null) {
//			if(Utilities.isCommandExist(tokens.get(index)))//if there is a command like this
//			{
//				cmdStam = (Command) Utilities.getCommand(tokens.get(index));
//				ExpressionCommand cmdEx = new ExpressionCommand(cmdStam);
//				
//				//To get the new ArrayList from the index i want to the index i want.
//				List<String> subArray = tokens.subList(index, tokens.size());
//				cmdEx.setS(subArray);
//				index+=cmdEx.calculate();
//			}
//			else {
//				if(Utilities.isSymbolExist(tokens.get(index))) {//if there is a symbol like this already
//					int TempInd = index;
//					index+=2;
//					///////////////////////////////////////////////////
//					String leftExpS="";
//					String rightExpS="";
//					String operatorS = null;
//					double ans = 0;
//					int indexBB = index;
//					boolean flag = true;
//					while(flag) {
//						switch(tokens.get(indexBB)) {
//						case "+":	flag = false; operatorS="+"; break;
//						
//						case "-":	flag = false; operatorS="-"; break;	
//
//						case "*":	flag = false; operatorS="*"; break;
//
//						case "/":	flag = false; operatorS="/"; break;
//						 
//						default: 	leftExpS+=tokens.get(indexBB); 
//						indexBB++;
//									break;
//						}
//						}
//					
//					while(true) {
//						indexBB++;
//						if(indexBB==tokens.size()||tokens.get(indexBB).equals("x")) {		
//							break;
//						}
//						rightExpS+=tokens.get(indexBB); 
//					}
//					
//					double leftResultS = ShuntingYard.calc(leftExpS.toString());
//					double rightResultS = ShuntingYard.calc(rightExpS.toString());
//						
//					switch (operatorS) {
//					case "+": ans = (leftResultS + rightResultS); break;
//					case "-": ans = (leftResultS - rightResultS); break;
//					case "/": ans = (leftResultS / rightResultS); break;
//					case "*": ans = (leftResultS * rightResultS); break;
//					}
//					
//					
//					//putting in symbolTable the ans
//					Utilities.symbolTable.get(tokens.get(TempInd)).setV(ans);
//					index+=3;
//					////////////////////////////////////////////////////
//					
//				}
//				else {
//					System.out.println("There is no Command like this");
//				}
//			}
//			
//		}
//	}
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
	
	@Override
	public void testArgs(String args) {
		// TODO Auto-generated method stub
		
	}

}
