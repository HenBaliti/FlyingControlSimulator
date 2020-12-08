package Commands;

import java.util.ArrayList;
import java.util.List;

//command interface
public interface Command {

	public int doCommand(List<String> tokens);
}