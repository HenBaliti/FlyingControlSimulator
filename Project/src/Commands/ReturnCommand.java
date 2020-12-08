package Commands;

import java.util.List;


public class ReturnCommand implements Command{

	//return 
	@Override
	public int doCommand(List<String> tokens) {
		StringBuilder e = new StringBuilder();
		for(int i=1; i<tokens.size(); i++)
			e.append(tokens.get(i));
		
		Interpeter.Parser.returnedValue=  (int) Expression.ShuntingYard.calc(e.toString());
		return 0;
		
	}


}
