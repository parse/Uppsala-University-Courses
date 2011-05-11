package symbolic;

public class Symbolic {
	public static Sexpr negate(Sexpr arg) {
		if (arg.isConstant())
			return new Constant(-1*arg.getValue());
		else
			return new Negation(arg);
	}
	
	public static Sexpr add(Sexpr left, Sexpr right) {		
		if (left.isConstant() && right.isConstant())
			return new Constant(left.getValue() + right.getValue());		
		else if (left.isConstant() && left.getValue() == 0.) 
			return right;
		else if (right.isConstant() && right.getValue() == 0.) 
			return left;
		else if (left.isVariable() && right.isVariable() && right.toString().equals(left.toString()))
			return new Multiplication(new Constant(2), right);
		else
			return new Addition(left, right);
	}
	
	public static Sexpr subtract(Sexpr left, Sexpr right) {
		if (left.isConstant() && right.isConstant())
			return new Constant(left.getValue() - right.getValue());
		else if (left.isConstant() && left.getValue() == 0.) 
			return right;
		else if (right.isConstant() && right.getValue() == 0.) 
			return left;
		else
			return new Subtraction(left, right);
	}
	
	public static Sexpr multiply(Sexpr left, Sexpr right) {
		if (left.isConstant() && right.isConstant())
			return new Constant(left.getValue() * right.getValue());
		else if (left.isConstant() && left.getValue() == 0.) 
			return new Constant(0);
		else if (right.isConstant() && right.getValue() == 0.) 
			return new Constant(0);
		else if (left.isConstant() && left.getValue() == 1.)
			return right;
		else if (right.isConstant() && right.getValue() == 1.)
			return left;
		else
			return new Multiplication(left, right);
	}
	
	public static Sexpr divide(Sexpr left, Sexpr right) {
		if (left.isConstant() && right.isConstant())
			return new Constant(left.getValue() / right.getValue());
		else if (left.isConstant() && left.getValue() == 0.) 
			return new Constant(0);
		else
			return new Division(left, right);
	}
	
	public static Sexpr sin(Sexpr arg){
		if (arg.isConstant())
			return new Constant(Math.sin(arg.getValue()));
		else
			return new Sin(arg);
	}
	
	public static Sexpr cos(Sexpr arg) {
		if (arg.isConstant())
			return new Constant(Math.cos(arg.getValue()));
		else
			return new Cos(arg);
	}
	
	public static Sexpr exp(Sexpr arg) {
		if (arg.isConstant())
			return new Constant(Math.exp(arg.getValue()));
		else
			return new Exp(arg);
	}
	
	public static Sexpr log(Sexpr arg) {
		if (arg.isConstant())
			return new Constant(Math.log(arg.getValue()));
		else
			return new Log(arg);
	}	
}
