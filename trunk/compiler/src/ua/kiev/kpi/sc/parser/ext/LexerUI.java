package ua.kiev.kpi.sc.parser.ext;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.GridLayout;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.io.DataInputStream;
import java.io.EOFException;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.PrintWriter;
import java.io.PushbackReader;
import java.io.StringReader;
import java.io.StringWriter;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTabbedPane;
import javax.swing.JTable;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.JTree;
import javax.swing.tree.DefaultTreeModel;
import javax.swing.tree.TreePath;
import javax.swing.tree.TreeSelectionModel;

import ua.kiev.kpi.sc.parser.ext.rules.ReduceRulesMapping;
import ua.kiev.kpi.sc.parser.ext.scope.Scope;
import ua.kiev.kpi.sc.parser.ext.ui.TreeNodeAdaptor;
import ua.kiev.kpi.sc.parser.lexer.Lexer;
import ua.kiev.kpi.sc.parser.lexer.LexerException;
import ua.kiev.kpi.sc.parser.node.EOF;
import ua.kiev.kpi.sc.parser.node.Start;
import ua.kiev.kpi.sc.parser.node.Token;
import ua.kiev.kpi.sc.parser.parser.Parser;
import ua.kiev.kpi.sc.parser.parser.ParserException;

public class LexerUI extends JFrame {
	private JTextArea taCode;
	private JTextArea taLexerResult;
	private JTextArea taParsedTree;
	private JTextArea taErrors;
	private JTextArea taRR;
	private JTable tblActionTable;
	private JTable tblGotoTable;
	private JButton btnExecute;
	private JTabbedPane tabPane = new JTabbedPane();
	private JTree trScopes;
	private JTextField tfVarName;
	private JButton btnScopedSearch;
	private Scope scopeTreeRoot;
	public static boolean isDebugMode;
	private void init() {

		setLayout(new BorderLayout());
		addWindowListener(new WindowAdapter() {
			@Override
			public void windowClosing(WindowEvent e) {
				System.exit(0);
			}
		});
		taCode = new JTextArea("", 50, 50);
		taLexerResult = new JTextArea("<results>", 50, 45);
		taLexerResult.setEditable(false);
		taLexerResult.setBackground(Color.GRAY);
		
		taParsedTree = new JTextArea("<results>", 50, 45);
		taParsedTree.setEditable(false);
		taParsedTree.setBackground(Color.GRAY);
		
		taErrors = new JTextArea("<errors>", 50, 45);
		taErrors.setEditable(false);
		taErrors.setBackground(Color.GRAY);
		
		taRR = new JTextArea("<RR>", 50, 45);
		taRR.setEditable(false);
		taRR.setBackground(Color.GRAY);
		
		trScopes = new JTree();
		
		
		btnExecute = new JButton("Execute");
		taCode.setAutoscrolls(true);
		add(tabPane, BorderLayout.EAST);
		tabPane.addTab("Lexer", new JScrollPane(taLexerResult));
		tabPane.addTab("RR", new JScrollPane(taRR));
		//tabPane.addTab("Parsed tree", new JScrollPane(taParsedTree));
		
		tblActionTable = new JTable(ReduceRulesMapping.createActionModel());
		tblGotoTable = new JTable(ReduceRulesMapping.createGotoModel());
		
		JPanel tablesPane = new JPanel();
		tablesPane.setLayout(new GridLayout(1,2));
		tablesPane.add(new JScrollPane(tblActionTable));
		tablesPane.add(new JScrollPane(tblGotoTable));
		//tabPane.addTab("ActionTable", tablesPane);
		
		
		tfVarName = new JTextField(45);
		btnScopedSearch = new JButton("Search for variable declaration scope");
		btnScopedSearch.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				returnSearchResult("");
				Scope selectedScope = null;
				TreePath path = trScopes.getSelectionPath();
				if (path != null) {
					TreeNodeAdaptor ad = ((TreeNodeAdaptor) path.getLastPathComponent());
					if (ad.isScope()) {
						selectedScope = ad.scope();
					}
				}
				if (selectedScope != null) {
					Scope result = selectedScope.getSymbolDeclarationScope(tfVarName.getText());
					if (result == null) {
						returnSearchResult("Variable '"+tfVarName.getText()+"' declaration scope not found.");
					} else {
						returnSearchResult("Variable '"+tfVarName.getText()+"' declaration scope starts at line "+result.getDefLine());
					}					
				}
				
				if (selectedScope == null) {
					returnSearchResult("Please type in variable name and select some of the scopes in the scope tree");
				}
			}
			
