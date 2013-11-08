package lispel.axioms.basic;

import java.util.LinkedList;

import lispel.Atom;
import lispel.Environment;
import lispel.Expression;
import lispel.Function;

public class AxiomEval extends Function {
	public AxiomEval() {
		super(null, null);
		parameters = new LinkedList<Atom>();
		parameters.add(new Atom("x"));
		code = new Atom("AXIOM_EVAL");
	}
	
	public Expression eval(Environment environment) {
		Expression first = environment.lookup("x");
		
		return first.eval(environment);
	}
}
