package exceptions;

import java.math.BigDecimal;

import account.Account;

public class NotEnoughBalanceException extends GeneralApplicationException {

	private static final long serialVersionUID = -997876103019594034L;

	private static final String NOT_ENOUGH_BALANCE_ERROR_MESSAGE = "Account with id = %s don't have enough balance [%s] - current account balance is %s";

	public NotEnoughBalanceException(Account account, BigDecimal amount) {
		super(String.format(NOT_ENOUGH_BALANCE_ERROR_MESSAGE, account.getAccountId(), amount, account.getBalance()));
	}

}
