package Interpeter;

import java.util.ArrayList;
import java.util.List;
import Commands.Command;
import Commands.ConditionCommand;
import Commands.DefineVarCommand;
import Commands.ExpressionCommand;
import Commands.OpenServerCommand;
import Commands.Utilities;
import Expression.ShuntingYard;


public class Parser implements ParserInterface {
	
	public static int returnedValue;
	public static ArrayList<String> arrBodyWhile = new ArrayList<>();
	public static ArrayList<String> CommandForWhile = new ArrayList<>();
	public Utilities ut;

	@Override
	public void parse(String[] lines) {

		ArrayList<String> errorsList = new ArrayList<>();
		int startIndexCond=0;
		int endIndexCond=0;
		//Searching for "While" to see if its like test 5
		for(String strWh:lines) {
			if(strWh.contains("while")) {
				for(int j=0;j<lines.length;j++) {
					if(lines[j].contains("{")) {
						startIndexCond=j+1;
					}
					if(lines[j].contains("}")) {
						endIndexCond=j-1;
					}
				}
				for(int i=startIndexCond;i<=endIndexCond;i++) {
					CommandForWhile.add(lines[i]);
				}
			}
			
		}
		
		
		Lexer lexer = new Lexer();
		ArrayList<String> tokens = lexer.lexer(lines);
		int IndexNumRow = 0;
		int index = 0;
		String cmdName;
		Command cmdStam;


		while(index!=tokens.size()) {
			IndexNumRow++; // Counting the rows of the "Code"
			cmdName = tokens.get(index);
			
			//////////////////
			// While - Condition
			//////////////////
			int TempIndex = index;
			ArrayList<String> TempTokens = tokens;
			ArrayList<String> ConditionWhile = new ArrayList<>();
			String strr = "";
			
			if(cmdName.equals("while")) {
				//Searching for opening {
				TempIndex++; // 
				while(!tokens.get(TempIndex).equals("{")) {
					ConditionWhile.add(TempTokens.get(TempIndex));
					strr+=TempTokens.get(TempIndex);
					TempIndex++;
				}
				//Searching for closing }
				TempIndex++; // 
				while(!tokens.get(TempIndex).equals("}")) {
					arrBodyWhile.add(TempTokens.get(TempIndex));
					TempIndex++;
				}
				
				cmdStam = (Command) Utilities.getCommand("while");
				ExpressionCommand cmdEx = new ExpressionCommand(cmdStam,ut);
				
				//To get the new ArrayList from the index i want to the index i want.
				cmdEx.setS(ConditionWhile);
				index = TempIndex+1;
				index+=cmdEx.calculate();
				
				
			}
			//////////////////
			else {
				//Checking if its contatins "="
				String[] ary = cmdName.split("=");
				ArrayList<String> stamList = new ArrayList<String>();
				if(cmdName.contains("=")) {
					stamList.add(ary[0]);
					stamList.add("=");
					stamList.add(ary[1]);
					stamList.add("%%");
					Command newcmd = new DefineVarCommand();
					index+=newcmd.doCommand(stamList,ut);
					
				}
				
				else {
					// Checking if the Command is not exist in the command hash
					if (!(Utilities.isCommandExist(cmdName))) {
						if(Utilities.isSymbolExist(cmdName)) { //Checking if its a Symbol And not A command at all.
							ExpressionCommand cmdEx = new ExpressionCommand(new DefineVarCommand(),ut);
							//To get the new ArrayList from the index i want to the index i want.
							List<String> subArray = tokens.subList(index, tokens.size());
							cmdEx.setS(subArray);
							index += cmdEx.calculate()+1;
						}else {
						errorsList.add("In Line " + IndexNumRow + " Command is Not Valid!/n");
						IndexNumRow++;
						index++;
						}
					}
					else {
						cmdStam = (Command) Utilities.getCommand(cmdName);
						ExpressionCommand cmdEx = new ExpressionCommand(cmdStam,ut);
						
						//To get the new ArrayList from the index i want to the index i want.
						List<String> subArray = tokens.subList(index, tokens.size());
						cmdEx.setS(subArray);
							

					
					 if(cmdName.equals("return")) {
							if(cmdEx.calculate()==0) {
								break;
							}
						}
						else {
						index+= cmdEx.calculate()+1;
						}
					}
				}
			}
			
		}

	}
}