package symbolic;

public abstract class Atom extends Sexpr {
	public Atom () {

	}
	
	public String toString () {
		return this.getName();
	}
	
}
