package symbolic;

import java.io.IOException;
import java.util.TreeMap;

public class Multiplication extends Binary {
	
	public Multiplication (Sexpr left, Sexpr right) {
		super(left, right);
		this.priority = 3;
	}
	
	protected String getName() {
		return "*";
	}
	
	public Sexpr diff(Sexpr var) {
		return Symbolic.add(Symbolic.multiply(left.diff(var), right), 
										Symbolic.multiply(left, right.diff(var)));
	}
	
	public Sexpr eval(TreeMap<String,Sexpr> t) throws IOException {
		Sexpr left = this.left.eval(t);
		Sexpr right = this.right.eval(t);
		
		return Symbolic.multiply(left, right);
	}
}
