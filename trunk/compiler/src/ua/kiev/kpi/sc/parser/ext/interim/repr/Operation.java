package ua.kiev.kpi.sc.parser.ext.interim.repr;

import ua.kiev.kpi.sc.parser.ext.interim.Translation.AbstractTranslation;

public class Operation extends AbstractTranslation {
	public static Operation ASSIGN() {return operation("ASSIGN", 2);}
	public static Operation DEF_VAR() { return operation("DEF_VAR", 2);}
	public static Operation DEF_ARR() { return operation("DEF_ARR", 2);}
	public static Operation MOD_FINAL() { return operation("MOD_FINAL", 1);}
	public static Operation ADD() { return operation("ADD", 2);}
	public static Operation SUB() { return operation("SUB", 2);}
	public static Operation RETURN() { return operation("RETURN", 1);}
	public static Operation EMPTY_RETURN() { return operation("RETURN;",1);}
	public static Operation OR() { return operation("OR", 2);}
	public static Operation AND() { return operation("AND", 2);}
	public static Operation GT() { return operation("GT", 2);}
	public static Operation LT() { return operation("LT", 2);}
	public static Operation GE() { return operation("GE", 2);}
	public static Operation LE() { return operation("LE", 2);}
	public static Operation MUL() { return operation("MUL", 2);}
	public static Operation DIV() { return operation("DIV", 2);}
	public static Operation MOD() { return operation("MOD", 2);}
	public static Operation NEGATION() { return operation("NEG", 1);}
	public static Operation ARRAY_ACCESS() { return operation("ARRAY_ACCESS", 2);}
	public static Operation FUNC_CALL() { return operation("CALL", 2);}
	public static Operation FUNC_DECL() { return operation("DECL", 2);}
	
	private static Operation operation(final String repr, final int args) {
		return new Operation() {
			@Override
			public int getArgsRequired() {
				return args;
			}
			
			@Override
			public String toString() {
				return repr;
			}
		};			
	}
}

