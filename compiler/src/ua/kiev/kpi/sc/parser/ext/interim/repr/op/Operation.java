package ua.kiev.kpi.sc.parser.ext.interim.repr.op;

import ua.kiev.kpi.sc.parser.ext.id.TypeSymbol;
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
	
	
	
	public static Operation OR() { return operation("OR", 2);}
	public static Operation AND() { return operation("AND", 2);}
	
	public static Operation ASSIGN() {return a();}
	
	public static Operation GT() { return c("GT");}
	public static Operation LT() { return c("LT");}
	public static Operation GE() { return c("GE");}
	public static Operation LE() { return c("LE");}
	
	public static Operation NEGATION() { return n();}
	public static Operation EQ() { return eq();}
	
	public static Operation MUL() { return w("MUL");}
	public static Operation ADD() { return w("ADD");}
	public static Operation SUB() { return w("SUB");}
	public static Operation DIV() { return w("DIV");}
	public static Operation MOD() { return w("MOD");}
	
	
	
	/**
	 * TODO: not working yet
	 */
	public static Operation ARRAY_ACCESS() { return operation("ARR_IDX", 2);}
	
	private static Operation a()
	{
		return new SingleType(2, "ASSIGN", null);
	}
	
	private static Operation c(String repr) 
	{
		return new CompareOp(repr);
	}
	
	private static Operation n()
	{
		return new SingleType(1, "NEG", TypeSymbol.T_BOOLEAN);
	}
	
	private static Operation w(final String repr)
	{		
		return new WideningOp(2, repr);
	}
	
	private static Operation eq() {
		return new AnyType(2, "EQ", TypeSymbol.T_BOOLEAN);
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

