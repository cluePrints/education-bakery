package ua.kiev.kpi.sc.parser.ext.interim;


public interface Translation {
	public static Translation EMPTY = new Translation() {
		public String toString() {
			return "<>";
		};
	};	
}
