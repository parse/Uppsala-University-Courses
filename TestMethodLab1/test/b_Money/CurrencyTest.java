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
		assertEquals("SEK", SEK.getName() );
		assertEquals("EUR", EUR.getName() );
	}
	
	@Test
	public void testGetRate() {
		assertEquals(new Double(0.15), SEK.getRate());
		assertEquals(new Double(0.20), DKK.getRate());
	}
	
	@Test
	public void testSetRate() {
		EUR.setRate(1.6);
		assertEquals(new Double(1.6), EUR.getRate() );
		// Reset rate
		EUR.setRate(1.5);
	}
	
	@Test
	public void testGlobalValue() {
		assertEquals(new Integer(3000), SEK.universalValue(20000));
	}
	
	@Test
	public void testValueInThisCurrency() {
		assertEquals(new Integer(100), SEK.valueInThisCurrency(10, EUR));
	}

}
