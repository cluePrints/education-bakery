package ua.kiev.kpi.sc.parser.ext.id;

import java.util.Arrays;
import java.util.Collection;

import ua.kiev.kpi.sc.parser.ext.MyException;
import ua.kiev.kpi.sc.parser.ext.scope.Scope;

public class Symbol {
	protected String name;
	protected Scope scope;
	public Scope getScope() {
		return scope;
	}
	public void setScope(Scope scope) {
		this.scope = scope;
	}
	private int defLine;
	private int defPos;
	
	public void setName(String name) {
		String normName = name.trim();
		if (reservedNames.contains(normName)) {
			String msg = String.format("'%1$s' is java reserved token and could not be used as id.", normName);
			throw new MyException(msg);
		}
		this.name = normName;
	}
	public void setDefLine(int defLine) {
		this.defLine = defLine;
	}
	public void setDefPos(int defPos) {
		this.defPos = defPos;
	}
	
	public String getName() {
		return name;
	}
	public int getDefLine() {
		return defLine;
	}
	public int getDefPos() {
		return defPos;
	}
	
	private static final Collection<String> reservedNames = Arrays.asList(new String[] {
			"abstract",
			"assert",
			"boolean",
			"break",
			"byte",
			"case",
			"catch",
			"char",
			"class",
			"const",
			"continue",				
			"default",
			"do",
			"double",
			"else",
			"enum",
			"extends",
			"false",
			"final",
			"finally",
			"float",
			"for",
				
			"goto",
			"if",
			"implements",
			"import",
			"instanceof",
			"int",
			"interface",
			"long",
			"native",
			"new",
			"null",
				
			"package",
			"private",
			"protected",
			"public",
			"return",
			"short",
			"static",
			"strictfp",
			"super",
			"switch",
				
			"synchronized",
			"this",
			"throw",
			"throws",
			"transient",
			"true",
			"try",
			"void",
			"volatile",
			"while"
	});
}
