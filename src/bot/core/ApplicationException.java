package bot.core;

/**
 * 
 * @author Mir4ik
 * @version 0.1 29.08.2014
 */
public class ApplicationException extends Exception {

	private static final long serialVersionUID = 3189658968868419110L;

	public ApplicationException() {}
	
	public ApplicationException(String message) {
		super(message);
	}
	
	public ApplicationException(Throwable cause) {
		super(cause);
	}
	
	public ApplicationException(String message, Throwable cause) {
		super(message, cause);
	}
}