package symbolic;

import java.io.IOException;
import java.util.TreeMap;

public class Addition extends Binary {
	
	public Addition (Sexpr left, Sexpr right) {
		super(left, right);
		this.priority = 4;
	}
	
	protected String getName() {
		return "+";
	}
	
	public Sexpr diff(Sexpr var) {
		return Symbolic.add(left.diff(var), right.diff(var));
	}
	
	public Sexpr eval(TreeMap<String,Sexpr> t) throws IOException {
		Sexpr left = this.left.eval(t);
		Sexpr right = this.right.eval(t);
		
		return Symbolic.add(left, right);
	}
	
	
}
