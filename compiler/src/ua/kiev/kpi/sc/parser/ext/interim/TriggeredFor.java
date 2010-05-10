package ua.kiev.kpi.sc.parser.ext.interim;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

import ua.kiev.kpi.sc.parser.node.Node;

@Retention(RetentionPolicy.RUNTIME)
public @interface TriggeredFor {
	int[] ruleIdArray() default {};
	Class<? extends Node>[] reductedNodesArray() default {}; 
}
