package Interpeter;

import java.util.HashMap;
import java.util.Map;

public class Utilities {
	
	public static Map<String,SymbolTabelObject> symbolTable = new HashMap<String,SymbolTabelObject>();
	public static Map<String,Command> CommandsHash = new HashMap<String,Command>();
	
	public static SymbolTabelObject getSymbolTableValue(String symbol) {
		return symbolTable.getOrDefault(symbol, null);
	}
	
	public static boolean isSymbolExist(String symbol) {
		return symbolTable.containsKey(symbol);
	}
	
	public static void setSymbolTable(String symbolName,SymbolTabelObject stVar) {
		symbolTable.put(symbolName, stVar);
	}
	
	
	public static Command getCommand(String CommandName) {
		return CommandsHash.getOrDefault(CommandName, null);
	}
	
	public static boolean isCommandExist(String CommandName) {
		return CommandsHash.containsKey(CommandName);
	}
	
	public static void setCommand(String CommandName,Command cmd) {
		CommandsHash.put(CommandName, cmd);
	}
}
