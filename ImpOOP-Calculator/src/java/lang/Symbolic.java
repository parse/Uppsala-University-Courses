package java.lang;

import symbolic.Constant;
import symbolic.Sexpr;
import symbolic.Sin;

public class Symbolic {
	public static Sexpr sin (Sexpr arg) {
		if ( arg.isConstant() )
			return new Constant( Math.sin( arg.getValue() ) );
		else
			return new Sin(arg);
	}
}
