package ua.kiev.kpi.sc.parser.ext.interim.repr;
import ua.kiev.kpi.sc.parser.ext.interim.Translation;
import ua.kiev.kpi.sc.parser.ext.interim.Translation.AbstractTranslation;
import ua.kiev.kpi.sc.parser.node.Node;

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