			private void returnSearchResult(String res) {
				setTitle(res);
			}
		});
		
		JPanel p = new JPanel();
		p.setLayout(new BorderLayout());
		p.add(new JScrollPane(trScopes));
		trScopes.getSelectionModel().setSelectionMode(TreeSelectionModel.SINGLE_TREE_SELECTION);
		p.add(tfVarName, BorderLayout.NORTH);
		p.add(btnScopedSearch, BorderLayout.SOUTH);
		
		tabPane.addTab("Scopes", p);
		
		tabPane.addTab("Errors", new JScrollPane(taErrors));
		//add(new JScrollPane(taResult), BorderLayout.EAST);
		add(new JScrollPane(taCode), BorderLayout.WEST);
		add(btnExecute, BorderLayout.SOUTH);

		preloadTestCase();
		
		pack();
		setVisible(true);		
		btnExecute.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				try {
					Parser.result = "";
					taLexerResult.setText("");
					taRR.setText("");
					taParsedTree.setText("");
					tabPane.setSelectedIndex(0);									
					
					taLexerResult.setText(runLexer());					
					taParsedTree.setText(parseTree());
					taRR.setText(Parser.result);
					
					scopeTreeRoot = runScoper();
					trScopes.setModel(new DefaultTreeModel(new TreeNodeAdaptor(scopeTreeRoot)));
					
					runScopeChecker(scopeTreeRoot);
					tabPane.setSelectedIndex(2);
				} catch (Exception ex) {
					tabPane.setSelectedIndex(3);
					
					StringWriter buf = new StringWriter();
					if (isDebugMode) {
						PrintWriter wr = new PrintWriter(buf);
						ex.printStackTrace(wr);
					}
					taErrors.setText(ex.getLocalizedMessage()+"\n"+buf.toString());
				}
			}
		});
	}

	public static void main(String[] args) {
		LexerUI ui = new LexerUI();
		ui.init();
		isDebugMode = (args != null) 
					&& (args.length>0) 
					&& (args[0]=="--debug");
	}

	private static String convertToken(Token t) {
		String nodeText = t.getText();
		nodeText = nodeText.replace("\n", "\\n");
		nodeText = nodeText.replace("\t", "\\t");
		nodeText = nodeText.replace("\r", "\\r");

		String res = String.format("[%1$3s, %2$3s]    %3$-15s %4$s", t
				.getLine(), t.getPos(), t.getClass().getSimpleName(), nodeText);
		return res;
	}

	private String parseTree() throws ParserException,
			LexerException, IOException {
		StringBuilder b = new StringBuilder();
		Lexer lexer = new Lexer(new PushbackReader(new StringReader(taCode
				.getText()), 1024));
		Parser p = new Parser(lexer);
		Start st = p.parse();

		LoggingInterpreter log = new LoggingInterpreter();
		st.apply(log);
		b.append(log.toString());
		return b.toString();
	}

	private String runLexer() {
		try {
			StringBuilder b = new StringBuilder();
			Lexer lexer = new Lexer(new PushbackReader(new StringReader(taCode
					.getText()), 1024));
	
			Token t = null;
	
			do {
				t = lexer.next();
				b.append(convertToken(t));
				b.append("\n");
			} while (!(t instanceof EOF));
			return b.toString();
		} catch (Exception ex){
			throw new RuntimeException(ex);
		}
	}
	
	private Scope runScoper() throws Exception {
		Lexer lexer = new Lexer(new PushbackReader(new StringReader(taCode.getText()), 1024));
		Parser p = new Parser(lexer);
		Start st = p.parse();
		ScopeTreeBuilder intv = new ScopeTreeBuilder();
		st.apply(intv);
		return intv.getRootScope();
	}
	
	private void runScopeChecker(Scope rootScope) throws Exception {
		Lexer lexer = new Lexer(new PushbackReader(new StringReader(taCode.getText()), 1024));
		Parser p = new Parser(lexer);
		Start st = p.parse();
		ScopeTreeChecker a = new ScopeTreeChecker(rootScope);
		st.apply(a);
	}
	
	private void preloadTestCase() {
		try {
			DataInputStream str = new DataInputStream(new FileInputStream(new File("testCase.txt")));
			StringBuffer b = new StringBuffer();
			try{
				String line;
				do {
					line = str.readLine();
					if (line == null) {
						break;
					}
					b.append(line).append("\n");
				} while (line != null);
			} catch (EOFException eof) {
				// do nothing
			}
			taCode.setText(b.toString());
		} catch (Exception ex) {
			throw new MyException(ex);
		}
	}
}
