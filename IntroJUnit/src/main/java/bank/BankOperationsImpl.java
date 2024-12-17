package bank;

import java.math.BigDecimal;

import account.Account;
import account.AccountStatus;
import currency.Currency;
import currency.CurrencyConverter;
import exceptions.GeneralApplicationException;
import exceptions.NotActiveAccountException;
import exceptions.NotEnoughBalanceException;
import utils.Logger;

public class BankOperationsImpl implements BankOperations {

	private CurrencyConverter currencyConverter;

	private static final String SUCCESS_DEPOSIT_MESSAGE = "Deposited %s %s to account with id %s";

	private static final String SUCCESS_WITHDREW_MESSAGE = "Withdrew %s %s to account with id %s";

	private static final String SUCCESS_TRANSFER_MESSAGE = "Transferred %s from %s account to %s account";

	public BankOperationsImpl() {
		currencyConverter = new CurrencyConverter();
	}

	@Override
	public void deposit(Account account, BigDecimal amount) {

		this.ensureAccountIsNotNullAndIsActive(account);

		if (account.getBalance() == null) {
			account.setBalance(amount);
		} else {
			BigDecimal newBalance = account.getBalance().add(amount);
			account.setBalance(newBalance);
		}

		Logger.log(SUCCESS_DEPOSIT_MESSAGE, amount, account.getCurrency().getCode(), account.getAccountId());
	}

	@Override
	public void withdraw(Account account, BigDecimal amount) {

		this.ensureAccountIsNotNullAndIsActive(account);

		if (this.hasEnoughMoney(account, amount)) {
			BigDecimal newBalance = account.getBalance().subtract(amount);
			account.setBalance(newBalance);
			Logger.log(SUCCESS_WITHDREW_MESSAGE, amount, account.getCurrency().getCode(), account.getAccountId());
			return;
		}

		throw new NotEnoughBalanceException(account, amount);
	}

	@Override
	public void transfer(Account fromAccount, Account toAccount, BigDecimal amount) {

		this.ensureAccountIsNotNullAndIsActive(fromAccount);
		this.ensureAccountIsNotNullAndIsActive(toAccount);

		BigDecimal convertedAmount = amount;
		if (this.areCurrenciesDifferent(fromAccount.getCurrency(), toAccount.getCurrency())) {
			convertedAmount = currencyConverter.convert(amount, fromAccount.getCurrency(), toAccount.getCurrency());
		}

		this.withdraw(fromAccount, amount);
		this.deposit(toAccount, convertedAmount);
		Logger.log(SUCCESS_TRANSFER_MESSAGE, amount, fromAccount.getAccountId(), toAccount.getAccountId());
	}

	private void ensureAccountIsNotNullAndIsActive(Account account) {

		if (account == null) {
			throw new GeneralApplicationException("Account is null");
		}

		if (this.isAccountNotActive(account)) {
			throw new NotActiveAccountException(account.getAccountId());
		}
	}

	private boolean hasEnoughMoney(Account account, BigDecimal transferAmount) {
		return account.getBalance() != null && 
				account.getBalance().doubleValue() >= transferAmount.doubleValue();
	}

	private boolean areCurrenciesDifferent(Currency fromCurrency, Currency toCurrency) {
		return !fromCurrency.getCode().equals(toCurrency.getCode());
	}

	private boolean isAccountNotActive(Account account) {
		return !account.getStatus().equals(AccountStatus.ACTIVE);
	}

	@Override
	public void test() {
	}
}
