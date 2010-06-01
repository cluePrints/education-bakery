package ua.kiev.kpi.sc.parser.ext;

import java.awt.Adjustable;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
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
import java.io.Reader;
import java.io.StringReader;
import java.io.StringWriter;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JPanel;
import javax.swing.JScrollBar;
import javax.swing.JScrollPane;
import javax.swing.JTabbedPane;
import javax.swing.JTable;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.JTree;
import javax.swing.tree.DefaultTreeModel;
import javax.swing.tree.TreePath;
import javax.swing.tree.TreeSelectionModel;

import ua.kiev.kpi.sc.parser.ext.interim.walker.InterimRepresentationBuilder;
import ua.kiev.kpi.sc.parser.ext.rules.ReduceRulesMapping;
import ua.kiev.kpi.sc.parser.ext.scope.Scope;
import ua.kiev.kpi.sc.parser.ext.ui.ActionTableModel;
import ua.kiev.kpi.sc.parser.ext.ui.ActionTableRenderer;
import ua.kiev.kpi.sc.parser.ext.ui.HelpFrame;
import ua.kiev.kpi.sc.parser.ext.ui.Preferences;
import ua.kiev.kpi.sc.parser.ext.ui.TerminalCodesModel;
import ua.kiev.kpi.sc.parser.ext.ui.TreeNodeAdaptor;
import ua.kiev.kpi.sc.parser.lexer.Lexer;
import ua.kiev.kpi.sc.parser.lexer.LexerException;
import ua.kiev.kpi.sc.parser.node.EOF;
import ua.kiev.kpi.sc.parser.node.Start;
import ua.kiev.kpi.sc.parser.node.Token;
import ua.kiev.kpi.sc.parser.parser.Parser;
import ua.kiev.kpi.sc.parser.parser.ParserException;

public class LexerUI extends JFrame {
	private Parser parser;
	private Start syntaxTree;
	
	private JTextArea taCode;
	private JTextArea taLexerResult;
	private JTextArea taParsedTree;
	private JTextArea taErrors;
	private JTextArea taRR;
	private JTextArea taPoliz;
	private JTable tblActionTable;
	private JTable terminalCodesTable;
	private JButton btnExecute;
	private JTabbedPane tabPane = new JTabbedPane();
	private JTree trScopes;
	private JTextField tfVarName;
	private JButton btnScopedSearch;
	private Scope scopeTreeRoot;
	void init() {		
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
		
		taPoliz = new JTextArea("<Poliz here>", 50, 45);
		taPoliz.setEditable(false);
		taPoliz.setBackground(Color.GRAY);
		
		trScopes = new JTree();
		
		
		btnExecute = new JButton("Execute");
		taCode.setAutoscrolls(true);		
		tabPane.addTab("Lexer", new JScrollPane(taLexerResult));
		tabPane.addTab("RR", new JScrollPane(taRR));		
		ActionTableModel model = new ActionTableModel();
		tblActionTable = new JTable(model);
		model.applyColumnHeaders(tblActionTable.getColumnModel());
		tblActionTable.setDefaultRenderer(Object.class, new ActionTableRenderer());
		
		tblActionTable.setAutoResizeMode (JTable.AUTO_RESIZE_OFF);
		JScrollPane pane = new JScrollPane(tblActionTable);
		pane.setHorizontalScrollBar(new JScrollBar(Adjustable.HORIZONTAL));
		tblActionTable.setAutoResizeMode (JTable.AUTO_RESIZE_OFF);
		
		terminalCodesTable = new JTable(new TerminalCodesModel(model));
		tabPane.addTab("Terminals", new JScrollPane(terminalCodesTable));
		

		
		
		tabPane.addTab("ActionTable", pane);
		
		
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
		
		tabPane.addTab("Debug tree", new JScrollPane(taParsedTree));
		tabPane.addTab("Poliz", new JScrollPane(taPoliz));
		
		tabPane.addTab("Errors", new JScrollPane(taErrors));
		//add(new JScrollPane(taResult), BorderLayout.EAST);
		JPanel leftPanel = new JPanel();
		leftPanel.setLayout(new BorderLayout());
		leftPanel.add(new JScrollPane(taCode));
		leftPanel.add(btnExecute, BorderLayout.SOUTH);
		
		
		JMenuBar bar = new JMenuBar();
		JMenu helpMenu = new JMenu("Help");		
		JMenuItem grammarItem = new JMenuItem("Grammar rules");
		final HelpFrame hlpFrame = new HelpFrame();
		grammarItem.addActionListener(new ActionListener() {
			
			public void actionPerformed(ActionEvent e) {
				hlpFrame.setVisible(true);	
			}
		});
		helpMenu.add(grammarItem);
		bar.add(helpMenu);
		leftPanel.add(bar, BorderLayout.NORTH);
			
		setLayout(new GridLayout(1,2));
		add(leftPanel);
		add(tabPane);

		preloadTestCase();
		
		pack();
		setVisible(true);		
		btnExecute.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				try {
					taLexerResult.setText("");
					taRR.setText("");
					taPoliz.setText("");
					taParsedTree.setText("");
					tabPane.setSelectedIndex(0);									
					
					taLexerResult.setText(Lexer.run(new StringReader(taCode
							.getText())));					
					taParsedTree.setText(parseTree());
					StringBuilder rulesTriggered = new StringBuilder();
					for (Integer ind : Parser.triggeredRulesInd)
					{
						String ruleRepr = ReduceRulesMapping.getRepresentation(ind);
						rulesTriggered.append(ruleRepr);
						rulesTriggered.append("\n");
					}
					String rulesTriggeredStr;
					if (Preferences.lookupRulesByIndexes) {
						rulesTriggeredStr = rulesTriggered.toString();
					} else {
						rulesTriggeredStr = Parser.readableRulesTriggered;
					}
					taRR.setText(rulesTriggeredStr);
					
					
					InterimRepresentationBuilder w = new InterimRepresentationBuilder();
					syntaxTree.apply(w);
					taPoliz.setText(w.toString());
					
					scopeTreeRoot = runScoper();
					trScopes.setModel(new DefaultTreeModel(new TreeNodeAdaptor(scopeTreeRoot)));
					
					runScopeChecker(scopeTreeRoot);
					tabPane.setSelectedIndex(6);
				} catch (Exception ex) {
					tabPane.setSelectedIndex(7);
					
					StringWriter buf = new StringWriter();
					if (!Preferences.productionMode) {
						PrintWriter wr = new PrintWriter(buf);
						ex.printStackTrace(wr);
						ex.printStackTrace();
					}
					taErrors.setText(ex.getLocalizedMessage()+"\n"+buf.toString());
				}
			}
		});
	}

	private String parseTree() throws ParserException,
			LexerException, IOException {
		StringBuilder b = new StringBuilder();
		Lexer lexer = new Lexer(new PushbackReader(new StringReader(taCode
				.getText()), 1024));
		parser = new Parser(lexer);
		syntaxTree = parser.parse();

		LoggingInterpreter log = new LoggingInterpreter();
		syntaxTree.apply(log);
		b.append(log.toString());
		return b.toString();
	}
	
	private Scope runScoper() throws Exception {
		ScopeTreeBuilder intv = new ScopeTreeBuilder();
		syntaxTree.apply(intv);
		return intv.getRootScope();
	}
	
	private void runScopeChecker(Scope rootScope) throws Exception {
		ScopeTreeChecker a = new ScopeTreeChecker(rootScope);
		syntaxTree.apply(a);
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
