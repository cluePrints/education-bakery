package ua.kiev.kpi.sc.parser.ext;

public class CompilerGUI {

	public static void main(String[] args) {
		LexerUI ui = new LexerUI();
		ui.init();
		LexerUI.isDebugMode = (args != null) 
					&& (args.length>0) 
					&& (args[0]=="--debug");
	}
}
