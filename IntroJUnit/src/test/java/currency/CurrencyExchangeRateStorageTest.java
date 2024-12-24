package currency;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import java.math.BigDecimal;
import java.util.stream.Stream;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.MethodSource;
import org.junit.jupiter.params.provider.ValueSource;

import exceptions.GeneralApplicationException;

public class CurrencyExchangeRateStorageTest {

	private CurrencyExchangeRateStorage storage;
	
	@BeforeEach
	void setUp() {
		storage = CurrencyExchangeRateStorage.getInstance();
	}
	
	@Test
	void testFoundExchangeRate() {
		assertDoesNotThrow(() -> storage.getExchangeRate("USD", "USD"));
	}
	
	@Test
	void testNotFoundExchangeRate() {
		assertThrows(
				GeneralApplicationException.class, 
				() -> storage.getExchangeRate("NESHTO", "DRUGO"));
	}
	
	@Test
	void testGetExchangeRateForEqualCodes() {
		BigDecimal actualRateOfOne = storage.getExchangeRate("USD","USD");
		assertEquals(new BigDecimal("1.0"), actualRateOfOne);
	}
	
	@ParameterizedTest
	@MethodSource("getParam")
	void testAddExchangeRateFromMethod(String param) {
		storage.addExchangeRate(
				"TEST_FROM", 
				"TEST_TO", 
				new BigDecimal(param)
		);
		
		BigDecimal actualExchangeRate =
				storage.getExchangeRate("TEST_FROM", "TEST_TO");
		
		assertEquals(new BigDecimal(param), actualExchangeRate);
	}
	
	static Stream<String> getParam(){
		return Stream.of("0.95", "1.22", "5.51");
	}
	
	@ParameterizedTest
	@ValueSource(doubles =  { 0.95, 1.22, 5.51 })
	void testAddExchangeRateFromParamsUsingDoubleArray(double exchangeRate) {
		storage.addExchangeRate(
				"TEST_FROM", 
				"TEST_TO", 
				new BigDecimal(exchangeRate)
		);
		
		BigDecimal actualExchangeRate =
				storage.getExchangeRate("TEST_FROM", "TEST_TO");
		
		assertEquals(new BigDecimal(exchangeRate), actualExchangeRate);
	}
	
	@ParameterizedTest
	@ValueSource(strings = { "0.95", "1.22", "5.51" })
	void testAddExchangeRateFromParams(String exchangeRate) {
		storage.addExchangeRate(
				"TEST_FROM", 
				"TEST_TO", 
				new BigDecimal(exchangeRate)
		);
		
		BigDecimal actualExchangeRate =
				storage.getExchangeRate("TEST_FROM", "TEST_TO");
		
		assertEquals(new BigDecimal(exchangeRate), actualExchangeRate);
	}
	
	@Test
	void testAddExchangeRate() {
		storage.addExchangeRate(
				"TEST_FROM", 
				"TEST_TO", 
				new BigDecimal("0.95")
		);
		
		BigDecimal actualExchangeRate =
				storage.getExchangeRate("TEST_FROM", "TEST_TO");
		
		assertEquals(0.95, actualExchangeRate.doubleValue());
		assertEquals(new BigDecimal("0.95"), actualExchangeRate);
	}
	
	
}
