package Interpeter;

public interface Command {

	public void doCommand(String[] args) throws Exception;
	public void testArgs(String[] args)	throws Exception;
}
