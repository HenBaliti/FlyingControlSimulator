package Commands;

import java.util.ArrayList;
import java.util.List;

import Expression.ShuntingYard;
import Interpeter.SymbolTabelObject;
import Model.SimulatorClient;

//getting all the var commands in this class
public class DefineVarCommand implements Command{

	public Utilities ut;
	int numOfArgs;
	public DefineVarCommand() {
		super();
	}
	@Override
	public int doCommand(List<String> tokens, Utilities ut) {
		this.ut = ut;
		String symbolName = tokens.get(0);
		SymbolTabelObject symbolNew = null;
		
		//Checking if its already in HashSymbol
		if(ut.isSymbolExist(symbolName)) {
			
			if(tokens.get(1).equals("=")) {
				if(tokens.get(2).equals("bind")) { //x = bind simX
					ut.symbolTable.get(tokens.get(0)).setSIM(tokens.get(3));
					SymbolTabelObject dd = ut.symbolTable.get(symbolName);
//					Utilities.symbolTableSim.put(tokens.get(3), dd);
					numOfArgs = 3;
				}
				else {// x = 562
					if(tokens.get(3).equals("%%")) {
						double dd = ShuntingYard.calc(tokens.get(2));
//						d = Utilities.symbolTable.get(symbolName);
//						d.setV(dd);
						numOfArgs = 1;
					}else {/////////////// breaks = 0
						double dd = ShuntingYard.calc(tokens.get(2));
						symbolNew = ut.symbolTable.get(tokens.get(0));
						symbolNew.setV(dd);
						numOfArgs = 2;
					}
				}
				
			}
			
		}
		else { 
			if(tokens.get(1).contains("=")) {
				//Checking if its contatins "="
				String[] ary = tokens.get(1).split("=");
				ArrayList<String> stamList = new ArrayList<String>();
					if(ut.isSymbolExist(ary[0])){
						
					}
					else {
						
					}
					stamList.add("=");
					stamList.add(ary[1]);
					ShuntingYard.calc(tokens.get(1));
				numOfArgs = 2;
			}
			else {//Defaults
				if(tokens.get(3).equals("bind")) {//var y=bind simX
					
					SymbolTabelObject stNewShort = new SymbolTabelObject(tokens.get(1));
					SymbolTabelObject stNewLong = new SymbolTabelObject(tokens.get(4));
					stNewShort.addObserver(stNewLong);
					stNewLong.addObserver(stNewShort);

					ut.symbolTable.put(tokens.get(4), stNewShort);
					ut.symbolTable.put(tokens.get(1), stNewLong);
					numOfArgs = 4;
				}
				else if (tokens.get(2).equals("=")){ //var x =  0 // -> Also var h0 = heading
					double dd = ShuntingYard.calc(tokens.get(3));
					SymbolTabelObject stNew = new SymbolTabelObject(dd,tokens.get(1));
					ut.symbolTable.put(tokens.get(1),stNew);
					numOfArgs = 3;
					
				}
			}
		}
		return numOfArgs;
	}


}