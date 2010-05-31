package ua.kiev.kpi.sc.parser.ext.interim.repr;

import ua.kiev.kpi.sc.parser.ext.interim.AbstractTranslation;

public class JumpAlways extends AbstractTranslation {
	public static JumpAlways getInstance() 
	{		
		return new JumpAlways();
	}
	
	@Override
	public int getArgsRequired() {
		return 1;
	}
	
	@Override
	public String toString() {
		return "JMP";
	}
}
