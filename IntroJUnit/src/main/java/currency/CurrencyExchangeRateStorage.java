package currency;

import java.math.BigDecimal;
import java.util.HashMap;
import java.util.Map;

import exceptions.GeneralApplicationException;

public class CurrencyExchangeRateStorage {

	private Map<String, BigDecimal> exchangeRates;

	private static CurrencyExchangeRateStorage instance;

	private static final String PAIR_SEPARATOR = "_";

	private CurrencyExchangeRateStorage() {

		String euro = CurrencyFactory.createEUR().getCode();
		String usd = CurrencyFactory.createUSD().getCode();
		String yen = CurrencyFactory.createYEN().getCode();

		exchangeRates = new HashMap<String, BigDecimal>();
		exchangeRates.put(usd + PAIR_SEPARATOR + euro, new BigDecimal("0.95"));
		exchangeRates.put(euro + PAIR_SEPARATOR + usd, new BigDecimal("1.05"));
		exchangeRates.put(usd + PAIR_SEPARATOR + yen, new BigDecimal("152.44"));
		exchangeRates.put(yen + PAIR_SEPARATOR + usd, new BigDecimal("0.0066"));
	}

	public static CurrencyExchangeRateStorage getInstance() {
		if (instance == null) {
			instance = new CurrencyExchangeRateStorage();
		}

		return instance;
	}

	public void addExchangeRate(String currencyCodeFrom, String currencyCodeTo, BigDecimal rate) {
		exchangeRates.put(currencyCodeFrom + PAIR_SEPARATOR + currencyCodeTo, rate);
	}

	public BigDecimal getExchangeRate(String currencyCodeFrom, String currencyCodeTo) {
		if (currencyCodeFrom.equals(currencyCodeTo)) {
			return new BigDecimal("1.0");
		}

		String key = currencyCodeFrom + PAIR_SEPARATOR + currencyCodeTo;
		if (!exchangeRates.containsKey(key)) {
			throw new GeneralApplicationException("Exchange rate not found for " + key);
		}

		return exchangeRates.get(key);
	}

}
