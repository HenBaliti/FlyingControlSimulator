package Commands;

import java.util.List;

import Expression.ShuntingYard;

public class SleepCommand implements Command {
	int numOfArgs = 0;
	@Override
	public int doCommand(List<String> tokens) {
		// TODO Auto-generated method stub
		  try {
	            Thread.sleep((long)ShuntingYard.calc(tokens.get(1)));
	        } catch (InterruptedException e) {
	            e.printStackTrace();
	        }
		  numOfArgs = 2;
		return numOfArgs;
	}

	@Override
	public void testArgs(String args) {
		// TODO Auto-generated method stub
		
	}

}
