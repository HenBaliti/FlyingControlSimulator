package Commands;

import java.util.List;

public class DisconnectCommand implements Command{

	int numOfArgs;
	@Override
	public int doCommand(List<String> tokens) {
        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        OpenServerCommand.stop=true;
        ConnectCommand.stop=true;
        System.out.println("bye");
        
		numOfArgs=0;
		return numOfArgs;
	}

	@Override
	public void testArgs(String args) {
		// TODO Auto-generated method stub
		
	}

}
