package symbolic;

import java.util.TreeMap;
import Exceptions.*;

public class Quit extends Command {	
	protected String getName() {
		return "quit";
	}
	
	public Sexpr eval(TreeMap<String,Sexpr> t) {
		throw new QuitException("Programmet avslutades");
	}
}
