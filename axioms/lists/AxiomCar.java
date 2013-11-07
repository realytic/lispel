package lispel.axioms.lists;

import java.util.LinkedList;

import lispel.Atom;
import lispel.Environment;
import lispel.Expression;
import lispel.Function;
import lispel.List;

public class AxiomCar extends Function {
	public AxiomCar() {
		super(null,null);
		parameters = new LinkedList<Atom>();
		parameters.add(new Atom("x"));
		code = new Atom("AXIOM_CAR");
	}
	
	public Expression eval(Environment environment) {
		Expression first = environment.lookup("x");
		
		if (first instanceof List) {
			List list = (List)first;
			if (list.getElements().isEmpty())
				return Expression.UNDEFINED;
			else
				return list.getElements().getFirst();
		} else {
			return Expression.UNDEFINED;
		}
	}
}
