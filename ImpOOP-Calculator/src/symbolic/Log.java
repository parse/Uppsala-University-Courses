package symbolic;

import java.io.IOException;
import java.util.TreeMap;

public class Log extends Unary {
	public Log(Sexpr arg) {
		super(arg);
		this.priority = 2;
	}
	
	public Sexpr eval(TreeMap<String,Sexpr> t) throws IOException {
		return Symbolic.log(arg.eval(t));
	}
	
	protected String getName() {
		return "log";
	}

	protected Sexpr diff(Sexpr var) {
		return Symbolic.multiply(Symbolic.divide(new Constant(1), new Log(arg)), arg.diff(var));
	}
}
