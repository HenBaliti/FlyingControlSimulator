package Commands;

import java.util.ArrayList;
import Commands.Utilities;
import Expression.ShuntingYard;

public class ReturnCommand implements Command{

	@Override
	public int doCommand(ArrayList<String> tokens) {
		StringBuilder e = new StringBuilder();
		for(int i=1; i<tokens.size(); i++)
			e.append(tokens.get(i));
		ShuntingYard.execute(e.toString(), Utilities.getSymbolTable());
		return 0;
		
	}

	@Override
	public void testArgs(String args) {
		// TODO Auto-generated method stub
		
	}

}
