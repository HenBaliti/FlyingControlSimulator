package Commands;

import java.util.ArrayList;
import Expression.Q3;

public class ReturnCommand implements Command{

	@Override
	public int doCommand(ArrayList<String> tokens) {
		StringBuilder e = new StringBuilder();
		for(int i=1; i<tokens.size(); i++)
			e.append(tokens.get(i));
//		calc(e.toString());
		return tokens.size();
		
	}

	@Override
	public void testArgs(String args) {
		// TODO Auto-generated method stub
		
	}

}
