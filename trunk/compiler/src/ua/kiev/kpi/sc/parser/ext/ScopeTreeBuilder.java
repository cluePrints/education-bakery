package ua.kiev.kpi.sc.parser.ext;

import ua.kiev.kpi.sc.parser.analysis.DepthFirstAdapter;
import ua.kiev.kpi.sc.parser.ext.id.FuncSymbol;
import ua.kiev.kpi.sc.parser.ext.id.TypeSymbol;
import ua.kiev.kpi.sc.parser.ext.id.VarSymbol;
import ua.kiev.kpi.sc.parser.ext.scope.RootScope;
import ua.kiev.kpi.sc.parser.ext.scope.Scope;
import ua.kiev.kpi.sc.parser.node.AClassBody;
import ua.kiev.kpi.sc.parser.node.AConstantDefinition;
import ua.kiev.kpi.sc.parser.node.ACycleCycleOperator;
import ua.kiev.kpi.sc.parser.node.AFunctionDeclaration;
import ua.kiev.kpi.sc.parser.node.ANormalFunctionBody;
import ua.kiev.kpi.sc.parser.node.ANotPublicClass;
import ua.kiev.kpi.sc.parser.node.APublicClass;
import ua.kiev.kpi.sc.parser.node.ASimpleIf;
import ua.kiev.kpi.sc.parser.node.AVariableDefinition;
import ua.kiev.kpi.sc.parser.node.AVoidFunctionBody;
import ua.kiev.kpi.sc.parser.node.PVariableName;
import ua.kiev.kpi.sc.parser.node.PVariableType;

public class ScopeTreeBuilder extends DepthFirstAdapter{	
	private RootScope rootScope = new RootScope();
	private Scope currentScope = rootScope;
	
	public RootScope getRootScope() {
		return rootScope;
	}
	
	// ============================================
	// Public class decl
	
	@Override
	public void inAPublicClass(APublicClass node) {
		TypeSymbol clazz = new TypeSymbol();
		clazz.setName(node.getIdentifier().getText());
		currentScope = new Scope(currentScope, clazz);
		currentScope.setDefLine(node.getIdentifier().getLine());
		currentScope.setDefPos(node.getIdentifier().getPos());
		super.inAPublicClass(node);
	}
	
	@Override
	public void inANotPublicClass(ANotPublicClass node) {
		TypeSymbol clazz = new TypeSymbol();
		clazz.setName(node.getIdentifier().getText());
		currentScope = new Scope(currentScope, clazz);
		
		currentScope.setDefLine(node.getIdentifier().getLine());
		currentScope.setDefPos(node.getIdentifier().getPos());
		super.inANotPublicClass(node);
	}
	
	@Override
	public void outAClassBody(AClassBody node) {
		currentScope = currentScope.getParent();
		super.outAClassBody(node);
	}	
	
	// ============================================
	// Function decl
	@Override
	public void inAFunctionDeclaration(AFunctionDeclaration node) {
		FuncSymbol func = new FuncSymbol();
		func.setName(node.getFunctionName().toString());
		currentScope = new Scope(currentScope, func);
		
		currentScope.setDefLine(node.getLPar().getLine());
		currentScope.setDefPos(node.getLPar().getPos());
		super.inAFunctionDeclaration(node);
	}	
	
	@Override
	public void outANormalFunctionBody(ANormalFunctionBody node) {
		currentScope = currentScope.getParent();
		super.outANormalFunctionBody(node);
	}
	@Override
	public void outAVoidFunctionBody(AVoidFunctionBody node) {
		currentScope = currentScope.getParent();
		super.outAVoidFunctionBody(node);
	}

	
	// ============================================
	// while cycle
	@Override
	public void inACycleCycleOperator(ACycleCycleOperator node) {
		currentScope = new Scope(currentScope);
		currentScope.setDefLine(node.getLPar().getLine());
		currentScope.setDefPos(node.getLPar().getPos());
		super.inACycleCycleOperator(node);
	}
	
	@Override
	public void outACycleCycleOperator(ACycleCycleOperator node) {
		currentScope = currentScope.getParent();
		super.outACycleCycleOperator(node);
	}
	
	// ============================================
	// if expression
	@Override
	public void inASimpleIf(ASimpleIf node) {
		currentScope = new Scope(currentScope);
		currentScope.setDefLine(node.getLPar().getLine());
		currentScope.setDefPos(node.getLPar().getPos());
		super.inASimpleIf(node);
	}
	
	@Override
	public void outASimpleIf(ASimpleIf node) {
		currentScope = currentScope.getParent();
		super.outASimpleIf(node);
	}
	
	// ============================================
	// Variable decl
	@Override
	public void inAVariableDefinition(AVariableDefinition node) {
		createDefinition(node.getVariableType(), node.getVariableName());
		super.inAVariableDefinition(node);
	}
	
	@Override
	public void inAConstantDefinition(AConstantDefinition node) {
		VarSymbol sym = createDefinition(node.getVariableType(), node.getVariableName());
		sym.setAssignable(false);
		super.inAConstantDefinition(node);
	}

	private VarSymbol createDefinition(PVariableType type, PVariableName name) {
		String typeName = type.toString().replaceAll(" ", "");
		String nodeName = name.toString();
		boolean isArray = false;
		if (typeName.endsWith("[]")) {
			typeName = typeName.substring(0, typeName.length()-2);
			isArray = true;
		}
		VarSymbol sym = new VarSymbol();
		sym.setName(nodeName);
		sym.setType(typeName);
		sym.setArray(isArray);
		currentScope.addIdentifier(sym);
		return sym;
	}
}
