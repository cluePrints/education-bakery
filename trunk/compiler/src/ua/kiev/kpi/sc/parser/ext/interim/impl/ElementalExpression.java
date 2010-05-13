package ua.kiev.kpi.sc.parser.ext.interim.impl;

import ua.kiev.kpi.sc.parser.ext.interim.Interim;
import ua.kiev.kpi.sc.parser.ext.interim.Translation;
import ua.kiev.kpi.sc.parser.ext.interim.TriggeredFor;
import ua.kiev.kpi.sc.parser.node.Node;
import ua.kiev.kpi.sc.parser.node.PElementalExpression;

@TriggeredFor(reductedNodesArray={PElementalExpression.class})
public class ElementalExpression implements Interim {
	public Translation translate(Node node) {
		return new Translation() {
			@Override
			public String toString() {
				return "EEX";
			}
		};
	}
}
