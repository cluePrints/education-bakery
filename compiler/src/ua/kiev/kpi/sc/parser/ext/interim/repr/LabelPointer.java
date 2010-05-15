package ua.kiev.kpi.sc.parser.ext.interim.repr;

import ua.kiev.kpi.sc.parser.ext.interim.Translation;

public class LabelPointer implements Translation {
	private int labelNum;

	public LabelPointer(int labelNum) {
		super();
		this.labelNum = labelNum;
	}
	
	@Override
	public String toString() {
		return "LABEL"+labelNum+"*";
	}
}
