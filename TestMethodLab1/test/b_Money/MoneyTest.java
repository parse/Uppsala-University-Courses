package b_Money;

import static org.junit.Assert.*;

import org.junit.Before;
import org.junit.Test;
import org.omg.CORBA.SystemException;

import com.sun.corba.se.impl.orbutil.ObjectUtility;
import com.sun.xml.internal.messaging.saaj.packaging.mime.internet.ParseException;

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
		 * Testing equal amounts: 
		 * 1,2 positive ammount
		 * 3 zero
		 * 4 negative
		 */
		String msg = "Testing equal amounts";
		assertEquals(msg, new Integer(10000), SEK100.getAmount());
		assertEquals(msg, new Integer(2000), EUR20.getAmount());
		assertEquals(msg, new Integer(0), EUR0.getAmount());
		assertEquals(msg, new Integer(-10000), SEKn100.getAmount());
	}

	@Test
	public void testGetCurrency() {
		/*
		 * Testing currencies:
		 * 1 SEK == currency of SEK100
		 * 2 EUR == currency of EUR10
		 * 3 EUR != currency of SEK100
		 */
		String msg = "Testing currencies";
		assertSame(msg, SEK, SEK100.getCurrency() );
		assertSame(msg, EUR, EUR10.getCurrency() );
		
		assertNotSame(msg, EUR, SEK100.getCurrency() );
	}

	@Test
	public void testToString() {
		/*
		 * 1 Test if negative and without decimal numbers is correct
		 * 2 Test positive with decimal numbers
		 */
		String msg = "Testing human readable strings";
		assertEquals(msg, "-100 SEK", SEKn100.toString() );
		assertEquals(msg, "100.5 SEK", SEK100p50.toString() );
	}

	@Test
	public void testGlobalValue() {
		/*
		 * Tests universal values:
		 * 1 Money -> Universal without decimals
		 * 2 Money -> Universal with decimals rounded up
		 */
		String msg = "Testing universal values";
		assertEquals(msg, new Integer(3000), SEK200.universalValue());
		assertEquals(msg, new Integer(1507), SEK100p45.universalValue());
	}

	@Test
	public void testEqualsMoney() {
		String msg = "Testing money between currencies";
		/*
		 * Test equality of money:
		 * 1 Test that SEK (200) can be compared to EUR (20)
		 * 2 Test that we can get false from unequal moneys
		 */
		assertTrue(msg, EUR20.equals(SEK200));
		assertFalse(msg, EUR20.equals(SEK0));
	}

	@Test
	public void testAdd() {
		/*
		 * Test addition functionality:
		 * 1 Different currency based moneys
		 * 2 Same currencies added
		 */
		/*Money testSEK400 = new Money(20000, SEK);
		Money testSEK300 = new Money(30000, SEK);
		Money testSEK100 = new Money(10000, SEK);
		Money testEUR20 = new Money(2000, EUR);
		Money testSEK200 = new Money(20000, SEK);*/
		
		assertEquals((new Money(40000, SEK)).getAmount().intValue(), SEK200.add(SEK200).getAmount().intValue());
		assertEquals((new Money(40000, SEK)).getAmount().intValue(), SEK200.add(EUR20).getAmount().intValue());
	}

	@Test
	public void testSub() {
		/*
		 * Test subtraction functionality:
		 * 1 Different currency based moneys
		 * 2 Same currencies subtracted to zero
		 */
		assertEquals(SEK200.sub(SEK200), SEK200.sub(EUR20));
		assertEquals(SEK0, SEK100.sub(SEK100));
	}

	@Test
	public void testIsZero() {
		/*
		 * Zero test:
		 * 1 Test if zero is generating true
		 * 2 Test if non zero money generates false
		 */
		assertTrue(SEK0.isZero());
		assertFalse(EUR20.isZero());
	}

	@Test
	public void testNegate() {
		/*
		 * Test negate functionality:
		 * 1 Positive negated should be negative
		 * 2 Negative value negated should be positive
		 */
		assertEquals(SEKn100.getAmount(), SEK100.negate().getAmount() );
		assertEquals(SEK100.getAmount(), SEKn100.negate().getAmount() );
	}

	@Test
	public void testCompareTo() {
		/*
		 * Test compare:
		 * 1 Original - compared = 0 ==> 0
		 * 2 Original - compared < 0 ==> -1
		 * 3 Original - compared > 0 ==> 1
		 */
		assertEquals(0, SEK200.compareTo(EUR20));
		assertEquals(-1, SEKn100.compareTo(EUR10));
		assertEquals(1, EUR20.compareTo(SEK0));
	}
}
