package b_Money;

import static org.junit.Assert.*;

import org.junit.Before;
import org.junit.Test;

public class MoneyTest {
	Currency SEK, DKK, NOK, EUR;
	Money SEK100, EUR10, SEK200, EUR20, SEK0, EUR0, SEKn100, SEK100p50, SEK100p45;
	
	@Before
	public void setUp() throws Exception {
		SEK = new Currency("SEK", 0.15);
		DKK = new Currency("DKK", 0.20);
		EUR = new Currency("EUR", 1.5);
		SEK100 = new Money(10000, SEK);
		SEK100p50 = new Money(10050, SEK);
		SEK100p45 = new Money(10045, SEK);
		EUR10 = new Money(1000, EUR);
		SEK200 = new Money(20000, SEK);
		EUR20 = new Money(2000, EUR);
		SEK0 = new Money(0, SEK);
		EUR0 = new Money(0, EUR);
		SEKn100 = new Money(-10000, SEK);
	}

	@Test
	public void testGetAmount() {
		/*
		 * Testing equal amounts: positive, negative and zero
		 */
		String msg = "Testing equal amounts";
		assertEquals(msg, 10000, SEK100);
		assertEquals(msg, 2000, EUR20);
		assertEquals(msg, 0, EUR0);
		assertEquals(msg, -10000, SEKn100);
	}

	@Test
	public void testGetCurrency() {
		/*
		 * Testing currencies: SEK = SEK, SEK != EUR
		 */
		String msg = "Testing currencies";
		assertSame(msg, SEK, SEK100.getCurrency() );
		assertSame(msg, EUR, EUR10.getCurrency() );
		
		assertNotSame(msg, EUR, SEK100.getCurrency() );
	}

	@Test
	public void testToString() {
		/*
		 * "(amount) (currencyname)", e.g. "10.5 SEK".
		 * Tests for negative (without dot) and positive with decimal numbers.
		 */
		String msg = "Testing human readable strings";
		assertEquals(msg, "-100 SEK", SEKn100.toString() );
		assertEquals(msg, "100.5 SEK", SEK100p50.toString() );
	}

	@Test
	public void testGlobalValue() {
		/*
		 * Tests if the universal values are the expected values. Test one without decimal values, and one that are to be rounded.
		 */
		String msg = "Testing universal values";
		assertEquals(msg, new Integer(3000), SEK200.universalValue());
		assertEquals(msg, new Integer(1507), SEK100p45.universalValue());
	}

	@Test
	public void testEqualsMoney() {
		String msg = "Testing money between currencies";
		assertTrue(msg, EUR20.equals(SEK200));
		assertFalse(msg, EUR20.equals(SEK0));
	}

	@Test
	public void testAdd() {
		assertEquals(SEK200.add(SEK200), SEK200.add(EUR20));
		assertEquals(SEK200, SEK100.add(SEK100));
	}

	@Test
	public void testSub() {
		assertEquals(SEK200.sub(SEK200), SEK200.sub(EUR20));
		assertEquals(SEK0, SEK100.sub(SEK100));
	}

	@Test
	public void testIsZero() {
		assertTrue(SEK0.isZero());
		assertFalse(EUR20.isZero());
	}

	@Test
	public void testNegate() {
		assertEquals(SEKn100, SEK100.negate() );
		assertEquals(SEK100, SEKn100.negate() );
	}

	@Test
	public void testCompareTo() {
		assertEquals(0, SEK200.compareTo(EUR20));
		assertEquals(-1, SEKn100.compareTo(EUR10));
		assertEquals(1, EUR20.compareTo(SEK0));
	}
}
