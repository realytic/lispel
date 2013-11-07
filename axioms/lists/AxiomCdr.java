package lispel.axioms.lists;

import java.util.LinkedList;

import lispel.Atom;
import lispel.Environment;
import lispel.Expression;
import lispel.Function;
import lispel.List;

public class AxiomCdr extends Function {
	public AxiomCdr() {
		super(null, null);
		parameters = new LinkedList<Atom>();
		parameters.add(new Atom("x"));
		code = new Atom("AXIOM_CDR");
	}
	
	public Expression eval(Environment environment) {
		Expression first = environment.lookup("x");
		
		if (first instanceof List) {
			List list = (List)first;
			if (list.getElements().isEmpty()) {
				return Expression.UNDEFINED;
			} else {
				LinkedList<Expression> result = new LinkedList<Expression>();
				
				boolean skip = true;
				for (Expression expression : list.getElements()) {
					if (skip) {
						skip = false;
					} else {
						result.add(expression);
					}
				}
				
				return new List(result);
			}
		} else {
			return Expression.UNDEFINED;
		}
	}

}
