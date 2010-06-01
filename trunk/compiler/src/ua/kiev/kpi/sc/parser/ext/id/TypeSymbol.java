package ua.kiev.kpi.sc.parser.ext.id;

import java.util.Collections;
import java.util.LinkedList;
import java.util.List;

public class TypeSymbol extends Symbol{	
	public static final List<Symbol> reservedSymbols;
	public static final TypeSymbol T_VOID = new ReservedTypeSymbol("void");
	public static final TypeSymbol T_INT = new ReservedTypeSymbol("int");
	public static final TypeSymbol T_FLOAT = new ReservedTypeSymbol("float");
	public static final TypeSymbol T_DOUBLE = new ReservedTypeSymbol("double");
	public static final TypeSymbol T_STRING = new ReservedTypeSymbol("String");
	public static final TypeSymbol T_INTEGER = new ReservedTypeSymbol("Integer");
	public static final TypeSymbol T_BOOLEAN = new ReservedTypeSymbol("boolean");
	static {
		LinkedList<Symbol> tmp = new LinkedList<Symbol>();
		tmp.add(T_INT);
		tmp.add(T_VOID);
		tmp.add(T_FLOAT);
		tmp.add(T_DOUBLE);
		tmp.add(T_STRING);
		tmp.add(T_INTEGER);
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
		
		@Override
		public String toString() {
			return name;
		}
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
		
		@Override
		public String toString() {
			return name;
		}
	}
}
