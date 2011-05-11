package symbolic;

import java.io.IOException;
import java.util.TreeMap;

public class Assignment extends Binary {
	public Assignment(Sexpr left, Sexpr right) {
		super(left, right);
		this.priority = 5;
	}
	
	public Sexpr eval(TreeMap<String,Sexpr> t) throws IOException {
		Sexpr l = left.eval(t);
		t.put(right.getName(), l);
		
		return l;
	}
	
	protected Sexpr diff(Sexpr var) {
		return left.diff(var);
	}
	
	protected String getName() {
		return "=";
	}
}
