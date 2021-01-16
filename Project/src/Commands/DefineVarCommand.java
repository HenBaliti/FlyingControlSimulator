package Commands;

import java.util.ArrayList;
import java.util.List;

import Expression.ShuntingYard;
import Interpeter.SymbolTabelObject;
import Model.SimulatorClient;

//getting all the var commands in this class
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
			
			if(tokens.get(1).equals("=")) {
				if(tokens.get(2).equals("bind")) { //x = bind simX
					Utilities.symbolTable.get(tokens.get(0)).setSIM(tokens.get(3));
					SymbolTabelObject dd = Utilities.symbolTable.get(symbolName);
					Utilities.symbolTableSim.put(tokens.get(3), dd);
					numOfArgs = 3;
				}
				else {// x = 562
					if(tokens.get(3).equals("%%")) {
						double dd = ShuntingYard.calc(tokens.get(2));
						d = Utilities.symbolTable.get(symbolName);
						d.setV(dd);
						numOfArgs = 1;
					}else {
						double dd = ShuntingYard.calc(tokens.get(2));
						d = Utilities.symbolTable.get(symbolName);
						d.setV(dd);//-------------------------------------^666666666666666666666666666666666666666666666666666666666666666666
						SimulatorClient.out.println("set " + Utilities.symbolTableSim.get(d.getSIM()) + " " + dd);
						System.out.println("set " + Utilities.symbolTableSim.get(d.getSIM()) + " " + dd);
						Utilities.symbolTableSim.get(d.getSIM()).setV(dd);
						numOfArgs = 2;
					}
				}
				
			}
			
		}
		else { //If This is -> var xxxxxxx
//			if(tokens.get(1).length() > 3) {
//				double d1;
//				StringBuilder s = new StringBuilder();
//				s.append(tokens.get(1).toString());
//				String s1 = new String();
//				for(int i=0;i<s.length(); i++) {
//					if(s.charAt(i) != '=') {
//						s1+=s.toString().charAt(i);
//							break;
//					}
//				}
//				Utilities.symbolTable.put(s1, new SymbolTabelObject());
//				d1 = ShuntingYard.calc(s.toString());
//				Utilities.symbolTable.get(s1).setV(d1);
//				numOfArgs = 1;
//			}
//			else
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
				numOfArgs = 2;
			}
			else {//Defaults
				if(tokens.get(3).equals("bind")) {//var y=bind simX
					if(Utilities.symbolTableSim.containsKey(tokens.get(4))) {//If there is already simX in the STSIM
						d = Utilities.symbolTableSim.get(tokens.get(4));
						Utilities.symbolTable.put(tokens.get(1), d);
					}
					else {
					d = new SymbolTabelObject();
					Utilities.symbolTable.put(tokens.get(1), d);
					Utilities.symbolTableSim.put(tokens.get(4), d);
					Utilities.symbolTable.get(tokens.get(1)).setSIM(tokens.get(4));
					}
					numOfArgs = 4;
				}
				else if (tokens.get(2).equals("=")){ //var - x - = - 0
					d = new SymbolTabelObject();
					double dd = ShuntingYard.calc(tokens.get(3));
					d.setV(dd);
					Utilities.symbolTable.put(tokens.get(1),d);
					numOfArgs = 3;
					
				}
				else {//Default var x=?
				d = new SymbolTabelObject();
				Utilities.symbolTable.put(tokens.get(1), d);
				numOfArgs = 1;
				}
			}
		}
		return numOfArgs;
	}


}