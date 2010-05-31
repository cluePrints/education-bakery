package ua.kiev.kpi.sc.parser.ext.interim.repr;

import java.util.concurrent.atomic.AtomicInteger;

import ua.kiev.kpi.sc.parser.ext.interim.AbstractTranslation;
import ua.kiev.kpi.sc.parser.ext.interim.InvisibleTranslation;

public class Marker extends AbstractTranslation implements InvisibleTranslation {
	private static final AtomicInteger count = new AtomicInteger(0);
	private int num;

	private Marker(int num) {
		super();
		this.num = num;
	}

	public static Marker getInstance() {
		return new Marker(count.getAndIncrement());
	}

	public static void reset() {
		count.set(0);
	}

	public int getNum() {
		return num;
	}

	public LabelPointer getPointer() {
		return new LabelPointer(this.num);
	}
	
	@Override
	public String toString() {
		return "MARKER:"+String.valueOf(num);
	}
}
