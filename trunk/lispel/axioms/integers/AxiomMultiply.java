package lispel.axioms.integers;

import java.util.LinkedList;

import lispel.Atom;
import lispel.Environment;
import lispel.Expression;
import lispel.Function;

public class AxiomMultiply extends Function {
	public AxiomMultiply() {
		super(null, null);
		parameters = new LinkedList<Atom>();
		parameters.add(new Atom("x"));
		parameters.add(new Atom("y"));
		code = new Atom("AXIOM_MULTIPLY");
	}
	
	public Expression eval(Environment environment) {
		Expression first = environment.lookup("x");
		Expression second = environment.lookup("y");
		
		if ((first instanceof Atom) && (second instanceof Atom)) {
			try {
				int x = Integer.parseInt(((Atom)first).getName());
				int y = Integer.parseInt(((Atom)second).getName());
			
				return new Atom("" + (x * y));
			} catch (NumberFormatException e) {
				return Expression.UNDEFINED;
			}
		} else {
			return Expression.UNDEFINED;
		}
	}
}
