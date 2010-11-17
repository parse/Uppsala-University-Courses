package b_Money;

import static org.junit.Assert.*;

import org.junit.Before;
import org.junit.Test;

public class MoneyTest {
	Currency SEK, DKK, NOK, EUR;
	Money SEK100, EUR10, SEK200, EUR20, SEK0, EUR0, SEKn100;
	
	@Before
	public void setUp() throws Exception {
		SEK = new Currency("SEK", 0.15);
		DKK = new Currency("DKK", 0.20);
		EUR = new Currency("EUR", 1.5);
		SEK100 = new Money(10000, SEK);
		EUR10 = new Money(1000, EUR);
		SEK200 = new Money(20000, SEK);
		EUR20 = new Money(2000, EUR);
		SEK0 = new Money(0, SEK);
		EUR0 = new Money(0, EUR);
		SEKn100 = new Money(-10000, SEK);
	}

	@Test
	public void testGetAmount() {
		fail("Write test case here");
	}

	@Test
	public void testGetCurrency() {
		fail("Write test case here");
	}

	@Test
	public void testToString() {
		fail("Write test case here");
	}

	@Test
	public void testGlobalValue() {
		fail("Write test case here");
	}

	@Test
	public void testEqualsMoney() {
		fail("Write test case here");
	}

	@Test
	public void testAdd() {
		fail("Write test case here");
	}

	@Test
	public void testSub() {
		fail("Write test case here");
	}

	@Test
	public void testIsZero() {
		fail("Write test case here");
	}

	@Test
	public void testNegate() {
		fail("Write test case here");
	}

	@Test
	public void testCompareTo() {
		fail("Write test case here");
	}
}
