package ua.kiev.kpi.sc.parser.ext.interim;

import ua.kiev.kpi.sc.parser.node.Node;

public class InterimsFactory {
	private IntermsRegistry registry;

	public Object create(int ruleNumber, Class<? extends Node> clazz)
	{
		return null;
	}
	
	public void setRegistry(IntermsRegistry registry) {
		this.registry = registry;
	}
}
