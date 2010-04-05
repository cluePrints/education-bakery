package ua.kiev.kpi.sc.parser.ext;

import ua.kiev.kpi.sc.parser.analysis.DepthFirstAdapter;
import ua.kiev.kpi.sc.parser.ext.id.FuncSymbol;
import ua.kiev.kpi.sc.parser.ext.id.Symbol;
import ua.kiev.kpi.sc.parser.ext.id.TypeSymbol;
import ua.kiev.kpi.sc.parser.ext.id.VarSymbol;
import ua.kiev.kpi.sc.parser.ext.scope.Scope;
import ua.kiev.kpi.sc.parser.node.AArrElemElementalExpression;
import ua.kiev.kpi.sc.parser.node.ACallElementalExpression;
import ua.kiev.kpi.sc.parser.node.AClassBody;
import ua.kiev.kpi.sc.parser.node.ACycleCycleOperator;
import ua.kiev.kpi.sc.parser.node.ACycleOperator;
import ua.kiev.kpi.sc.parser.node.AFunctionDeclaration;
import ua.kiev.kpi.sc.parser.node.AIdentifierElementalExpression;
import ua.kiev.kpi.sc.parser.node.ANegCast;
import ua.kiev.kpi.sc.parser.node.ANormalFunctionBody;
import ua.kiev.kpi.sc.parser.node.ANotPublicClass;
import ua.kiev.kpi.sc.parser.node.APublicClass;
import ua.kiev.kpi.sc.parser.node.ARecursiveElementalExpression;
import ua.kiev.kpi.sc.parser.node.ASimpleCast;
import ua.kiev.kpi.sc.parser.node.ASimpleIf;
import ua.kiev.kpi.sc.parser.node.ATypeName;
import ua.kiev.kpi.sc.parser.node.AVoidFunctionBody;

public class ScopeTreeChecker extends DepthFirstAdapter{
	private Scope rootScope;
	private Scope currentScope;
	
	private TypeSymbol lastVarClass;
	
	public ScopeTreeChecker(Scope rootScope) {
		super();
		this.rootScope = rootScope;
		this.currentScope = rootScope;
		//enterScope();
	}
	
	public Scope getRootScope() {
		return rootScope;
	}
	
	// ============================================
	// Public class decl
	
	@Override
	public void inAPublicClass(APublicClass node) {
		enterScope(node.getClassToken().getLine());
		super.inAPublicClass(node);
	}
	
	@Override
	public void inANotPublicClass(ANotPublicClass node) {
		enterScope(node.getClassToken().getLine());
		super.inANotPublicClass(node);
	}
	
	@Override
	public void outAClassBody(AClassBody node) {
		exitScope();
	}

	// ============================================
	// Function decl
	@Override
	public void inAFunctionDeclaration(AFunctionDeclaration node) {
		enterScope(node.getLPar().getLine());
		super.inAFunctionDeclaration(node);
	}	
	
	@Override
	public void outANormalFunctionBody(ANormalFunctionBody node) {
		exitScope();
		super.outANormalFunctionBody(node);
	}
	@Override
	public void outAVoidFunctionBody(AVoidFunctionBody node) {
		exitScope();
		super.outAVoidFunctionBody(node);
	}
	
	// ============================================
	// while cycle
	@Override
	public void inACycleCycleOperator(ACycleCycleOperator node) {
		enterScope(node.getLPar().getLine());
		super.inACycleCycleOperator(node);
	}
	
	@Override
	public void outACycleOperator(ACycleOperator node) {
		exitScope();
		super.outACycleOperator(node);
	}
	
	// ============================================
	// if expression
	@Override
	public void inASimpleIf(ASimpleIf node) {
		enterScope(node.getIf().getLine());
		super.inASimpleIf(node);
	}
	
	@Override
	public void outASimpleIf(ASimpleIf node) {
		exitScope();
		super.outASimpleIf(node);
	}

	private void enterScope(int line) {
		for (int i=0; i<currentScope.getChildScopesCount(); i++) {
			Scope child = currentScope.getChildScope(i);
			if (child.getDefLine() == line) {
				currentScope = child;
				return;
			}
		}
		if (currentScope.getParent().getDefLine() == line) {
			currentScope = currentScope.getParent();
		} else {
			throw new MyException("We've got scoping algorithm problems.");
		}
		
	}

