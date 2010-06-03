package ua.kiev.kpi.sc.parser.ext.interim.repr;
import ua.kiev.kpi.sc.parser.ext.interim.ScopeAware;
import ua.kiev.kpi.sc.parser.ext.interim.Translation;
import ua.kiev.kpi.sc.parser.ext.scope.Scope;
import ua.kiev.kpi.sc.parser.node.Node;

public class TypePointer implements Translation, ScopeAware {
	private String repr;
	private Scope scope;
	
	public Scope getScope() {
		return scope;
	}

	public void setScope(Scope scope) {
		this.scope = scope;
	}
	public TypePointer(Node node) {
		super();
		this.repr = node.toString().trim();
	}
	
	public String getName()
	{
		return repr;
	}

	@Override
	public String toString() {
		return repr+"*";
	}
}
