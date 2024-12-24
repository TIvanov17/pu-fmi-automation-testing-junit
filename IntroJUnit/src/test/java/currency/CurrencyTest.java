package currency;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.junit.jupiter.api.TestInstance.Lifecycle;

import static org.hamcrest.Matchers.*;
import static org.hamcrest.MatcherAssert.*;

@TestInstance(Lifecycle.PER_METHOD)
public class CurrencyTest {

	private Currency currency;
	
	@BeforeEach
	void createCurrencyForTests() {
		currency = new Currency("USD", "$");
	}
	
	@Test
	void testCreateCurrency() {
		assertEquals(currency.getCode(), "USD");
		assertNotEquals(currency.getSymbol(), "neshto");
		assertEquals(currency.getSymbol(), "$");
	}
	
	@Test
	void testCreateCurrencyHarmcrest() {
		assertThat(currency.getCode(), is("USD"));
		assertThat(currency.getCode(), not(equalTo("EUR")));
		assertThat(currency.getSymbol(), equalTo("$"));
		assertThat(currency.getSymbol(), not(equalTo("1")));
	}
	
	@Test 
	void testSettersOfCurrency(){
		currency.setCode("EUR");
		currency.setSymbol("€");
		
		Assertions.assertThat(currency.getCode())
			.isNotEmpty()
			.isNotNull()
			.isEqualTo("EUR");
		
		Assertions.assertThat(currency.getSymbol())
			.isNotEmpty()
			.isNotNull()
			.isNotEqualTo("$")
			.isEqualTo("€");
	}
	
}
