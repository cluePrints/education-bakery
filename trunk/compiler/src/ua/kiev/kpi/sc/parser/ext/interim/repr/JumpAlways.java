package ua.kiev.kpi.sc.parser.ext.interim.repr;

import ua.kiev.kpi.sc.parser.ext.interim.Translation;

public class JumpAlways implements Translation {
	public static JumpAlways getInstance() 
	{		
		return new JumpAlways();
	}
	
	@Override
	public String toString() {
		return "jmpAlways";
	}
}
