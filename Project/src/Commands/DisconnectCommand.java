package Commands;

import java.util.List;

public class DisconnectCommand implements Command{

	int numOfArgs;
	@Override
	public int doCommand(List<String> tokens) {
		// TODO Auto-generated method stub
		numOfArgs=0;
		return numOfArgs;
	}

	@Override
	public void testArgs(String args) {
		// TODO Auto-generated method stub
		
	}

}
