package ua.kiev.kpi.sc.parser.ext;

import java.io.FileReader;
import java.io.PushbackReader;

import ua.kiev.kpi.sc.parser.lexer.Lexer;
import ua.kiev.kpi.sc.parser.node.Start;
import ua.kiev.kpi.sc.parser.parser.Parser;

public class ParserMain {
	public static void main(String[] args) throws Exception{
		LexerMain.main(args);
		Lexer lexer = new Lexer (new PushbackReader(
	               new FileReader(args[0]), 1024));
	    Parser p = new Parser(lexer);
	    Start st = p.parse();
	    LoggingInterpreter log = new LoggingInterpreter(System.out);
	    st.apply(log);
	}
}
