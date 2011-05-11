package symbolic;

import java.io.IOException;
import java.util.TreeMap;

public class Quotation extends Unary {
	public Quotation(Sexpr arg) {
		super(arg);
		this.priority = 1;
	}
	
	public Sexpr eval(TreeMap<String,Sexpr> t) throws IOException {
		return this.arg;
	}
	
	protected String getName() {
		return "\"";
	}

	protected Sexpr diff(Sexpr var) {
		return new Quotation(arg.diff(var));
	}
}
