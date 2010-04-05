package ua.kiev.kpi.sc.parser.ext.scope;

import ua.kiev.kpi.sc.parser.ext.id.Symbol;
import ua.kiev.kpi.sc.parser.ext.id.TypeSymbol;


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
		@Override
		public Symbol getDeclaredSymbol(String name) {
			return null;
		}
		
		@Override
		public Symbol getScopeHeaderSymbol() {
			return null;
		}
		
		@Override
		public Symbol getVisibleSymbol(String name) {
			return null;
		}
	};
}
