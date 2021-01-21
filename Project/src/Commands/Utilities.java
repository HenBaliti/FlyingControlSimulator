package Commands;

import java.util.HashMap;
import java.util.Map;
import java.util.Observable;

import Interpeter.SymbolTabelObject;

//all the hashmap utilities
public class Utilities extends Observable{
	public static Map<String,SymbolTabelObject> symbolTable = new HashMap<String,SymbolTabelObject>();
	public static Map<String,ExpressionCommand> CommandsHash = new HashMap<String,ExpressionCommand>();
	public static Map<String,Command> CommandsHash2 = new HashMap<String,Command>();

	
	public Utilities() {
		
//		symbolTable.put("aileron", new SymbolTabelObject(0,"/controls/flight/aileron"));
//		symbolTable.put("elevator", new SymbolTabelObject(0, "/controls/flight/elevator"));

//	    String[] GettingsNamesOrder = {"/instrumentation/airspeed-indicator/indicated-speed-kt", "/instrumentation/altimeter/pressure-alt-ft", "/instrumentation/attitude-indicator/indicated-pitch-deg", "/instrumentation/attitude-indicator/indicated-roll-deg", "/instrumentation/encoder/indicated-altitude-ft",
//	    	      "/instrumentation/attitude-indicator/internal-roll-deg", "/instrumentation/encoder/indicated-altitude-ft", "/instrumentation/encoder/pressure-alt-ft", "/instrumentation/gps/indicated-altitude-ft", "/instrumentation/gps/indicated-vertical-speed",
//	    	      "/instrumentation/gps/indicated-vertical-speed", "/instrumentation/heading-indicator/indicated-heading-deg", "/instrumentation/magnetic-compass/indicated-heading-deg", "/instrumentation/slip-skid-ball/indicated-slip-skid", "/instrumentation/turn-indicator/indicated-turn-rate", "/instrumentation/vertical-speed-indicator/indicated-speed-fpm", "/controls/flight/flaps", "/engines/engine/rpm"
//	    	};
//		  String[] variablesNamesOrder = {"airspeed", "Pressure", "pitch", "roll", "Internal-Pitch",
//				  "Internal-Roll", "Encoder-Altitude", "Encoder-Pressure", "GPS-Altitude", "Ground-Speed",
//				  "Vertical-Speed", "heading", "Compass-Heading", "Slip", "Turn", "Fpm-Speed", "Flaps", "Rpm"
//				};

		
        symbolTable.put("/instrumentation/airspeed-indicator/indicated-speed-kt",new SymbolTabelObject(0,"airspeed"));
        symbolTable.put("/instrumentation/altimeter/pressure-alt-ft",new SymbolTabelObject(0,"Pressure"));
        symbolTable.put("/instrumentation/attitude-indicator/indicated-pitch-deg",new SymbolTabelObject(0,"pitch"));
        symbolTable.put("/instrumentation/attitude-indicator/indicated-roll-deg",new SymbolTabelObject(0, "roll"));
        symbolTable.put("/instrumentation/attitude-indicator/internal-pitch-deg",new SymbolTabelObject(0,"Internal-Pitch"));
        symbolTable.put("/instrumentation/attitude-indicator/internal-roll-deg",new SymbolTabelObject(0,"Internal-Roll"));
        symbolTable.put("/instrumentation/encoder/indicated-altitude-ft",new SymbolTabelObject(0,"Encoder-Altitude"));
        symbolTable.put("/instrumentation/encoder/pressure-alt-ft",new SymbolTabelObject(0, "Encoder-Pressure"));
        symbolTable.put("/instrumentation/gps/indicated-altitude-ft",new SymbolTabelObject(0,"GPS-Altitude"));
        symbolTable.put("/instrumentation/gps/indicated-ground-speed-kt",new SymbolTabelObject(0,"Ground-Speed"));
        symbolTable.put("/instrumentation/gps/indicated-vertical-speed",new SymbolTabelObject(0,"Vertical-Speed"));
        symbolTable.put("/instrumentation/heading-indicator/indicated-heading-deg",new SymbolTabelObject(0,"heading"));
        symbolTable.put("/instrumentation/magnetic-compass/indicated-heading-deg",new SymbolTabelObject(0,"Compass-Heading"));
        symbolTable.put("/instrumentation/slip-skid-ball/indicated-slip-skid",new SymbolTabelObject(0,"Slip"));
        symbolTable.put("/instrumentation/turn-indicator/indicated-turn-rate",new SymbolTabelObject(0,"Turn"));
        symbolTable.put("/instrumentation/vertical-speed-indicator/indicated-speed-fpm",new SymbolTabelObject(0,"Fpm-Speed"));
        symbolTable.put("/controls/flight/flaps",new SymbolTabelObject(0,"Flaps"));
        symbolTable.put("/engines/engine/rpm",new SymbolTabelObject(0, "Rpm"));
        symbolTable.put("/position/longitude-deg",new SymbolTabelObject(0,"Longtitude"));
        symbolTable.put("/position/latitude-deg",new SymbolTabelObject(0, "Latitude"));
		
		
		Utilities.CommandsHash.put("return",new ExpressionCommand( new ReturnCommand(),this));
		Utilities.CommandsHash.put("var",new ExpressionCommand( new DefineVarCommand(),this));
		Utilities.CommandsHash.put("while",new ExpressionCommand( new ConditionCommand(),this));
		Utilities.CommandsHash.put("connect",new ExpressionCommand( new ConnectCommand(),this));
		Utilities.CommandsHash.put("disconnect",new ExpressionCommand( new DisconnectCommand(),this));
		Utilities.CommandsHash.put("openDataServer",new ExpressionCommand( new OpenServerCommand(),this));
		Utilities.CommandsHash.put("sleep", new ExpressionCommand(new SleepCommand(),this));
		Utilities.CommandsHash.put("print", new ExpressionCommand(new PrintCommand(),this));
		
		Utilities.CommandsHash2.put("return",new ReturnCommand());
		Utilities.CommandsHash2.put("while",new ConditionCommand());
		Utilities.CommandsHash2.put("var",new DefineVarCommand());
		Utilities.CommandsHash2.put("connect",new ConnectCommand());
		Utilities.CommandsHash2.put("disconnect",new DisconnectCommand());
		Utilities.CommandsHash2.put("openDataServer",new OpenServerCommand());
		Utilities.CommandsHash2.put("sleep", new SleepCommand());
		Utilities.CommandsHash2.put("print", new PrintCommand());
		
		
//      String[] variablesNamesOrder = {"/instrumentation/airspeed-indicator/indicated-speed-kt", "/instrumentation/altimeter/pressure-alt-ft", "/instrumentation/attitude-indicator/indicated-pitch-deg", "/instrumentation/attitude-indicator/indicated-roll-deg", "/instrumentation/encoder/indicated-altitude-ft",
//      "/instrumentation/attitude-indicator/internal-roll-deg", "/instrumentation/encoder/indicated-altitude-ft", "/instrumentation/encoder/pressure-alt-ft", "/instrumentation/gps/indicated-altitude-ft", "/instrumentation/gps/indicated-vertical-speed",
//      "/instrumentation/gps/indicated-vertical-speed", "/instrumentation/heading-indicator/indicated-heading-deg", "/instrumentation/magnetic-compass/indicated-heading-deg", "/instrumentation/slip-skid-ball/indicated-slip-skid", "/instrumentation/turn-indicator/indicated-turn-rate", "/instrumentation/vertical-speed-indicator/indicated-speed-fpm", "/controls/flight/flaps", "/engines/engine/rpm"
//};
      
		
		
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
	
	public void setUpdate(String str) {
		setChanged();
		notifyObservers(str);
	}
}
