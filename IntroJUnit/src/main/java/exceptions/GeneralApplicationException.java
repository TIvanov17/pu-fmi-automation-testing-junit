package exceptions;

public class GeneralApplicationException extends RuntimeException {

	private static final long serialVersionUID = 1410297189612794672L;

	public GeneralApplicationException(String message) {
		super(message);
	}
	
}
