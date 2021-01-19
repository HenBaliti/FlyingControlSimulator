package Commands;

import java.util.List;


public class PrintCommand implements  Command{
	public Utilities ut;
    @Override
    public int doCommand(List<String> tokens, Utilities ut) {
    	this.ut = ut;
        for (int i=1;i<tokens.size();i++)
        {
            if(ut.symbolTable.containsKey(tokens.get(i)))
                System.out.print(tokens.get(i)+": "+ut.symbolTable.get(tokens.get(i)).getV());
            else
                System.out.print(tokens.get(i));
        }
        System.out.println("");
        return 2;
    }

}

