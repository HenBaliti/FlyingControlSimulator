package Expression;
import java.util.*;

import Commands.Utilities;

//Calculate Every Expression
public class ShuntingYard {
	public static double calc(String exp) {
		if (!validations(exp))
			System.out.println("throw exception");
		LinkedList<String> queue = new LinkedList<>();
		Stack<String> stack = new Stack<>();
		int len = exp.length();

		String token = "";
		for (int i = 0; i < len; i++) {
			if (exp.charAt(i) >= '0' && exp.charAt(i) <= '9') {
				token = exp.charAt(i) + "";
				while ((i + 1 < len && exp.charAt(i + 1) >= '0' && exp.charAt(i + 1) <= '9')
						|| (i + 1 < len && exp.charAt(i + 1) == '.'))
					token = token + exp.charAt(++i);
			}

			else if ((exp.charAt(i) >= 'A' && exp.charAt(i) <= 'Z')||(exp.charAt(i) >= 'a' && exp.charAt(i) <= 'z')) {
				token = exp.charAt(i) + "";
				while (i<exp.length()-1&&((exp.charAt(i+1) >= 'A' && exp.charAt(i+1) <= 'Z')||(exp.charAt(i+1) >= 'a' && exp.charAt(i+1) <= 'z')))
					token = token + exp.charAt(++i);
				//////////
				String sta=Utilities.symbolTable.get(token).getSIM();
				//if its not empty object
				if(sta!=null)
					token= Utilities.symbolTableSim.get(sta).getV()+"";
				else
					token= Utilities.symbolTable.get(token).getV()+"";
			} else
				token = exp.charAt(i) + "";


			switch (token) {
				case "+":
				case "-":
					while (!stack.isEmpty() && !stack.peek().equals("("))
						queue.addFirst(stack.pop());
					stack.push(token);
					break;
				case "*":
				case "/":
					while (!stack.isEmpty() && (stack.peek().equals("*") || stack.peek().equals("/")))
						queue.addFirst(stack.pop());
					stack.push(token);
					break;
				case "(":
					stack.push(token);
					break;
				case ")":
					while (!stack.isEmpty() && !(stack.peek().equals("(")))
						queue.addFirst(stack.pop());
					stack.pop();
					break;
				default: // Always a number
					queue.addFirst(token);
					break;
			}
		}
		while (!stack.isEmpty())
			queue.addFirst(stack.pop());
		Expression finalExpression = buildExp(queue);
		double answer = finalExpression.calculate();
		return Double.parseDouble(String.format("%.3f", answer));
	}

	private static boolean validations(String expression) {
		return true; // TODO implement validations

	}

	private static Expression buildExp(LinkedList<String> queue) {
		Expression returnedExp = null;
		Expression right = null;
		Expression left = null;
		String currentExp = queue.removeFirst();
		if (currentExp.equals("+") || currentExp.equals("-") || currentExp.equals("*")
				|| currentExp.equals("/")) {
			right = buildExp(queue);
			left = buildExp(queue);
		}
		switch (currentExp) {
			case "+":
				returnedExp = new Plus(left, right);
				break;
			case "-":
				returnedExp = new Minus(left, right);
				break;
			case "*":
				returnedExp = new Mul(left, right);
				break;
			case "/":
				returnedExp = new Div(left, right);
				break;
			default:
				returnedExp = new Number(
						Double.parseDouble(String.format("%.2f", Double.parseDouble(currentExp))));
				break;
		}

		return returnedExp;
	}

	}
