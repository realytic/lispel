package lispel;

import java.util.LinkedList;

public class Function extends Expression {
	protected LinkedList<Atom> parameters;
	protected Expression code;
	
	public Function(LinkedList<Atom> parameters, Expression code) {
		this.parameters = parameters;
		this.code = code;
	}
	
	public LinkedList<Atom> getParameters() {
		return parameters;
	}
	
	@Override
	public Expression eval(Environment environment) {
		Expression result = code.eval(environment);
		return result;
	}
	
	public String toString() {
		StringBuffer buffer = new StringBuffer("(lambda (");
		
		int i = 0;
		for (Atom parameter : parameters) {
			buffer.append(parameter.getName());
			if (i != parameters.size()-1)
				buffer.append(" ");
			i++;
		}
		buffer.append(") ");
		buffer.append(code);
		buffer.append(")");
		
		return buffer.toString();
	}
}
