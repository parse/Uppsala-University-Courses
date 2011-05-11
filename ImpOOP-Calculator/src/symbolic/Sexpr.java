package symbolic;
import java.io.IOException;
import java.util.TreeMap;

public abstract class Sexpr {
	protected int priority = 0;
	private double value = 0;

	protected abstract Sexpr diff (Sexpr v);
	protected abstract String getName();
	public abstract Sexpr eval(TreeMap<String,Sexpr> t) throws IOException; 
		
	protected int getPriority () {
		return priority;
	}
	
	public boolean isConstant () {
		return false;
	}
	
	public boolean isVariable () {
		return false;
	}
	
	public double getValue () {
		return value;
	}
}
