package currency;

import java.math.BigDecimal;
import java.math.RoundingMode;

public class CurrencyConverter {

	private CurrencyExchangeRateStorage currencyExchangeRateStorage;

	public CurrencyConverter() {
		currencyExchangeRateStorage = CurrencyExchangeRateStorage.getInstance();
	}

	public BigDecimal convert(BigDecimal amount, Currency currencyFrom, Currency currencyTo) {
		BigDecimal exchangeRate = currencyExchangeRateStorage.getExchangeRate(currencyFrom.getCode(), currencyTo.getCode());
		BigDecimal convertedAmount = amount.multiply(exchangeRate);
		return convertedAmount.setScale(2, RoundingMode.HALF_UP);
	}

}
