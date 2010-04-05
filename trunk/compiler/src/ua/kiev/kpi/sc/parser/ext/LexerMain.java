package ua.kiev.kpi.sc.parser.ext;

import java.io.FileReader;
import java.io.PushbackReader;

import ua.kiev.kpi.sc.parser.lexer.Lexer;
import ua.kiev.kpi.sc.parser.node.EOF;
import ua.kiev.kpi.sc.parser.node.Token;

public class LexerMain {
	public static void main(String[] args) throws Exception{
		Lexer lexer = new Lexer (new PushbackReader(
	               new FileReader(args[0]), 1024));	    
	    Token t = null;
	    do {
	    	t = lexer.next();
	    	System.out.println(convertToken(t));
	    } while (!(t instanceof EOF));
	}
	
	private static String convertToken(Token t) {		
		String nodeText = t.getText();
		nodeText = nodeText.replace("\n", "\\n");
		nodeText = nodeText.replace("\t", "\\t");
		nodeText = nodeText.replace("\r", "\\r");
		
		String res = String.format("[%1$3s, %2$3s]    %3$-15s %4$s", 
				t.getLine(),
				t.getPos(),
				t.getClass().getSimpleName(),
				nodeText);
		return res;
	}
}
