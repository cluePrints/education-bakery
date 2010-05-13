package ua.kiev.kpi.sc.parser.ext.scope;

import ua.kiev.kpi.sc.parser.ext.id.Symbol;
import ua.kiev.kpi.sc.parser.ext.id.TypeSymbol;
import ua.kiev.kpi.sc.parser.ext.id.VarSymbol;


public class RootScope extends Scope{
	public RootScope() {
		super(EMPTY_SCOPE);
		for (Symbol sym : TypeSymbol.reservedSymbols) {
			addIdentifier(sym);
		}
	}
	
	/**
	 * Empty scope not to check for null scope as a parent
	 */
	private static final Scope EMPTY_SCOPE = new Scope(null) {
		public void addIdentifier(Symbol id) {};
		protected <T extends Symbol> T getDeclaredSymbol(String name, java.lang.Class<T> clazz) {
			return null;
		};
		
		@Override
		public Symbol getScopeHeaderSymbol() {
			return null;
		}
		
		@Override
		public VarSymbol getVisibleVarSymbol(String name) {
			return null;
		};
	};
}
