package ua.kiev.kpi.sc.parser.ext.id;

import java.util.Collections;
import java.util.LinkedList;
import java.util.List;

public class TypeSymbol extends Symbol{	
	public static final List<Symbol> reservedSymbols;
	static {
		LinkedList<Symbol> tmp = new LinkedList<Symbol>();
		tmp.add(new ReservedTypeSymbol("void"));
		tmp.add(new ReservedTypeSymbol("int"));
		tmp.add(new ReservedTypeSymbol("float"));
		tmp.add(new ReservedTypeSymbol("double"));
		tmp.add(new ReservedTypeSymbol("String"));
		tmp.add(new ReservedTypeSymbol("Integer"));
		ReservedTypeSymbol boolType = new ReservedTypeSymbol("boolean");
		tmp.add(new ReservedValueSymbol("true", boolType));
		tmp.add(new ReservedValueSymbol("false", boolType));

		reservedSymbols = Collections.unmodifiableList(tmp);
	}
	
	private static class ReservedValueSymbol extends VarSymbol {		
		public ReservedValueSymbol(String name, ReservedTypeSymbol type) {
			super();
			this.name = name;
			this.type = type.name;
		}

		public final int getDefLine() {
			return -1;
		}
		
		public final int getDefPos() {
			return -1;
		};
	}
	
	private static class ReservedTypeSymbol extends TypeSymbol {
		public ReservedTypeSymbol(String name) {
			super();
			this.name = name;
		}

		public final int getDefLine() {
			return -1;
		}
		
		public final int getDefPos() {
			return -1;
		};
	}
}
