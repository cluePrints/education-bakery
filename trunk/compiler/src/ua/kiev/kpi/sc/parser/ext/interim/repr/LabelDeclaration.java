package ua.kiev.kpi.sc.parser.ext.interim.repr;

import java.util.concurrent.atomic.AtomicInteger;

import ua.kiev.kpi.sc.parser.ext.interim.Translation.AbstractTranslation;

public class LabelDeclaration extends AbstractTranslation {
	private static final AtomicInteger count = new AtomicInteger(0);
	private int num;	

	private LabelDeclaration(int num) {
		super();
		this.num = num;
	}

	public static LabelDeclaration getInstance() 
	{		
		return new LabelDeclaration(count.getAndIncrement());
	}
	
	public static void reset()
	{
		count.set(0);
	}
		
	public int getNum() {
		return num;
	}
	
	public LabelPointer getPointer()
	{
		return new LabelPointer(this.num);
	}
	
	@Override
	public String toString() {
		return ":LABEL"+num;
	}
}
