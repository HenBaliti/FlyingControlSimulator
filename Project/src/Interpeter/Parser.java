package Interpeter;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;


public class Parser implements ParserInterface {

	@Override
	public void parse(String[] lines) {

		ArrayList<String> errorsList = new ArrayList<>();
		Lexer lexer = new Lexer();
		ArrayList<String> tokens = lexer.lexer(lines);
		int IndexNumRow = 0;
		int index = 0;
		String cmdName;
		Command cmd;


		while(index!=tokens.size()) {
			IndexNumRow++; // Counting the rows of the "Code"
			cmdName = tokens.get(index);

			// Checking if the Command is not exist in the command hash
			if (!(Utilities.isCommandExist(cmdName))) {
				errorsList.add("In Line " + IndexNumRow + " Command is Not Valid!/n");
				IndexNumRow++;
				index++;
			}
			else {
				cmd = Utilities.getCommand(cmdName);
				//To get the new ArrayList from the index i want to the index i want.
				ArrayList<String> subArray = (ArrayList<String>) tokens.subList(index, tokens.size());
				index+= cmd.doCommand(subArray);
			}
		}

	}
}
