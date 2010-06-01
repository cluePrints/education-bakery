package ua.kiev.kpi.sc.parser.ext.checker;

import java.util.List;

import ua.kiev.kpi.sc.parser.analysis.DepthFirstAdapter;
import ua.kiev.kpi.sc.parser.ext.ScopeTreeChecker;
import ua.kiev.kpi.sc.parser.ext.interim.Translation;
import ua.kiev.kpi.sc.parser.ext.interim.walker.InterimRepresentationBuilder;
import ua.kiev.kpi.sc.parser.node.AAssignOperator;

public class SemanticChecker extends DepthFirstAdapter{
	private ScopeTreeChecker scopeTreeBuilder;
	private InterimRepresentationBuilder interimReprBuilder;
	public SemanticChecker(ScopeTreeChecker scopeTreeBuilder,
			InterimRepresentationBuilder interimReprBuilder) {
		super();
		this.scopeTreeBuilder = scopeTreeBuilder;
		this.interimReprBuilder = interimReprBuilder;
	}
	
	
	@Override
	public void inAAssignOperator(AAssignOperator node) {
		List<Translation> t = interimReprBuilder.getFilteredPolizStack();
		t.clear();
	}
	
	@Override
	public void outAAssignOperator(AAssignOperator node) {
		List<Translation> t = interimReprBuilder.getFilteredPolizStack();
		t.clear();
	}
}
