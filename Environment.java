package lispel;

import java.util.HashMap;

public class Environment {
	private final Environment outer;
	private final HashMap<String, Expression> local = new HashMap<String, Expression>();
	
	public Environment(Environment outer) {
		this.outer = outer;
	}
	
	public Environment getOuter() {
		return outer;
	}
	
	public void bind(String name, Expression expression) {
		local.put(name, expression);
	}
	
	public Expression lookup(String name) {
		Expression value = local.get(name);
		if ((value == null) && (outer != null))
			value = outer.lookup(name);
		return value;
	}
}
