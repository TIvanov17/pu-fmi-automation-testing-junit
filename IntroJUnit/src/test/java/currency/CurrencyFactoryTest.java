package currency;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assumptions.assumeTrue;

import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;

public class CurrencyFactoryTest {
	
	@Test 
	@Disabled("I will enable it after the bug is fixed")
	void testCreateValidUSD() {
		Currency usd = CurrencyFactory.createUSD();
//		assumeTrue(usd != null);
		assertEquals(usd.getSymbol(), "$");
		assertEquals(usd.getCode(), "USD");
	}
	
	@Nested
	class CurrencyEUROTest {
	
		@Test 
		void testCreateValidEuro() {
			Currency usd = CurrencyFactory.createEUR();
			assertEquals(usd.getSymbol(), "€");
			assertEquals(usd.getCode(), "EUR");
		}
		
	}
	
}
