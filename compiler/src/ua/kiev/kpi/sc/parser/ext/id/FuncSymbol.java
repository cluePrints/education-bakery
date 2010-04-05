package ua.kiev.kpi.sc.parser.ext.id;

import java.util.List;

public class FuncSymbol extends Symbol{
	private List<VarSymbol> params;
	private TypeSymbol returnType;
	public List<VarSymbol> getParams() {
		return params;
	}
	public void setParams(List<VarSymbol> params) {
		this.params = params;
	}
	public TypeSymbol getReturnType() {
		return returnType;
	}
	public void setReturnType(TypeSymbol returnType) {
		this.returnType = returnType;
	}
}
