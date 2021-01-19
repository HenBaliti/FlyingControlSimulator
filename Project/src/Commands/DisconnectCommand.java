package Commands;

import java.util.List;

public class DisconnectCommand implements Command{

	public Utilities ut;
	//disconnecting from server
	int numOfArgs;
	@Override
	public int doCommand(List<String> tokens, Utilities ut) {
		this.ut = ut;
        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        OpenServerCommand.stop=true;
        ConnectCommand.stop=true;
        ConnectCommand.out.println("bye");
        System.out.println("bye");
        
		numOfArgs=0;
		return numOfArgs;
	}


}
