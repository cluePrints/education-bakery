package ua.kiev.kpi.sc.parser.ext.ui;

import java.util.Enumeration;

import javax.swing.tree.TreeNode;

import ua.kiev.kpi.sc.parser.ext.id.Symbol;
import ua.kiev.kpi.sc.parser.ext.scope.RootScope;
import ua.kiev.kpi.sc.parser.ext.scope.Scope;

public class TreeNodeAdaptor implements TreeNode{
	private Object node; // Scope or Symbol

	public Object getNode() {
		return node;
	}

	public TreeNodeAdaptor(Object node) {
		super();
		this.node = node;
	}

	public boolean isScope() 
	{
		return node instanceof Scope;
	}
	
	private boolean isSymbol() 
	{
		return node instanceof Symbol;
	}
	
	public Scope scope() 
	{
		return (Scope) node;
	}
	
	private Symbol symbol()
	{
		return (Symbol) node;
	}
	
	@Override
	public Enumeration children() {
		throw new RuntimeException("Not supproted");
	}

	@Override
	public boolean getAllowsChildren() {
		return !isLeaf();
	}

	@Override
	public TreeNode getChildAt(int childIndex) {
		if (isSymbol()) {
			return null;
		} else if (isScope()) {
			return new TreeNodeAdaptor(scope().getChildren().get(childIndex));
		}
		return null;
	}

	@Override
	public int getChildCount() {
		return scope().getChildren().size();
	}

	@Override
	public int getIndex(TreeNode node) {
		if (isScope()) {
			return scope().getChildren().indexOf(((TreeNodeAdaptor)node).getNode());
		} else {
			throw new RuntimeException("Not supproted");
		}
	}

	@Override
	public TreeNode getParent() {
		if (isScope()) {
			return new TreeNodeAdaptor(scope().getParent());
		} else if (isSymbol()){
			return new TreeNodeAdaptor(symbol().getScope());
		} else {
			return null;
		}
	}

	@Override
	public boolean isLeaf() {
		return isSymbol();
	}
	
	@Override
	public String toString() {
		if (isScope() && scope().getScopeHeaderSymbol() != null) {			
			return scope().getScopeHeaderSymbol().getName();
		} else if (isSymbol()){
			return symbol().getName();
		} else if (this.node instanceof RootScope) {
			return "<Root>";
		} else {
			return "<unnamed>";
		}
	}
	
	
}
