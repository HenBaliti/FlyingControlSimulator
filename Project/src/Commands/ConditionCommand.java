package Commands;

import java.util.ArrayList;
import java.util.List;

import Expression.ShuntingYard;
import Interpeter.Lexer;
import Interpeter.Parser;
import Interpeter.SymbolTabelObject;
import Model.SimulatorClient;


public class ConditionCommand implements Command{

	int numOfArgs;
	protected String leftExp="";
	protected String rightExp="";
	protected String operator;
	public Utilities ut;
	
	public ConditionCommand() {
		super();
		this.numOfArgs = 0;
		this.operator = null;
		
	}
	//check which operator is it and adding to the leftext
	@Override
	public int doCommand(List<String> tokens, Utilities ut) {
		this.ut = ut;
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
				
				if(tokensCommand.get(index).contains("(")) {//if its contains () in the index ++; //---> rudder = (h0 - headin)/20
					//first taking the expression in the "()"
					int tmporaryIndex = index;
					String leftExpPhrasis = tokensCommand.get(index);//"(h"
					StringBuilder leftExpPhrasisSB = new StringBuilder(leftExpPhrasis);//----> getting of the "("
					leftExpPhrasisSB.deleteCharAt(0);
					leftExpPhrasis = leftExpPhrasisSB.toString();
					tmporaryIndex++;
					String operatorOfPhrasis = tokensCommand.get(tmporaryIndex); // "-"
					tmporaryIndex++;
					String RestOfExp = tokensCommand.get(tmporaryIndex); //"heading)/20"
					tmporaryIndex++;
					RestOfExp+=tokensCommand.get(tmporaryIndex);
					tmporaryIndex++;
					RestOfExp+=tokensCommand.get(tmporaryIndex);

					String rightExpPhrasis ="";
					String rightExpLast ="";
					String operatorLast ="";
					
					for(int i=0;i<RestOfExp.length();i++) {
						if(RestOfExp.charAt(i)!=')')
							rightExpPhrasis+=RestOfExp.charAt(i); // building the right expression -> "heading"
						else {
							i++;
							if(RestOfExp.charAt(i)=='+'||RestOfExp.charAt(i)=='-'||RestOfExp.charAt(i)=='*'||RestOfExp.charAt(i)=='/') {
								operatorLast+= RestOfExp.charAt(i);
								i++;
								while(i<RestOfExp.length()) {
									rightExpLast+=RestOfExp.charAt(i);// /20
									i++;
								}
							}
						}
					}
					
					
					
					///After build the two Expression need to go to the SHUNTINGYARD for calculate
					
					//First the parasis expression
					double ansParasis = 0;
					double leftResultParasis = 0;
					//If the leftExp is in the symboltable take the VarObject
					if(ut.symbolTable.containsKey(leftExpPhrasis)){
						leftResultParasis = Utilities.symbolTable.get(leftExpPhrasis).getV();
					}
					else {
						leftResultParasis = ShuntingYard.calc(leftExpPhrasis.toString());
					}
					double rightResultParasis = ShuntingYard.calc(rightExpPhrasis.toString());
						
					switch (operatorOfPhrasis) {
					case "+": ansParasis = (leftResultParasis + rightResultParasis); break;
					case "-": ansParasis = (leftResultParasis - rightResultParasis); break;
					case "/": ansParasis = (leftResultParasis / rightResultParasis); break;
					case "*": ansParasis = (leftResultParasis * rightResultParasis); break;
					}
					
					//Now the second Expression with the pharasis answer
					double ansLast = 0;

					double rightResultLast = ShuntingYard.calc(rightExpLast.toString());
						
					switch (operatorLast) {
					case "+": ansLast = (ansParasis + rightResultLast); break;
					case "-": ansLast = (ansParasis - rightResultLast); break;
					case "/": ansLast = (ansParasis / rightResultLast); break;
					case "*": ansLast = (ansParasis * rightResultLast); break;
					}
					
					//putting in symbolTable the ans
					SymbolTabelObject symbolNew = ut.symbolTable.get(tokensCommand.get(TempInd));
					symbolNew.setV(ansLast);
					index=0;
//					SimulatorClient.out.println("set " + Utilities.symbolTable.get(tokensCommand.get(TempInd)).getSIM() + " " + ansLast);
//					System.out.println("set " + Utilities.symbolTable.get(tokensCommand.get(TempInd)).getSIM() + " " + ansLast);
					////////////////////////////////////////////////////666666666666666666666666666666------------------------here
					
				}
				else if(tokensCommand.get(index).equals("-")){ // If There is a "-" on the index ->2
					int indexMinus = index;
					index++;
					String leftMinus = tokensCommand.get(index);
					index++;
					String operatorMinus = tokensCommand.get(index);
					index++;
					String rightMinus = tokensCommand.get(index);
					double leftResultMinus = 0;
					double ansMinus = 0;
					
					
					//If the leftExp is in the symboltable take the VarObject
					if(ut.symbolTable.containsKey(leftMinus)){
						leftResultMinus = ut.symbolTable.get(leftMinus).getV();
					}
					else {
						leftResultMinus = ShuntingYard.calc(leftMinus.toString());
					}
					double rightResultMinus = ShuntingYard.calc(rightMinus.toString());
						
					switch (operatorMinus) {
					case "+": ansMinus = (leftResultMinus + rightResultMinus); break;
					case "-": ansMinus = (leftResultMinus - rightResultMinus); break;
					case "/": ansMinus = (leftResultMinus / rightResultMinus); break;
					case "*": ansMinus = (leftResultMinus * rightResultMinus); break;
					}
					
					//putting in symbolTable the ans
					
					SymbolTabelObject symbolNew2 = ut.symbolTable.get(tokensCommand.get(TempInd));
					symbolNew2.setV(ansMinus);
					index=0;
					
//					Utilities.symbolTable.get(tokensCommand.get(TempInd)).setV(ansMinus);
//					index=0;
//					SimulatorClient.out.println("set " + Utilities.symbolTable.get(tokensCommand.get(TempInd)).getSIM() + " " + ansMinus);
//					System.out.println("set " + Utilities.symbolTable.get(tokensCommand.get(TempInd)).getSIM() + " " + ansMinus);
					////////////////////////////////////////////////////666666666666666666666666666666------------------------here
					
					
				}else {
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
					ut.symbolTable.get(tokensCommand.get(TempInd)).setV(ans);
					index=0;
					////////////////-----------------------------6666666666666666666666666666666666666-----hereee
					////////////////////////////////////////////////////
					
				}
			}else if(ut.isCommandExist(tokensCommand.get(index))) {//if there is a command like this in the command hash -> do the command
				Command cmdStam = (Command) ut.getCommand(tokensCommand.get(index).toString());
				ExpressionCommand cmdEx = new ExpressionCommand(cmdStam,ut);
				
				//To get the new ArrayList from the index i want to the index i want.
				List<String> subArray = tokensCommand.subList(index, tokensCommand.size());
				cmdEx.setS(subArray);
				cmdEx.calculate();
				
			}
			
			
		}
	}
	

}
