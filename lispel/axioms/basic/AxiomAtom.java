package lispel.axioms.basic;

import java.util.LinkedList;

import lispel.Atom;
import lispel.Environment;
import lispel.Expression;
import lispel.Function;
import lispel.List;

public class AxiomAtom extends Function {
	public AxiomAtom() {
		super(null, null);
		parameters = new LinkedList<Atom>();
		parameters.add(new Atom("x"));
		code = new Atom("AXIOM_ATOM");
	}
	
	public Expression eval(Environment environment) {
		Expression first = environment.lookup("x");
		
		if (first instanceof Atom ||
		   ((first instanceof List) && (((List)first).getElements().isEmpty()))) {
			return Expression.TRUE;
		} else {
			return Expression.FALSE;
		}
	}
}
