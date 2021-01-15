package Commands;

import java.util.List;
import Expression.ShuntingYard;

public class SleepCommand implements Command{

    @Override
    public int doCommand(List<String> tokens) {
        try {
            Thread.sleep((long) ShuntingYard.calc(tokens.get(1)));
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        return 2;
    }
}
