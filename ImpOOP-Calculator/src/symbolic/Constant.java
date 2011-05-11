package symbolic;

import java.io.IOException;
import java.util.TreeMap;

public class Constant extends Atom {
	private double value;
	
	public Constant(double value) {
		this.value = value;
	}
	
	public double getValue () {
		return value;
	}
	
	protected String getName() {
		return Double.toString(value);
	}
	
	public boolean isConstant () {
		return true;
	}
	
	protected Sexpr diff (Sexpr var) {
		return new Constant(0);
	}
	
	public Sexpr eval(TreeMap<String,Sexpr> t) throws IOException {
		return this;
	}
}
