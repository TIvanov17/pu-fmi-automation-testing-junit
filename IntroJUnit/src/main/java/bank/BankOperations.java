package bank;

import java.math.BigDecimal;

import account.Account;

public interface BankOperations {

	public void deposit(Account account, BigDecimal amount);
	
	public void withdraw(Account account, BigDecimal amount);

	public void transfer(Account fromAccount, Account toAccount, BigDecimal amount);
	
	public void test();
}