	private void exitScope() {
		currentScope = currentScope.getParent();
	}
	
	// ================================================
	// Variable usages
	@Override
	public void caseAIdentifierElementalExpression(
			AIdentifierElementalExpression node) {
		variableUsageEncountered(node.getIdentifier().getLine(), node.getIdentifier().getText());
		super.caseAIdentifierElementalExpression(node);
	}
	
	@Override
	public void caseARecursiveElementalExpression(
			ARecursiveElementalExpression node) {
		Symbol obj = variableUsageEncountered(node.getIdentifier().getLine(), node.getIdentifier().getText());
		int line = node.getDot().getLine();
		if (obj instanceof VarSymbol) {
			VarSymbol variable = (VarSymbol) obj;
			String className = variable.getType();
			lastVarClass = currentScope.getClassSymbol(className);
			if (lastVarClass == null) {				
				throw new MyException(String.format("Type %2$s not found (line %1$d). ", line, className));
			} else {
				super.caseARecursiveElementalExpression(node);
			}
		} else {
			throw new MyException(String.format("Variable %2$s not found (line %1$d). ", line, node.getIdentifier().getText()));
		}
	}
	
	@Override
	public void caseAArrElemElementalExpression(AArrElemElementalExpression node) {		
		variableUsageEncountered(node.getIdentifier().getLine(), node.getIdentifier().getText());
		super.caseAArrElemElementalExpression(node);
	}
	
	@Override
	public void outANegCast(ANegCast node) {
		lastVarClass = null;
		super.outANegCast(node);
	}
	
	@Override
	public void outASimpleCast(ASimpleCast node) {
		lastVarClass = null;
		super.outASimpleCast(node);
	}
	
	@Override
	public void caseACallElementalExpression(ACallElementalExpression node) 
	{
		String funcName = node.getIdentifier().getText();
		int line = node.getLPar().getLine();
		
		// <something>.call()
		if (lastVarClass != null) { 
			Scope classScope = lastVarClass.getScope();
			if (classScope == null) {
				throw new MyException(String.format("Class import error: class %2$s at %1$d has no info about %3$s function. ",
						line,
						lastVarClass.getName(),
						funcName));
			
			}
			Symbol sym = classScope.getDeclaredSymbol(funcName);		
			if (sym != null && sym instanceof FuncSymbol) {
				FuncSymbol func = (FuncSymbol) sym;
				// TODO: check params
			} else {
				throw new MyException(String.format("Function %3$s for class %2$s was not found (line %1$d). ",
						line,
						lastVarClass.getName(),
						funcName));
			}
		} else {
			// call()
			Scope classScope = currentScope.getParentClassScope();
			Symbol sym = classScope.getDeclaredSymbol(funcName);		
			if (sym != null && sym instanceof FuncSymbol) {
				FuncSymbol func = (FuncSymbol) sym;
				// TODO: check params
			} else {
				throw new MyException(String.format("Function %3$s for class %2$s was not found (line %1$d). ",
						line,
						lastVarClass.getName(),
						funcName));
			}
		}
		//variableUsageEncountered(node.getIdentifier().getLine(), node.getIdentifier().getText());
		super.caseACallElementalExpression(node);
		
	}
	
	@Override
	public void inATypeName(ATypeName node) {
		String className = node.getIdentifier().getText();
		int line = node.getIdentifier().getLine();
		TypeSymbol type = currentScope.getClassSymbol(className);
		if (type == null) {
			throw new MyException(String.format("Type %2$s not found (line %1$d). ", line, className));
		}
		super.inATypeName(node);
	}


	private Symbol variableUsageEncountered(int line, String name) {
		Symbol var = currentScope.getVisibleSymbol(name);
		if (var == null) {
			Scope classScope = currentScope.getParentClassScope();
			if (classScope != null) {
				var = classScope.getVisibleSymbol(name);
			}
			if (var == null && lastVarClass != null) {
				classScope = lastVarClass.getScope();
			}
			if (classScope != null) {
				var = classScope.getVisibleSymbol(name);
			}
		}
		if (var == null) {
			throw new MyException(String.format("Variable %2$s is not visible at line %1$d. ", line, name));
		}
		if (!(var instanceof VarSymbol)) {
			throw new MyException(String.format("%2$s expected to be variable at line %1$d. ", line, name));
		}
		return var;
	}
}
