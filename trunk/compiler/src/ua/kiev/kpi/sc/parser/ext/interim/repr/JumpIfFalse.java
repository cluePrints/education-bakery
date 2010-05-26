package ua.kiev.kpi.sc.parser.ext.interim.repr;

import ua.kiev.kpi.sc.parser.ext.interim.Translation;

public class JumpIfFalse implements Translation {
	public static JumpIfFalse getInstance() 
	{		
		return new JumpIfFalse();
	}
	
	@Override
	public String toString() {
		return "JMP_FALSE";
	}
}
