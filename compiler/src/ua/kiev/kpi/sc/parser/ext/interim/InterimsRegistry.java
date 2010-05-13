package ua.kiev.kpi.sc.parser.ext.interim;


public interface InterimsRegistry {
	Iterable<? extends Interim> lookupAll();
}
