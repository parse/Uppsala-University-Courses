package b_Money;

import static org.junit.Assert.*;

import org.junit.Before;
import org.junit.Test;

public class BankTest {
	Currency SEK, DKK;
	Bank SweBank, Nordea, DanskeBank;
	
	@Before
	public void setUp() throws Exception {
		DKK = new Currency("DKK", 0.20);
		SEK = new Currency("SEK", 0.15);
		SweBank = new Bank("SweBank", SEK);
		Nordea = new Bank("Nordea", SEK);
		DanskeBank = new Bank("DanskeBank", DKK);
		SweBank.openAccount("Ulrika");
		SweBank.openAccount("Bob");
		Nordea.openAccount("Bob");
		DanskeBank.openAccount("Gertrud");
	}

	@Test
	public void testGetName() {
		// Check that we get correct name
		assertEquals("Nordea", Nordea.getName() );
	}

	@Test
	public void testGetCurrency() {
		// Try a true and a false statement of currency objects
		assertSame(SEK, SweBank.getCurrency() );
		assertNotSame(SEK, DanskeBank.getCurrency() );
	}

	@Test
	public void testOpenAccount() throws AccountExistsException, AccountDoesNotExistException {		
		// Try to open an account
		SweBank.openAccount("Test");
		// Try to deposit - If account is opened, we can do a deposit without getting exception
		SweBank.deposit("Test", new Money(100, SEK) );
	}

	@Test
	public void testDeposit() throws AccountDoesNotExistException {
		// Try to deposit to an account of a bank and check that we get the desired balance
		Nordea.deposit("Bob", new Money(100, SEK));
		assertEquals(100, Nordea.getBalance("Bob").intValue() );
		
		// Try to deposit money of a currency other than the banks currency
		Integer testDepositMultipleCurrencies = new Money(100, SEK).add(new Money(100, DKK)).getAmount();
		Nordea.deposit("Bob", new Money(100, DKK));
		assertEquals(testDepositMultipleCurrencies.intValue(), Nordea.getBalance("Bob").intValue() );
	}

	@Test
	public void testWithdraw() throws AccountDoesNotExistException {
		// Set up a balance of +100
		SweBank.deposit("Bob", new Money(100, SEK));
		
		// The expected value is 100 SEK - 100 DKK
		Integer testDepositMultipleCurrencies = new Money(100, SEK).sub(new Money(100, DKK)).getAmount();
		
		// Try to withdraw DKK from SEK bank. Check if the value is the expected
		SweBank.withdraw("Bob", new Money(100, DKK));
		assertEquals(testDepositMultipleCurrencies.intValue(), SweBank.getBalance("Bob").intValue() );
	}
	
	@Test
	public void testGetBalance() throws AccountDoesNotExistException {
		// Deposit 100 SEK to bob
		Nordea.deposit("Bob", new Money(100, SEK));
		
		// We should have 100 as balance (SEK)
		assertEquals(100, Nordea.getBalance("Bob").intValue() );
	}
	
	@Test
	public void testTransfer() throws AccountDoesNotExistException {	
		// Get the current balances of two accounts
		int preTransferAmountBob = SweBank.getBalance("Bob");
		int preTransferAmountUlrika = SweBank.getBalance("Ulrika");
		
		// Transfer some money
		SweBank.transfer("Bob", "Ulrika", new Money(100, SEK));
		
		// Check that money has been withdrawn
		assertEquals(preTransferAmountBob-100, SweBank.getBalance("Bob").intValue());
		// Check that deposit 
		assertEquals(preTransferAmountUlrika+100, SweBank.getBalance("Ulrika").intValue());
	}
	
	@Test
	public void testTimedPayment() throws AccountDoesNotExistException {
		// Get the current balances of two accounts
		int preTransferAmountBob = SweBank.getBalance("Bob");
		int preTransferAmountBobNordea = Nordea.getBalance("Bob");
		
		// Add a timed payment and tick
		SweBank.addTimedPayment("Bob", "Test", 1, 1, new Money(100, SEK), Nordea, "Bob");
		SweBank.tick();
		
		// Should be same amounts
		assertEquals(preTransferAmountBob, SweBank.getBalance("Bob").intValue());
		assertEquals(preTransferAmountBobNordea, Nordea.getBalance("Bob").intValue());
		
		SweBank.tick();
		
		// 100 withdrawn
		assertEquals(preTransferAmountBob-100, SweBank.getBalance("Bob").intValue());
		// 100 deposit
		assertEquals(preTransferAmountBobNordea+100, Nordea.getBalance("Bob").intValue());
		
		SweBank.tick();
		SweBank.tick();
		
		// 200 withdrawn
		assertEquals(preTransferAmountBob-200, SweBank.getBalance("Bob").intValue());
		// 200 deposit
		assertEquals(preTransferAmountBobNordea+200, Nordea.getBalance("Bob").intValue());
		
		// Remove the payment
		SweBank.removeTimedPayment("Bob", "Test");
		
		SweBank.tick();
		SweBank.tick();
		SweBank.tick();
		SweBank.tick();
		
		// Check that the amounts are the same as last time, so we know the payment has been removed
		assertEquals(preTransferAmountBob-200, SweBank.getBalance("Bob").intValue());
		assertEquals(preTransferAmountBobNordea+200, Nordea.getBalance("Bob").intValue());
	}
}
