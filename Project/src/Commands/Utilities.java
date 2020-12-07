package Commands;

import java.util.HashMap;
import java.util.Map;

import Interpeter.SymbolTabelObject;

public class Utilities {
	public static Map<String,SymbolTabelObject> symbolTable = new HashMap<String,SymbolTabelObject>();
	public static Map<String,ExpressionCommand> CommandsHash = new HashMap<String,ExpressionCommand>();
	public static Map<String,SymbolTabelObject> symbolTableSim = new HashMap<String,SymbolTabelObject>();
	public static Map<String,Command> CommandsHash2 = new HashMap<String,Command>();
	
	public Utilities() {
		Utilities.CommandsHash.put("return",new ExpressionCommand( new ReturnCommand()));
		Utilities.CommandsHash.put("var",new ExpressionCommand( new DefineVarCommand()));
		Utilities.CommandsHash.put("connect",new ExpressionCommand( new ConnectCommand()));
		Utilities.CommandsHash.put("disconnect",new ExpressionCommand( new DisconnectCommand()));
		Utilities.CommandsHash.put("openDataServer",new ExpressionCommand( new OpenServerCommand()));
		Utilities.CommandsHash.put("while",new ExpressionCommand( new LoopCommand()));
		
		Utilities.CommandsHash2.put("return",new ReturnCommand());
		Utilities.CommandsHash2.put("var",new DefineVarCommand());
		Utilities.CommandsHash2.put("connect",new ConnectCommand());
		Utilities.CommandsHash2.put("disconnect",new DisconnectCommand());
		Utilities.CommandsHash2.put("openDataServer",new OpenServerCommand());
		Utilities.CommandsHash2.put("while",new LoopCommand());
		
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
	
	
	public static ExpressionCommand getCommandEx(String CommandName) {
		return CommandsHash.getOrDefault(CommandName, null);
	}
	public static Command getCommand(String CommandName) {
		return CommandsHash2.getOrDefault(CommandName, null);
	}
	
	public static boolean isCommandExist(String CommandName) {
		return CommandsHash.containsKey(CommandName);
	}
	
	public static void setCommand(String CommandName,ExpressionCommand cmd) {
		CommandsHash.put(CommandName, cmd);
	}
}
