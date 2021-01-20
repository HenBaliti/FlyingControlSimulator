package Commands;

import java.util.LinkedList;
import java.util.List;

import Expression.Expression;

public class ExpressionCommand implements Expression {
    private Command c;
    Utilities ut;

    private List<String> s = new LinkedList<String>();

    public ExpressionCommand(Command c,Utilities ut) {
    	this.ut = ut;
        this.c = c;
    }

    public Command getC() {
        return c;
    }

    public void setC(Command c) {
        this.c = c;
    }

    public List<String> getS() {
        return s;
    }

    public void setS(List<String> s) {
        this.s.addAll(s);
    }

    @Override
    public double calculate() {
        return c.doCommand(s,this.ut);
    }
}