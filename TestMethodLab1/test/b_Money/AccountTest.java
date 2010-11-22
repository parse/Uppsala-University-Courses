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
		testAccount.addTimedPayment("Test", 1, 1, new Money(100, SEK), SweBank, "Alice");
		assertTrue( testAccount.timedPaymentExists("Test") );
		
		testAccount.removeTimedPayment("Test");
		assertFalse( testAccount.timedPaymentExists("Test") );
	}
	
	@Test
	public void testTimedPayment() throws AccountDoesNotExistException {
		int preTransferAmountHans = testAccount.getBalance().getAmount().intValue();
		int preTransferAmountAlice = SweBank.getBalance("Alice").intValue();
		
		testAccount.addTimedPayment("Test", 1, 1, new Money(100, SEK), SweBank, "Alice");
		testAccount.tick();
		
		// Same amount
		assertEquals(preTransferAmountHans, testAccount.getBalance().getAmount().intValue() );
		assertEquals(preTransferAmountAlice, SweBank.getBalance("Alice").intValue() );
		
		testAccount.tick();
		
		// 100 withdrawn
		assertEquals(preTransferAmountHans-100, testAccount.getBalance().getAmount().intValue() );
		assertEquals(preTransferAmountAlice+100, SweBank.getBalance("Alice").intValue() );
		
		testAccount.tick();
		testAccount.tick();
		
		assertEquals(preTransferAmountHans-200, testAccount.getBalance().getAmount().intValue() );
		assertEquals(preTransferAmountAlice+200, SweBank.getBalance("Alice").intValue() );
		
		testAccount.removeTimedPayment("Test");
		
		testAccount.tick();
		testAccount.tick();
		testAccount.tick();
		testAccount.tick();
		
		assertEquals(preTransferAmountHans-200, testAccount.getBalance().getAmount().intValue() );
		assertEquals(preTransferAmountAlice+200, SweBank.getBalance("Alice").intValue() );
	}

	@Test
	public void testAddWithdraw() {
		int preTransferAmountTestaccount = testAccount.getBalance().getAmount().intValue();
		
		testAccount.deposit(new Money(200, SEK));
		assertEquals(preTransferAmountTestaccount+200, testAccount.getBalance().getAmount().intValue() );
		
		testAccount.withdraw(new Money(200, SEK));
		assertEquals(preTransferAmountTestaccount, testAccount.getBalance().getAmount().intValue() );
	}
	
	@Test
	public void testGetBalance() {
		assertEquals(10000000, testAccount.getBalance().getAmount().intValue() );
	}
}
