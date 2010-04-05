package ua.kiev.kpi.sc.parser.ext;

public class MyException extends RuntimeException{
	public MyException(String msg) {
		super(msg);
	}
	
	public MyException(Throwable cause) {
		super(cause);
	}
}
