package Commands;

import java.util.HashMap;
import java.util.Map;

import Interpeter.SymbolTabelObject;

public class Utilities {
	
	public static Map<String,SymbolTabelObject> symbolTable = new HashMap<String,SymbolTabelObject>();
	public static Map<String,Command> CommandsHash = new HashMap<String,Command>();
	
	public Utilities() {
	Utilities.CommandsHash.put("var", new DefineVarCommand());
	Utilities.CommandsHash.put("return", new ReturnCommand());
	Utilities.CommandsHash.put("var", new DefineVarCommand());
	Utilities.CommandsHash.put("connect", new ConnectCommand());
	Utilities.CommandsHash.put("disconnect", new DisconnectCommand());
	Utilities.CommandsHash.put("openDataServer", new OpenServerCommand());
	Utilities.CommandsHash.put("while", new LoopCommand());
	}
	
	public static Map<String,SymbolTabelObject> getSymbolTable() {
		return symbolTable;
	}
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
