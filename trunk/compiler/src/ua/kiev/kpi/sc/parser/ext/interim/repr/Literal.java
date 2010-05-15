package ua.kiev.kpi.sc.parser.ext.interim.repr;

import ua.kiev.kpi.sc.parser.ext.interim.Translation.AbstractTranslation;

public class Literal extends AbstractTranslation {
	private String repr;

	public Literal(String repr) {
		super();
		this.repr = repr;
	}

	@Override
	public String toString() {
		return repr;
	}
}
