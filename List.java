package lispel;

import java.util.LinkedList;

public class List extends Expression {
	private final LinkedList<Expression> elements;
	
	public List(LinkedList<Expression> elements) {
		this.elements = elements;
	}
	
	public List() {
		this.elements = new LinkedList<Expression>();
	}
	
	public LinkedList<Expression> getElements() {
		return elements;
	}
	
	@Override
	public Expression eval(Environment environment) {
		if (!elements.isEmpty()) {
			/* list starts with a symbol */
			if (elements.getFirst() instanceof Atom) {
				Atom symbol = (Atom)elements.getFirst(); 
				String name = symbol.getName();	
				
				/* special forms: */
				/* quote */
				if (name.equals("quote")) { 		
					if (elements.size() != 2)
						return Expression.UNDEFINED;
					return elements.get(1);
				}
				/* lambda */
				else if (name.equals("lambda")) {
					if (elements.size() != 3) 
						return Expression.UNDEFINED;
					Expression parameterList = elements.get(1);
					if (!(parameterList instanceof List))
						return Expression.UNDEFINED;
					LinkedList<Expression> parameters = ((List) parameterList).getElements();
					LinkedList<Atom> parameterSymbols = new LinkedList<Atom>();
					for (Expression parameter : parameters) {
						if (!(parameter instanceof Atom))
							return Expression.UNDEFINED;
						parameterSymbols.add((Atom)parameter);
					}
					Expression code = elements.get(2);
					
					return new Function(parameterSymbols, code);
				}
				/* cond */
				else if (name.equals("cond")) {
					boolean skip = true;
					for (Expression expression : elements) {
						if (skip) {
							skip = false;
						} else {
							if (!(expression instanceof List))
								return Expression.UNDEFINED;
							List pair = (List) expression;
							if (pair.getElements().size() != 2)
								return Expression.UNDEFINED;
							Expression firstElement = pair.getElements().getFirst().eval(environment);
							if (firstElement instanceof Atom) {
								Atom first = (Atom)firstElement;
								if (first.getName().equals(Expression.TRUE.getName()))
									return pair.getElements().get(1).eval(environment);
							}
						}
					}
					return Expression.UNDEFINED;
				}
			}
			
			/* function call*/
			Expression firstElement = elements.getFirst().eval(environment);
			if (firstElement instanceof Function) {
				Function function = (Function)firstElement;
				LinkedList<Expression> arguments = new LinkedList<Expression>();
				
				if (function.parameters.size() != elements.size() - 1)
					return Expression.UNDEFINED;
				
				boolean skip = true;
				for (Expression expression : elements) {
					if (skip) {
						skip = false;
					} else {
						arguments.add(expression.eval(environment));
					}
				}
				
				Environment functionEnvironment = new Environment(environment);
				for (Atom parameter : function.getParameters()) {
					functionEnvironment.bind(parameter.getName(), arguments.poll());
				}

				return function.eval(functionEnvironment);
			}
			
			/* normal list */
			LinkedList<Expression> evaluatedElements = new LinkedList<Expression>();
			evaluatedElements.add(firstElement);
			boolean skip = true;
			for (Expression expression : elements) {
				if (skip) {
					skip = false;
				} else {
					evaluatedElements.add(expression.eval(environment));
				}
			}
			return new List(evaluatedElements);
		} else {
			return new List();
		}
	}
	
	public String toString() {
		StringBuffer buffer = new StringBuffer("(");
		
		int i = 0;
		for (Expression expression : elements) {
			buffer.append(expression);
			if (i != elements.size()-1)
				buffer.append(" ");
			i++;
		}
		buffer.append(")");
		
		return buffer.toString();
	}
}
