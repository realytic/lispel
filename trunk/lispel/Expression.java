package lispel;

public abstract class Expression {	
	public final static List UNDEFINED = new List();
	public final static Atom TRUE = new Atom("true");
	public final static Atom FALSE = new Atom("false");
	public abstract Expression eval(Environment environment);
}
