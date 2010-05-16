package ua.kiev.kpi.sc.parser.ext.interim;


public interface Translation {
	public static Translation EMPTY = new Translation() {
		public String toString() {
			return "<>";
		};
	};
	
	public abstract class AbstractTranslation implements Translation {
		private String comment;
		private int argsRequired;

		public int getArgsRequired() {
			return argsRequired;
		}

		public void setArgsRequired(int argsRequired) {
			this.argsRequired = argsRequired;
		}

		public String getComment() {
			return comment;
		}

		public void setComment(String comment) {
			this.comment = comment;
		}
		
	}
	
}
