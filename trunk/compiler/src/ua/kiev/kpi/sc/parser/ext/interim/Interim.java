package ua.kiev.kpi.sc.parser.ext.interim;

import ua.kiev.kpi.sc.parser.node.Node;

/**
 * It's factory for generating intermediate representation of syntax tree.
 *
 */
public interface Interim {
	Translation translate(Node node);
}
