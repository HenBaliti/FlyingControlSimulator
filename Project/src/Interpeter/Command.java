package Interpeter;

import java.util.ArrayList;

public interface Command {

	public int doCommand(ArrayList<String> tokens);
	public void testArgs(String args);
}
