package Commands;

import java.util.List;
import Expression.ShuntingYard;

public class SleepCommand implements Command{

	public Utilities ut;
    @Override
    public int doCommand(List<String> tokens, Utilities ut) {
        try {
            Thread.sleep((long) ShuntingYard.calc(tokens.get(1)));
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        return 2;
    }
}
