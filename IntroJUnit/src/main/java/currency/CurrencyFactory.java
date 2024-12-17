package currency;

public class CurrencyFactory {

	private CurrencyFactory() {

	}

	public static Currency createUSD() {
		return new Currency("USD", "$");
	}

	public static Currency createEUR() {
		return new Currency("EUR", "€");
	}

	public static Currency createYEN() {
		return new Currency("JPY", "¥");
	}
}
