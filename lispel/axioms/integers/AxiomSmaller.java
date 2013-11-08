package lispel.axioms.integers;

import java.util.LinkedList;

import lispel.Atom;
import lispel.Environment;
import lispel.Expression;
import lispel.Function;

public class AxiomSmaller extends Function {
	public AxiomSmaller() {
		super(null, null);
		parameters = new LinkedList<Atom>();
		parameters.add(new Atom("x"));
		parameters.add(new Atom("y"));
		code = new Atom("AXIOM_SMALLER");
	}
	
	public Expression eval(Environment environment) {
		Expression first = environment.lookup("x");
		Expression second = environment.lookup("y");
		
		if ((first instanceof Atom) && (second instanceof Atom)) {
			try {
				int x = Integer.parseInt(((Atom)first).getName());
				int y = Integer.parseInt(((Atom)second).getName());
				if (x < y)
					return Expression.TRUE;
				else
					return Expression.FALSE;
			} catch (NumberFormatException e) {
				return Expression.UNDEFINED;
			}
		} else {
			return Expression.UNDEFINED;
		}
	}
}
