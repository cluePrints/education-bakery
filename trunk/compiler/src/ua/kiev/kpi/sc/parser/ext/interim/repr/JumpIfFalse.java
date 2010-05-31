package ua.kiev.kpi.sc.parser.ext.interim.repr;

import ua.kiev.kpi.sc.parser.ext.interim.AbstractTranslation;

public class JumpIfFalse extends AbstractTranslation{
	public static JumpIfFalse getInstance() 
	{		
		return new JumpIfFalse();
	}
	
	@Override
	public int getArgsRequired() {
		return 2;
	}
	
	@Override
	public String toString() {
		return "JMP_FALSE";
	}
}
