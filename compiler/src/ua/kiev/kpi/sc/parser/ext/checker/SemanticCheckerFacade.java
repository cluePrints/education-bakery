package ua.kiev.kpi.sc.parser.ext.checker;

import ua.kiev.kpi.sc.parser.analysis.AnalysisDelegator;
import ua.kiev.kpi.sc.parser.ext.ScopeTreeChecker;
import ua.kiev.kpi.sc.parser.ext.interim.walker.InterimRepresentationBuilder;
import ua.kiev.kpi.sc.parser.ext.scope.RootScope;

public class SemanticCheckerFacade extends AnalysisDelegator {
	private ScopeTreeChecker sc;
	private InterimRepresentationBuilder i;
	private SemanticChecker c;
	public SemanticCheckerFacade(RootScope scope){		
		super();
		this.sc = new ScopeTreeChecker(scope);
		this.i = new InterimRepresentationBuilder();
		this.c = new SemanticChecker(sc, i);
		analysis.add(sc);
		analysis.add(i);
		analysis.add(c);
	}
	
	
}
