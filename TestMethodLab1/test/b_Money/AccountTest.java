package b_Money;

import org.junit.Before;
import org.junit.Test;
import static org.junit.Assert.*;

public class AccountTest {
	Currency SEK, DKK;
	Bank Nordea;
	Bank DanskeBank;
	Bank SweBank;
	Account testAccount;
	
	@Before
	public void setUp() throws Exception {
		SEK = new Currency("SEK", 0.15);
		SweBank = new Bank("SweBank", SEK);
		SweBank.openAccount("Alice");
		testAccount = new Account("Hans", SEK);
		testAccount.deposit(new Money(10000000, SEK));

		SweBank.deposit("Alice", new Money(1000000, SEK));
	}
	
	@Test
	public void testAddRemoveTimedPayment() {
		// Test addTimedPayment and check if it exists after function call
		testAccount.addTimedPayment("Test", 1, 1, new Money(100, SEK), SweBank, "Alice");
		assertTrue( testAccount.timedPaymentExists("Test") );
		
		// Try to remove timed payment and check if it is removed
		testAccount.removeTimedPayment("Test");
		assertFalse( testAccount.timedPaymentExists("Test") );
	}
	
	@Test
	public void testTimedPayment() throws AccountDoesNotExistException {
		// The amounts before any ticks and payments
		int preTransferAmountHans = testAccount.getBalance().getAmount().intValue();
		int preTransferAmountAlice = SweBank.getBalance("Alice").intValue();
		
		// Set up the payment and tick once
		testAccount.addTimedPayment("Test", 1, 1, new Money(100, SEK), SweBank, "Alice");
		testAccount.tick();
		
		// There should be the same amount on each account
		assertEquals(preTransferAmountHans, testAccount.getBalance().getAmount().intValue() );
		assertEquals(preTransferAmountAlice, SweBank.getBalance("Alice").intValue() );
		
		testAccount.tick();
		
		// 100 withdrawn from testAccount. Alice gets deposit of 100
		assertEquals(preTransferAmountHans-100, testAccount.getBalance().getAmount().intValue() );
		assertEquals(preTransferAmountAlice+100, SweBank.getBalance("Alice").intValue() );
		
		// Tick for another payment
		testAccount.tick();
		testAccount.tick();
		
		// Another 100 withdrawn/deposit
		assertEquals(preTransferAmountHans-200, testAccount.getBalance().getAmount().intValue() );
		assertEquals(preTransferAmountAlice+200, SweBank.getBalance("Alice").intValue() );
		
		// Remove payment
		testAccount.removeTimedPayment("Test");
		
		// Tick some times, and afterwards check if the amounts are still the same
		testAccount.tick();
		testAccount.tick();
		testAccount.tick();
		testAccount.tick();
		
		assertEquals(preTransferAmountHans-200, testAccount.getBalance().getAmount().intValue() );
		assertEquals(preTransferAmountAlice+200, SweBank.getBalance("Alice").intValue() );
	}

	@Test
	public void testAddWithdraw() {
		// Amount before deposit and withdraw
		int preTransferAmountTestaccount = testAccount.getBalance().getAmount().intValue();
		
		// Try to deposit 200 and check if desired value
		testAccount.deposit(new Money(200, SEK));
		assertEquals(preTransferAmountTestaccount+200, testAccount.getBalance().getAmount().intValue() );
		
		// Try to withdraw 200 and check value
		testAccount.withdraw(new Money(200, SEK));
		assertEquals(preTransferAmountTestaccount, testAccount.getBalance().getAmount().intValue() );
	}
	
	@Test
	public void testGetBalance() {
		// Test if we get the correct balance from an account
		assertEquals(10000000, testAccount.getBalance().getAmount().intValue() );
	}
}
