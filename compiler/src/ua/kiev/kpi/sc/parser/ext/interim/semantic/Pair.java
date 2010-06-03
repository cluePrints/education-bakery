package ua.kiev.kpi.sc.parser.ext.interim.semantic;

import ua.kiev.kpi.sc.parser.ext.id.TypeSymbol;

public class Pair {
	public TypeSymbol type;
	public Pair(TypeSymbol type, Object value) {
		super();
		this.type = type;
		this.value = value;
	}
	public Object value;	
	
	@Override
	public String toString() {
		return type.getName()+": "+String.valueOf(value);
	}
}
