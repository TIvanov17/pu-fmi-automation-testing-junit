package exceptions;

public class NotActiveAccountException extends GeneralApplicationException {

	private static final long serialVersionUID = -5110198736541855286L;

	private static final String NOT_ENOUGH_BALANCE_ERROR_MESSAGE = "Account with id = %s is not active";

	public NotActiveAccountException(Long accountId) {
		super(String.format(NOT_ENOUGH_BALANCE_ERROR_MESSAGE, accountId));
	}

}
