package lispel;

public class Atom extends Expression {
	private final String name;

	public Atom(String name) {
		this.name = name;
	}
	
	@Override
	public Expression eval(Environment environment) {
		Expression result = environment.lookup(name); 
		return (result != null) ? result : this;
	}

	public String getName() {
		return name;
	}
	
	public String toString() {
		return getName();
	}
}
