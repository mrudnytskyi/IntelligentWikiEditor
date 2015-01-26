package bot.compiler;

/**
 * Class, representing exception while article parsing.
 * 
 * @author Mir4ik
 * @version 0.1 17.1.2015
 */
public class CompilerException extends Exception {

	private static final long serialVersionUID = 1273744087199630175L;

	private final int context;
	
	public int getContext() {
		return context;
	}
	
	public CompilerException() {
		super();
		context = 0;
	}
	
	public CompilerException(String message) {
		super(message);
		context = 0;
	}
	
	public CompilerException(String message, Throwable cause) {
		super(message, cause);
		context = 0;
	}
	
	public CompilerException(String message, int context) {
		super(message);
		this.context = context;
	}
	
	@Override
	public String toString() {
		return super.toString() + " Symbol #" + context;
	}
}