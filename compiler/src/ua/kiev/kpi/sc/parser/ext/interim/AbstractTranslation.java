package ua.kiev.kpi.sc.parser.ext.interim;
public abstract class AbstractTranslation implements Translation {
	private String comment;

	public int getArgsRequired() {
		return 0;
	}

	public String getComment() {
		return comment;
	}

	public void setComment(String comment) {
		this.comment = comment;
	}
	
}