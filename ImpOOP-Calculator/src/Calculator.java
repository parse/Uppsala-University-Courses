import java.util.TreeMap;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;

import Exceptions.DiffException;
import Exceptions.EvaluationException;
import Exceptions.QuitException;
import Exceptions.SyntaxException;

import symbolic.*;

public class Calculator {	
	public static void main(String[] args) {
		InputStream in;

		try {
			in = new FileInputStream("filnamn.txt"); // Ifall du vill ladda en fil med uttryck
		} catch (FileNotFoundException exc) {
			in = System.in;
		}
		
		TreeMap<String,Sexpr> t = new TreeMap<String,Sexpr>();
		Parser p = new Parser(in, t);
				
		while (true)
			try {
				System.out.print(">> ");
				Sexpr expr = p.statement();
				System.out.println("Inläst uttryck: " + expr);
				Sexpr eval = expr.eval(t);
				p.setAnswer(eval);
				System.out.println( eval );
			} catch (IOException e) {
				System.out.println("Inputfel: " + e.getMessage());
				
				p.reset(System.in);
			} catch (QuitException qExc) {
				System.out.println(qExc.getMessage());
				
				try {
					in.close();
				} catch (IOException e) {
					System.out.println( e.getMessage() );
				} finally {
					System.exit(1);
				}
				
			} catch (SyntaxException sExc) {
				System.out.println("Syntaxfel: " + sExc.getMessage());
				
				p.reset(System.in);
			} catch (DiffException dExc) {
				System.out.println("Deriveringsfel: " + dExc.getMessage());
				
				p.reset(System.in);
			} catch (EvaluationException eExc) {
				System.out.println("Evalueringsfel: " + eExc.getMessage());
				
				p.reset(System.in);
			}
	}
}