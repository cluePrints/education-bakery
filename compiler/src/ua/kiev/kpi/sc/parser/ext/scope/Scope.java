package ua.kiev.kpi.sc.parser.ext.scope;

import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import ua.kiev.kpi.sc.parser.ext.id.ScopeException;
import ua.kiev.kpi.sc.parser.ext.id.Symbol;
import ua.kiev.kpi.sc.parser.ext.id.TypeSymbol;
/**
 * No statics is cool thing for scoping.
 * No declarations in loop/if headers is cool.
 * No declarations in blocks(except class variables, func arguments) is even better.
 */
public class Scope {
	private Scope parent;
	private Symbol headerSymbol;
	private Map<String, Symbol> declaredIdentifiers = new HashMap<String, Symbol>();
	private List<Scope> childScopes = new LinkedList<Scope>();
	
	public Scope(Scope parent) {
		this(parent, null);
	}
	public Scope(Scope parent, Symbol headerSymbol) {
		super();
		this.parent = parent;
		this.headerSymbol = headerSymbol;
		if (headerSymbol != null) {
			parent.addIdentifier(headerSymbol);
			headerSymbol.setScope(this);
		}
		if (parent != null) {
			parent.addChildScope(this);
		}
	}
	 
	/**
	 * Symbol for which this scope is linked to. I.e. for function - funcSymbol, 
	 * for class - corresponding classSymbol. For blocks - null;  
	 * 
	 * @return
	 */
	public Symbol getScopeHeaderSymbol() {
		return headerSymbol;
	}
	
	public Scope getParent() {
		return parent;
	}
	
	public void addIdentifier(Symbol id) {
		if (isSymbolVisible(id.getName()) && !(getVisibleSymbol(id.getName()) instanceof TypeSymbol)) {
			throw new ScopeException("Symbol '"+id.getName()+"' is already defined.");
		}
		declaredIdentifiers.put(id.getName(), id);
	}
	
	public void addChildScope(Scope scope) {
		childScopes.add(scope);
	}
	
	public int getChildScopeIdx(Scope scope) {
		return childScopes.indexOf(scope);
	}
	
	public Scope getChildScope(int num) {
		if (num <0 || num >= childScopes.size()) {
			return null;
		}
		return childScopes.get(num);
	}
	
	public int getChildScopesCount() {
		return childScopes.size();
	}
	
	/**
	 * Get identifier from this scope or some of the parent's.
	 * 
	 * @param name
	 * @return
	 */
	public Symbol getVisibleSymbol(String name) {
		Symbol result = getDeclaredSymbol(name);	
		if (result == null) {			
			result = parent.getVisibleSymbol(name);
		}
		
		/*
		 * enable if static variables will be added
		 * 
		if (result == null) {
			Symbol possiblyClass = getRoot().getDeclaredSymbol(name);
			if (possiblyClass instanceof ClassSymbol) {
				result = possiblyClass;
			}
		}*/
		return result;
	}
	
	/**
	 * 
	 * Get identifier from this scope only
	 * 
	 * @param name
	 * @return
	 */
	public Symbol getDeclaredSymbol(String name) {
		return declaredIdentifiers.get(name);		
	}
	
	public Scope getSymbolDeclarationScope(String name) {
		if (declaredIdentifiers.get(name) != null) {
			return this;
			
		} else if (parent != null) {
			return parent.getSymbolDeclarationScope(name);
			
		} else {
			return null;
		}
	}
	
	public TypeSymbol getClassSymbol(String name) {
		Symbol possiblyClass = getRoot().getDeclaredSymbol(name);
		if (possiblyClass instanceof TypeSymbol) {
			return (TypeSymbol) possiblyClass;
		}
		return null;
	}
	
	/**
	 * Returns true if identifier is visible from this scope
	 * 
	 * @param name
	 * @return
	 */
	public boolean isSymbolVisible(String name) {
		return getVisibleSymbol(name) != null;
	}
	
	public boolean isSymbolDeclared(String name) {
		return getDeclaredSymbol(name) != null;
	}
	
	public Symbol getHeaderSymbol() {
		return headerSymbol;
	}

	public void setHeaderSymbol(Symbol headerSymbol) {
		this.headerSymbol = headerSymbol;
	}

	public void setParent(Scope parent) {
		this.parent = parent;
	}	
	
	public LinkedList<Object> getChildren() 
	{
		LinkedList<Object> result = new LinkedList<Object>();
		result.addAll(this.childScopes);
		for (Symbol sym : this.declaredIdentifiers.values()) {
			if (sym.getScope() == null || sym.getScope().getHeaderSymbol() != sym) {
				result.add(sym);
			}
		}
		return result;
	}
	
	public Scope getParentClassScope() {
		Scope scope = this;
		while (scope.getParent() != null && !(scope.getHeaderSymbol() instanceof TypeSymbol)) {
			scope = scope.getParent();
		}
		return scope;
	}
	
	private Scope getRoot() {
		Scope scope = this;
		while (!(scope instanceof RootScope)) {
			scope = scope.getParent();
		}
		return scope;
	}
	
	private boolean isRootScope() {
		return getRoot() != null;
	}
	
	private int defLine;
	private int defPos;
	public int getDefLine() {
		return defLine;
	}
	public void setDefLine(int defLine) {
		this.defLine = defLine;
	}
	public int getDefPos() {
		return defPos;
	}
	public void setDefPos(int defPos) {
		this.defPos = defPos;
	}
}
