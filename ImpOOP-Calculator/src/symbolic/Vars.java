package symbolic;

import java.io.IOException;
import java.util.TreeMap;

public class Vars extends Command {
	
	protected String getName() {
		return "vars";
	}
	
	public Sexpr eval(TreeMap<String,Sexpr> t) throws IOException {
		return new Variable( t.toString() );
	}
}
