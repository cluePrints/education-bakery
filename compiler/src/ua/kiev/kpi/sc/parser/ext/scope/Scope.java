package ua.kiev.kpi.sc.parser.ext.scope;

import java.util.Collection;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;

import ua.kiev.kpi.sc.parser.ext.id.FuncSymbol;
import ua.kiev.kpi.sc.parser.ext.id.ScopeException;
import ua.kiev.kpi.sc.parser.ext.id.Symbol;
import ua.kiev.kpi.sc.parser.ext.id.TypeSymbol;
import ua.kiev.kpi.sc.parser.ext.id.VarSymbol;

import com.google.common.base.Predicate;
import com.google.common.collect.HashMultimap;
import com.google.common.collect.Iterables;
import com.google.common.collect.Multimap;
/**
 * No statics is cool thing for scoping.
 * No declarations in loop/if headers is cool.
 * No declarations in blocks(except class variables, func arguments) is even better.
 */
public class Scope {
	private Scope parent;
	private Symbol headerSymbol;
	private Multimap<String, Symbol> declaredIdentifiers = HashMultimap.create();
	private List<Scope> childScopes = new LinkedList<Scope>();
	
	@Override
	public String toString() {
		StringBuilder b = new StringBuilder();
		String header;
		if (headerSymbol != null) {
			header = headerSymbol.getName();
		} else {
			header = "<none>";
		}
		b.append(getMargin()+"\\"+header+"\n");
		for (Scope child : childScopes) { 
			b.append(child);
		}
		for (Symbol id : declaredIdentifiers.values()) {
			b.append(getMargin()+id.getName()+"\n");
		}
		return b.toString();
	}
	
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
		assertNoSuchVariableDeclared(id);
		assertNoSuchFunctionDeclared(id);
		declaredIdentifiers.put(id.getName(), id);
	}
	
	private void assertNoSuchVariableDeclared(Symbol id)
	{
		if (id instanceof VarSymbol) {
			Collection<Symbol> symbols = declaredIdentifiers.get(id.getName());
			for (Symbol symbol : symbols) {
				if (symbol instanceof VarSymbol) {
					throw new ScopeException("Variable '"+id.getName()+"' already exists in current scope");
				}
			}
		}
	}
	
	private void assertNoSuchFunctionDeclared(Symbol sym)
	{		
		if (sym instanceof FuncSymbol) {
			final FuncSymbol func = (FuncSymbol) sym;

			Collection<Symbol> symbols = declaredIdentifiers.get(sym.getName());
			Iterable<FuncSymbol> funcs = Iterables.filter(symbols, FuncSymbol.class);
			
			funcs = Iterables.filter(funcs, new Predicate<FuncSymbol>() {
				public boolean apply(FuncSymbol input) {
					boolean sameName = func.getName().equals(input.getName());
					boolean sameArgList = func.getParams().equals(input.getParams());
					return sameName && sameArgList;
				}
			});
			
			Iterator<FuncSymbol> fIterator = funcs.iterator();
			if (fIterator.hasNext()) {
				throw new ScopeException("Function '" + func.getName() + "' already exists in current scope");
			}
		}
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

	public VarSymbol getVisibleVarSymbol(String name) {
		VarSymbol result = getDeclaredVarSymbol(name);	
		if (result == null) {			
			result = parent.getVisibleVarSymbol(name);
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
	
	public VarSymbol getDeclaredVarSymbol(String name)
	{
		return getDeclaredSymbol(name, VarSymbol.class);
	}
	
	public FuncSymbol getDeclaredFuncSymbol(String name)
	{
		return getDeclaredSymbol(name, FuncSymbol.class);
	}
	
	protected <T extends Symbol> T getDeclaredSymbol(final String name, Class<T> clazz) {
		Collection<Symbol> symbols = declaredIdentifiers.get(name);
		Iterable<T> vars = Iterables.filter(symbols, clazz);
		
		vars = Iterables.filter(vars, new Predicate<T>() {
			public boolean apply(T input) {				
				return name.equals(input.getName());
			}
		});
		
		Iterator<T> vIterator = vars.iterator();
		T result = null;
		if (vIterator.hasNext()) {
			result = vIterator.next();
			if (vIterator.hasNext()) {
				throw new ScopeException("Two same typed symbols found in scope: "+name+"\n"+vIterator.next()+"\n"+result);
			}
		}
		
		return result;		
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
		return getRoot().getDeclaredSymbol(name, TypeSymbol.class);
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
	
	private int getDepth()
	{
		int i=0;
		Scope scope = this;
		while (!(scope instanceof RootScope)) {
			scope = scope.getParent();
			i++;
		}
		return i;
	}
	
	private String getMargin()
	{
		String result ="";
		for (int i=0; i<getDepth(); i++) {
			result = result + "  ";
		}
		return result;
	}
	
	
}
