package Commands;

import java.util.ArrayList;
import java.util.List;

public interface Command {

	public int doCommand(List<String> tokens);
	public void testArgs(String args);
}