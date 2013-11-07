package lispel.axioms.basic;

import java.util.LinkedList;

import lispel.Atom;
import lispel.Environment;
import lispel.Expression;
import lispel.Function;

public class AxiomSet extends Function {
	public AxiomSet() {
		super(null, null);
		parameters = new LinkedList<Atom>();
		parameters.add(new Atom("name"));
		parameters.add(new Atom("value"));
		code = new Atom("AXIOM_CONS");
	}
	
	public Expression eval(Environment environment) {
		Expression first = environment.lookup("name");
		Expression second = environment.lookup("value");
		
		if (!(first instanceof Atom))
			return Expression.UNDEFINED;
		environment.getOuter().bind(((Atom)first).getName(), second);
		
		return second;
	}
}
