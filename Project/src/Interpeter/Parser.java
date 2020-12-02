package Interpeter;


import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

public class Parser {

	public void parse(String fileName,Lexer lexer) throws IOException{
		
		List<String> errorsList = new ArrayList<>();
		int IndexNum=0;
		String cmdName;
		Command cmd;
		String[] args;
		
		BufferedReader fileInput = new BufferedReader(new FileReader(new File(fileName)));
		
		String line;
		while((line=fileInput.readLine())!=null){
			IndexNum++;
			
			//Checks that there is no "White Words" on the line that have readen. + Ignore Empty lines
			if(line.trim().length()==0)
				continue;
			try {
				List<String> tokens = lexer.lexer(line);
				cmdName = tokens.get(0);
				
				//Checking if the Command is already exist in the command hash
				if(Utilities.isCommandExist(cmdName)) {
					errorsList.add("In Line "+IndexNum+" Command is Not Valid!/n");
					continue;
				}
				cmd = Utilities.getCommand(cmdName);
				args = tokens.toArray(new String[0]);
				cmd.testArgs(args);
				
			}catch(Exception e){
				errorsList.add("In Line "+IndexNum+": "+e.getMessage());
				continue;
			}
			
		}
	}
}
