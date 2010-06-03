package ua.kiev.kpi.sc.parser.ext.id;

import ua.kiev.kpi.sc.parser.ext.interim.repr.FuncPointer;

public class FuncType extends TypeSymbol{
	private FuncPointer pointer;

	public FuncPointer getPointer() {
		return pointer;
	}

	public void setPointer(FuncPointer pointer) {
		this.pointer = pointer;
	}

	public FuncType(FuncPointer pointer) {
		super();
		this.pointer = pointer;
	}	
	
	@Override
	public String getName() {
		return pointer.getName();
	}
	
	@Override
	public void setName(String name) {
		// do nothing
	}
}
