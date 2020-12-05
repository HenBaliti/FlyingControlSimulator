package Commands;

import java.util.List;

public class ConnectCommand implements Command{

	int numOfArgs;
	@Override
	public int doCommand(List<String> tokens) {

		numOfArgs = 2;
		return numOfArgs;
	}

	@Override
	public void testArgs(String args) {
		// TODO Auto-generated method stub
		
	}

}
