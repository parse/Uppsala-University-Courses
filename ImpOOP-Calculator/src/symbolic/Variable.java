package symbolic;

import java.io.IOException;
import java.util.TreeMap;

public class Variable extends Atom {
	private String ident;
	
	public Variable(String ident) {
		this.ident = ident;
	}
		
	protected String getName() {
		return ident;
	}
	
	protected Sexpr diff (Sexpr arg) {
		if (arg.getName().equals(ident))
			return new Constant(1);
		else
			return new Constant(0);
	}
	
	public Sexpr eval(TreeMap<String,Sexpr> t) throws IOException {
		if (t.containsKey(ident))
			return t.get(ident);
		else
			return this;
	}
	
	public boolean isVariable() {
		return true;
	}
}