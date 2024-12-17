package account;

import java.math.BigDecimal;

import currency.Currency;

public class Account {

	private Long accountId;

	private User user;

	private BigDecimal balance;
	
	private Currency currency;
	
	private AccountStatus status;

	public Account(Long accountId, User user, BigDecimal balance,  Currency currency, AccountStatus status) {
		this.accountId = accountId;
		this.user = user;
		this.balance = balance;
		this.currency = currency;
		this.status = status;
	}

	public Long getAccountId() {
		return accountId;
	}

	public void setAccountId(Long accountId) {
		this.accountId = accountId;
	}

	public User getUser() {
		return user;
	}

	public void setUser(User user) {
		this.user = user;
	}

	public BigDecimal getBalance() {
		return balance;
	}

	public void setBalance(BigDecimal balance) {
		this.balance = balance;
	}

	public Currency getCurrency() {
		return currency;
	}

	public void setCurrency(Currency currency) {
		this.currency = currency;
	}

	public AccountStatus getStatus() {
		return status;
	}

	public void setStatus(AccountStatus status) {
		this.status = status;
	}

	public String getFormatedAmount() {
		return this.getBalance() + " " + this.getCurrency().getCode();
	}

}
