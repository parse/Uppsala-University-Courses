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
		/*
		 * Test name:
		 * 1,2 Test if returned currency name is correct
		 */
		assertEquals("SEK", SEK.getName() );
		assertEquals("EUR", EUR.getName() );
	}
	
	@Test
	public void testGetRate() {
		/*
		 * Test getRate:
		 * 1, 2 Test if rate is the set value
		 */
		assertEquals(new Double(0.15), SEK.getRate());
		assertEquals(new Double(0.20), DKK.getRate());
	}
	
	@Test
	public void testSetRate() {
		/*
		 * Test setRate:
		 * Set the rate using the function, then:
		 * 1 Test if the new rate is set
		 * Reset to the old test rate
		 */
		EUR.setRate(1.6);
		assertEquals(new Double(1.6), EUR.getRate() );
		// Reset rate
		EUR.setRate(1.5);
	}
	
	@Test
	public void testGlobalValue() {
		/*
		 * Test universalValue:
		 * 1 See that a value is returned correctly according to the rate
		 * 2 Check that decimal value is rounded correctly
		 */
		assertEquals(new Integer(3000), SEK.universalValue(20000));
		assertEquals(new Integer(1507), SEK.universalValue(10045));
	}
	
	@Test
	public void testValueInThisCurrency() {
		/*
		 * Test getRate:
		 * 1 Test if converted value is correct
		 */
		assertEquals(new Integer(100), SEK.valueInThisCurrency(10, EUR));
	}

}
