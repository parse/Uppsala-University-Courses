import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.IOException;
import java.io.StreamTokenizer;
import java.util.TreeMap;

import Exceptions.SyntaxException;

import symbolic.*;

public class Parser {

	private StreamTokenizer st;
	private TreeMap<String,Sexpr> t;	
	
	public Parser (InputStream in, TreeMap<String,Sexpr> t) {
		st = initStreamReader(in);
		this.t = t;
	}
	
	public void reset(InputStream in) {
		st = initStreamReader(in); 
	}
	
	public void setAnswer(Sexpr expr) {
		t.put("ans", expr);
	}

	private static StreamTokenizer initStreamReader(InputStream in) {
		StreamTokenizer st = new StreamTokenizer( new InputStreamReader(in) );
		
		st.eolIsSignificant(true);
		st.ordinaryChar('+');
        st.ordinaryChar('-');   
        st.ordinaryChar('/');
        st.ordinaryChar('*');
        st.ordinaryChar('\'');
        st.ordinaryChar('&');
        st.ordinaryChar('\"');
		
		return st;
	}
	
	public Sexpr statement() throws IOException {
		Sexpr result = null;
		
		st.nextToken();
		if (st.ttype == StreamTokenizer.TT_WORD && Command.isCommand(st.sval))
			result = command();
		else if (st.ttype != StreamTokenizer.TT_EOL && st.ttype != StreamTokenizer.TT_EOF) {
			result = assignment();
		}
		else if (st.ttype == StreamTokenizer.TT_EOF)
			result = new Quit();
		
		if (st.ttype != StreamTokenizer.TT_EOL && st.ttype != StreamTokenizer.TT_EOF)
			throw new IOException("Felaktigt uttryck!");

		
		return result;
	}
	
	private Sexpr command() throws IOException{
		Sexpr result = null;
		
		if (st.sval.equals("quit"))
			result = new Quit();
		else if (st.sval.equals("vars"))
			result = new Vars();
		
		st.nextToken();
		
		return result;
	}
	
	private Sexpr assignment() throws IOException {
		Sexpr result = expression();

		while (st.ttype == '=') {
			if (st.nextToken() == StreamTokenizer.TT_WORD) {
				result = new Assignment(result, new Variable(st.sval));
				st.nextToken();
			}
			else
				throw new SyntaxException("Felaktig variabel");
		}
		
		return result;
	}
	
	private Sexpr expression() throws IOException {
		Sexpr result = term();

		
		while (st.ttype == '+' || st.ttype == '-') {
			int i = st.ttype;
			
			st.nextToken();
			
			if (i == '+')
				result = new Addition( result, term() );
			else
				result = new Subtraction( result, term() );
		}
			
		return result;
	}
	
	private Sexpr term() throws IOException {
		Sexpr prod = factor();
		
		while (st.ttype == '*' || st.ttype == '/') {
			int i = st.ttype;
			
			st.nextToken();
			
			if (i == '*')
				prod = new Multiplication( prod, factor() );
			else
				prod = new Division( prod, factor() );
		}
		
		return prod;
	}
	
	private Sexpr factor() throws IOException {
		Sexpr result = primary();
		
		while (st.ttype == '\'') {
			if (st.nextToken() == StreamTokenizer.TT_WORD) {
				result = new Differentiation(result, new Variable(st.sval));
				st.nextToken();
			}
		}
		
		return result;
	}
	
	private Sexpr primary() throws IOException {
		Sexpr result = null;
		if (st.ttype == '(') {
			st.nextToken(); // Läs förbi '(' 
			result = assignment();
			
			if (st.ttype != ')') {
				throw new SyntaxException("Saknar matchande högerparantes");
			} else
				st.nextToken();
			
		} else if (st.ttype == StreamTokenizer.TT_NUMBER) {
			result = new Constant(st.nval);
			st.nextToken();
		} else if (Unary.isUnaryIdent(st.sval, st.ttype)) {
			result = unary(st.sval, st.ttype);
		} else if (st.ttype == StreamTokenizer.TT_WORD){
			result = new Variable(st.sval);
			
			st.nextToken();
		} else {
			throw new SyntaxException("Oväntat tecken " + (char)st.ttype);
		}
		
		return result;
	}
	
	private Sexpr unary(String f, int c) throws IOException {	
		st.nextToken();
		
		if (f == null)
			f = String.valueOf( (char) c );
			
		if (f.equals("sin")) {
			return new Sin( primary() );
		} else if (f.equals("cos")) {
			return new Cos( primary() );
		} else if (f.equals("log")) {
			return new Log( primary() );
		} else if (f.equals("exp")) {
			return new Exp( primary() );
		} else if (f.equals("-")) {
			return new Negation( primary() );
		} else if (f.equals("&")) {
			if (st.ttype == StreamTokenizer.TT_WORD)
				return new Evaluation( primary() );
			else
				throw new SyntaxException("Felaktig variabel efter &");
			
		} else if (f.equals("\"")) {
			return new Quotation( primary() );
		}
		
		return null;
	}
}
