package Commands;

import java.util.ArrayList;
import java.util.List;

import Expression.ShuntingYard;
import Interpeter.SymbolTabelObject;

public class DefineVarCommand implements Command{

	int numOfArgs;
	public DefineVarCommand() {
		super();
	}
	@Override
	public int doCommand(List<String> tokens) {
		String symbolName = tokens.get(0);
		SymbolTabelObject d = null;
		
		//Checking if its already in HashSymbol
		if(Utilities.isSymbolExist(symbolName)) {
			if(tokens.get(1)=="=") {
				double dd = ShuntingYard.calc(tokens.get(2));
				d = Utilities.symbolTable.get(symbolName);
				d.setV(dd);
				numOfArgs = 1;
			}
			
		}
		else { //If This is -> var xxxxxxx
			if(tokens.get(1).contains("=")) {
				//Checking if its contatins "="
				String[] ary = tokens.get(1).split("=");
				ArrayList<String> stamList = new ArrayList<String>();
					if(Utilities.isSymbolExist(ary[0])){
						
					}
					else {
						
					}
					stamList.add("=");
					stamList.add(ary[1]);
					ShuntingYard.calc(tokens.get(1));
//					Command newcmd = new DefineVarCommand();
//					newcmd.doCommand(stamList);

				numOfArgs = 2;
			}
			else {//Default
				d = new SymbolTabelObject();
				Utilities.symbolTable.put(tokens.get(1), d);
				numOfArgs = 1;
			}
		}
		return numOfArgs;
	}

	@Override
	public void testArgs(String args) {
		// TODO Auto-generated method stub
		
	}

}
