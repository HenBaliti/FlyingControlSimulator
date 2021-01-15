package Commands;

import java.util.List;


public class PrintCommand implements  Command{
    @Override
    public int doCommand(List<String> tokens) {
        for (int i=1;i<tokens.size();i++)
        {
            if(Utilities.symbolTable.containsKey(tokens.get(i)))
                System.out.print(tokens.get(i)+Utilities.symbolTable.get(tokens.get(i)).getV());
            else
                System.out.print(tokens.get(i));
        }
        System.out.println("");
        return 2;
    }

}

