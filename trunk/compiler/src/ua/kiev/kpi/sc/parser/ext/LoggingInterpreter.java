package ua.kiev.kpi.sc.parser.ext;

import java.io.PrintStream;

import ua.kiev.kpi.sc.parser.analysis.DepthFirstAdapter;
import ua.kiev.kpi.sc.parser.node.Node;

public class LoggingInterpreter extends DepthFirstAdapter{	
	private int depth = 0;
    private PrintStream out;
    private StringBuilder buf;

    public LoggingInterpreter(PrintStream out) {    	
        this.out = out;
    }
    
    public LoggingInterpreter() {
    	buf = new StringBuilder();
    }

    public void defaultCase(Node node) {
        indent();
        printNodeName(node);
        if (buf != null) {
        	buf.append("\n");
        } else {
        	out.println();
        }
    }

    public void defaultIn(Node node) {
        indent();
        printNodeName(node);        
        if (buf != null) {
        	buf.append("\n");
        } else {
        	out.println();
        }
        depth = depth+1;
    }

    public void defaultOut(Node node) {
        depth = depth-1;
        if (out != null) {
        	out.flush();
        }
    }

    private void printNodeName(Node node) {
        String fullName = node.getClass().getName();
        String name = fullName.substring(fullName.lastIndexOf('.')+1);
        if (buf != null) {
        	buf.append(name);
        } else {
        	out.print(name);
        }
    }

    private void indent() {
    	if (out != null) {
    		for (int i = 0; i < depth; i++) out.print("   ");
    	} else {
    		for (int i = 0; i < depth; i++) buf.append("   ");
    	}
    }
    
    @Override
    public String toString() {
    	return buf.toString();
    }
}
