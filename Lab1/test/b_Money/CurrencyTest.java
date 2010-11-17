package b_Money;

import static org.junit.Assert.*;

import org.junit.Before;
import org.junit.Test;

public class CurrencyTest {
	Currency SEK, DKK, NOK, EUR;
	
	@Before
	public void setUp() throws Exception {
		/* Setup currencies with exchange rates */
		SEK = new Currency("SEK", 0.15);
		DKK = new Currency("DKK", 0.20);
		EUR = new Currency("EUR", 1.5);
	}

	@Test
	public void testGetName() {
		fail("Write test case here");
	}
	
	@Test
	public void testGetRate() {
		fail("Write test case here");
	}
	
	@Test
	public void testSetRate() {
		fail("Write test case here");
	}
	
	@Test
	public void testGlobalValue() {
		fail("Write test case here");
	}
	
	@Test
	public void testValueInThisCurrency() {
		fail("Write test case here");
	}

}
