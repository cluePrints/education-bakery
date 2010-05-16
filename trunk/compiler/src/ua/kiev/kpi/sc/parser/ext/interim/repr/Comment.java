package ua.kiev.kpi.sc.parser.ext.interim.repr;
import ua.kiev.kpi.sc.parser.ext.interim.Translation.AbstractTranslation;

public class Comment extends AbstractTranslation {
	private String repr;

	public Comment(String txt) {
		super();
		this.repr = txt;
	}

	@Override
	public String toString() {
		return repr;
	}
}
