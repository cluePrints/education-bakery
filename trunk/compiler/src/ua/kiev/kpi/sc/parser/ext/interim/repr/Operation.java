package ua.kiev.kpi.sc.parser.ext.interim.repr;

import ua.kiev.kpi.sc.parser.ext.interim.Translation.AbstractTranslation;

public class Operation extends AbstractTranslation {
	public static Operation ASSIGN() {return operation("ASSIGN");}
	public static Operation DEF_VAR() { return operation("DEF_VAR");}
	public static Operation DEF_ARR() { return operation("DEF_ARR");}
	public static Operation MOD_FINAL() { return operation("MOD_FINAL");}
	public static Operation ADD() { return operation("ADD");}
	public static Operation SUB() { return operation("SUB");}
	public static Operation RETURN() { return operation("RETURN");}
	public static Operation EMPTY_RETURN() { return operation("RETURN;}");}
	public static Operation OR() { return operation("OR");}
	public static Operation AND() { return operation("AND");}
	public static Operation GT() { return operation("GT");}
	public static Operation LT() { return operation("LT");}
	public static Operation GE() { return operation("GE");}
	public static Operation LE() { return operation("LE");}
	public static Operation MUL() { return operation("MUL");}
	public static Operation DIV() { return operation("DIV");}
	public static Operation MOD() { return operation("MOD");}
	public static Operation NEGATION() { return operation("NEG");}
	public static Operation ARRAY_ACCESS() { return operation("ARRAY_ACCESS");}
	public static Operation FUNC_CALL() { return operation("CALL");}
	
	
	private static Operation operation(final String repr) {
		return new Operation() {
			@Override
			public String toString() {
				return repr;
			}
		};			
	}
}

