package symbolic;

import java.io.IOException;
import java.util.TreeMap;

public class Subtraction extends Binary {
	
	public Subtraction (Sexpr left, Sexpr right) {
		super(left, right);
		this.priority = 4;
	}
	
	protected String getName() {
		return "-";
	}
	
	public Sexpr diff(Sexpr arg) {
		return Symbolic.subtract(left.diff(arg), right.diff(arg));
	}
	
	public Sexpr eval(TreeMap<String,Sexpr> t) throws IOException {
		Sexpr left = this.left.eval(t);
		Sexpr right = this.right.eval(t);
		
		return Symbolic.subtract(left, right);
	}
}
