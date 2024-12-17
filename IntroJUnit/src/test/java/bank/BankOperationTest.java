package bank;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.math.BigDecimal;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import account.Account;
import account.AccountStatus;
import account.User;
import currency.Currency;
import currency.CurrencyFactory;

public class BankOperationTest {

	private BankOperations bankOperations = new BankOperationsImpl();

	@Test
	@DisplayName("This test should be ok - deposit method add inserted amount")
	void testShouldDepositAmountIntoValidAccount() {
		// 1. Arrange
		Currency usd = CurrencyFactory.createUSD();

		User ivan = new User("Ivan", "Ivanov", "ivan.ivanov@email.com");

		Account account = new Account(1L, ivan, new BigDecimal("1000"), usd, AccountStatus.ACTIVE);

		// 2. Act
		bankOperations.deposit(account, new BigDecimal("100"));

		// 3. Assert
		assertEquals(new BigDecimal("1100"), account.getBalance());
	}

	@Test
	@DisplayName("This test should be ok - withdraw method subtract amount")
	void testShouldWithdrawAmountFromValidAccount() {
		// 1. Arrange
		Currency usd = CurrencyFactory.createUSD();
		User ivan = new User("Ivan", "Ivanov", "ivan.ivanov@email.com");
		Account account = new Account(1L, ivan, new BigDecimal("1000"), usd, AccountStatus.ACTIVE);

		// 2. Act
		bankOperations.withdraw(account, new BigDecimal("1000"));

		// 3. Assert
		assertEquals(new BigDecimal("0"), account.getBalance());
	}

}
