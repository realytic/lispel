package lispel.axioms.basic;

import java.util.LinkedList;

import lispel.Atom;
import lispel.Environment;
import lispel.Expression;
import lispel.Function;
import lispel.List;

public class AxiomEq extends Function {
	public AxiomEq() {
		super(null, null);
		parameters = new LinkedList<Atom>();
		parameters.add(new Atom("x"));
		parameters.add(new Atom("y"));
		code = new Atom("AXIOM_EQ");
	}
	
	public Expression eval(Environment environment) {
		Expression first = environment.lookup("x");
		Expression second = environment.lookup("y");
		
		if ((first instanceof Atom) && (second instanceof Atom) && (((Atom)first).getName().equals(((Atom)second).getName()))) {
			return Expression.TRUE;
		} else if ((first  instanceof List) && ((List)first ).getElements().isEmpty() &&
				  ( second instanceof List) && ((List)second).getElements().isEmpty()) {
			return Expression.TRUE;
		} else {
			return Expression.FALSE;
		}
	}
}
