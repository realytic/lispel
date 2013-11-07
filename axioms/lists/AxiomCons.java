package lispel.axioms.lists;

import java.util.LinkedList;

import lispel.Atom;
import lispel.Environment;
import lispel.Expression;
import lispel.Function;
import lispel.List;

public class AxiomCons extends Function {
	public AxiomCons() {
		super(null, null);
		parameters = new LinkedList<Atom>();
		parameters.add(new Atom("x"));
		parameters.add(new Atom("y"));
		code = new Atom("AXIOM_CONS");
	}
	
	public Expression eval(Environment environment) {
		Expression first = environment.lookup("x");
		Expression second = environment.lookup("y");
		
		if (second instanceof List) {
			LinkedList<Expression> result = new LinkedList<Expression>();
			
			result.add(first);
			result.addAll(((List)second).getElements());
			
			return new List(result);
		} else {
			return Expression.UNDEFINED;
		}
	}

}