package ua.kiev.kpi.sc.parser.ext.interim;

import ua.kiev.kpi.sc.parser.node.Node;

public class InterimsFactory {
	private InterimsRegistry registry;

	public Interim create(int ruleNumber, Class<? extends Node> clazz)
	{
		return null;
	}
	
	public void setRegistry(InterimsRegistry registry) {
		this.registry = registry;
	}
}
