package ua.kiev.kpi.sc.parser.ext.interim;

import ua.kiev.kpi.sc.parser.ext.scope.Scope;

public interface ScopeAware {
	void setScope(Scope s);
	Scope getScope();
}
