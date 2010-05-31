package ua.kiev.kpi.sc.parser.ext.interim;


public interface Translation {
	public static Translation EMPTY = new Translation() {
		public String toString() {
			return "<>";
		};
	};
	
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
	
}
