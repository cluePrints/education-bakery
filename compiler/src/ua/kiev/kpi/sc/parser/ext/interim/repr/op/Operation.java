package ua.kiev.kpi.sc.parser.ext.interim.repr.op;

import ua.kiev.kpi.sc.parser.ext.interim.AbstractTranslation;

public class Operation extends AbstractTranslation {
	public static int NO_ARGUMENTS=0;
	public static int UNBOUNDED=-1; 	/*depends on special condition*/
	
	public static Operation DEF_VAR() { return operation("DEF_VAR", 2);}
	public static Operation DEF_ARR() { return operation("DEF_ARR", 2);}
	public static Operation FUNC_DECL() { return operation("DEF_FUNC", -1);}
	public static Operation CLASS_DECL() { return operation("DEF_ÑLASS", -1);}
	public static Operation FUNC_CALL() { return operation("CALL", -1);}
	
	public static Operation MOD_FINAL() { return operation("MOD_CONST", 1);}
	public static Operation RETURN() { return operation("RETURN", 1);}
	public static Operation EMPTY_RETURN() { return operation("RETURN;",0);}
	public static Operation NEGATION() { return operation("NEG", 1);}
	
	public static Operation ASSIGN() {return operation("ASSIGN", 2);}
	
	public static Operation OR() { return operation("OR", 2);}
	public static Operation AND() { return operation("AND", 2);}
	public static Operation GT() { return operation("GT", 2);}
	public static Operation LT() { return operation("LT", 2);}
	public static Operation GE() { return operation("GE", 2);}
	public static Operation LE() { return operation("LE", 2);}
	
	public static Operation MUL() { return w("MUL", 2);}
	public static Operation ADD() { return w("ADD", 2);}
	public static Operation SUB() { return w("SUB", 2);}
	public static Operation DIV() { return w("DIV", 2);}
	public static Operation MOD() { return w("MOD", 2);}
	
	
	
	/**
	 * TODO: not working yet
	 */
	public static Operation ARRAY_ACCESS() { return operation("ARR_IDX", 2);}
	
	private static Operation w(final String repr, final int args)
	{		
		return new WideningOp(args, repr);
	}
	
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

