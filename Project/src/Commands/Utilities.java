package Commands;

import java.util.HashMap;
import java.util.Map;

import Interpeter.SymbolTabelObject;

//all the hashmap utilities
public class Utilities {
	public static Map<String,SymbolTabelObject> symbolTable = new HashMap<String,SymbolTabelObject>();
	public static Map<String,ExpressionCommand> CommandsHash = new HashMap<String,ExpressionCommand>();
	public static Map<String,Command> CommandsHash2 = new HashMap<String,Command>();
	
	public Utilities() {
		
//		symbolTable.put("aileron", new SymbolTabelObject(0,"/controls/flight/aileron"));
//		symbolTable.put("elevator", new SymbolTabelObject(0, "/controls/flight/elevator"));
		
		Utilities.CommandsHash.put("return",new ExpressionCommand( new ReturnCommand()));
		Utilities.CommandsHash.put("var",new ExpressionCommand( new DefineVarCommand()));
		Utilities.CommandsHash.put("while",new ExpressionCommand( new ConditionCommand()));
		Utilities.CommandsHash.put("connect",new ExpressionCommand( new ConnectCommand()));
		Utilities.CommandsHash.put("disconnect",new ExpressionCommand( new DisconnectCommand()));
		Utilities.CommandsHash.put("openDataServer",new ExpressionCommand( new OpenServerCommand()));
		Utilities.CommandsHash.put("sleep", new ExpressionCommand(new SleepCommand()));
		Utilities.CommandsHash.put("print", new ExpressionCommand(new PrintCommand()));
		
		Utilities.CommandsHash2.put("return",new ReturnCommand());
		Utilities.CommandsHash2.put("while",new ConditionCommand());
		Utilities.CommandsHash2.put("var",new DefineVarCommand());
		Utilities.CommandsHash2.put("connect",new ConnectCommand());
		Utilities.CommandsHash2.put("disconnect",new DisconnectCommand());
		Utilities.CommandsHash2.put("openDataServer",new OpenServerCommand());
		Utilities.CommandsHash2.put("sleep", new SleepCommand());
		Utilities.CommandsHash2.put("print", new PrintCommand());
		
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
