package Commands;

import java.util.List;
import Commands.Utilities;
import Expression.ShuntingYard;

public class ReturnCommand implements Command{

	@Override
	public int doCommand(List<String> tokens) {
		StringBuilder e = new StringBuilder();
		for(int i=1; i<tokens.size(); i++)
			e.append(tokens.get(i));
		
	//	Expression.ShuntingYard.execute(e.toString(), Utilities.getSymbolTable());
		return 0;
		
	}

	@Override
	public void testArgs(String args) {
		// TODO Auto-generated method stub
		
	}

}
