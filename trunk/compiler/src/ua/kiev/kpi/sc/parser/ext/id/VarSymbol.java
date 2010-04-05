package ua.kiev.kpi.sc.parser.ext.id;

public class VarSymbol extends Symbol{
	protected String type;
	protected boolean assignable = true;
	protected boolean isArray;

	public boolean isArray() {
		return isArray;
	}

	public void setArray(boolean isArray) {
		this.isArray = isArray;
	}

	public void setAssignable(boolean assignable) {
		this.assignable = assignable;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type.trim();
	}
	
	public boolean isAssignable() {
		return assignable;
	}
}
