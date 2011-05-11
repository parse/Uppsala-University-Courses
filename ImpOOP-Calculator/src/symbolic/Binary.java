package symbolic;

public abstract class Binary extends Sexpr {
	public Sexpr left;
	public Sexpr right;
	
	public Binary (Sexpr left, Sexpr right) {
		this.left = left;
		this.right = right;
	}
	
	protected Sexpr getLeft () {
		return left;
	}
	
	protected Sexpr getRight () {
		return right;
	}
	
	public String toString () {
		String res;
		if (left.getPriority() > this.getPriority())
			res = "(" + left.toString() + ")";
		else
			res = left.toString();
		
		res += " " + this.getName() + " ";
		
		if (right.getPriority() > this.getPriority())
			res += "(" + right.toString() + ")";
		else
			res += right.toString();
		
		return res;
	}	
	
}
