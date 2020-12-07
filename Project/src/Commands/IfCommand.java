package Commands;

import java.util.List;

public class IfCommand extends ConditionCommand implements Command{

	@Override
	public int doCommand(List<String> tokens) {
		// TODO Auto-generated method stub
		if(checkCondtion()) {
			makeCommands(tokens);
		}
		
		return tokens.size();
	}

	@Override
	public void testArgs(String args) {
		// TODO Auto-generated method stub
		
	}

}
