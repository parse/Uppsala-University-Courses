package symbolic;

import java.io.IOException;
import java.util.TreeMap;

public class Differentiation extends Binary {

	public Differentiation (Sexpr left, Sexpr right) {
		super(left, right);
		this.priority = 2;
	}
	
	public Sexpr eval(TreeMap<String,Sexpr> t) throws IOException {
		return left.eval(t).diff(right);
	}
	
	protected String getName() {
		return "'";
	}
	
	protected Sexpr diff(Sexpr var) {
		return left.diff(var);
	}
}
