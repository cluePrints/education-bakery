package ua.kiev.kpi.sc.parser.ext.interim.repr;

import ua.kiev.kpi.sc.parser.ext.id.TypeSymbol;
import ua.kiev.kpi.sc.parser.ext.interim.AbstractTranslation;

public class Literal extends AbstractTranslation {
	private String repr;
	private TypeSymbol type;
	
	public Literal(String repr, TypeSymbol type) {
		super();
		this.repr = repr;
		this.type = type;
	}
	
	public TypeSymbol getType() {
		return type;
	}

	@Override
	public String toString() {
		return repr;
	}
}
