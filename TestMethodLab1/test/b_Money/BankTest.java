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
		assertEquals("Nordea", Nordea.getName() );
	}

	@Test
	public void testGetCurrency() {
		assertSame(SEK, SweBank.getCurrency() );
		assertNotSame(SEK, DanskeBank.getCurrency() );
	}

	@Test
	public void testOpenAccount() throws AccountExistsException, AccountDoesNotExistException {		
		SweBank.openAccount("Test");
		SweBank.deposit("Test", new Money(100, SEK) );
	}

	@Test
	public void testDeposit() throws AccountDoesNotExistException {
		Nordea.deposit("Bob", new Money(100, SEK));
		assertEquals(100, Nordea.getBalance("Bob").intValue() );
		
		Integer testDepositMultipleCurrencies = new Money(100, SEK).add(new Money(100, DKK)).getAmount();
		
		Nordea.deposit("Bob", new Money(100, DKK));
		assertEquals(testDepositMultipleCurrencies.intValue(), Nordea.getBalance("Bob").intValue() );
	}

	@Test
	public void testWithdraw() throws AccountDoesNotExistException {
		SweBank.deposit("Bob", new Money(100, SEK));
		
		Integer testDepositMultipleCurrencies = new Money(100, SEK).sub(new Money(100, DKK)).getAmount();
		
		SweBank.withdraw("Bob", new Money(100, DKK));
		assertEquals(testDepositMultipleCurrencies.intValue(), SweBank.getBalance("Bob").intValue() );
	}
	
	@Test
	public void testGetBalance() throws AccountDoesNotExistException {
		Nordea.deposit("Bob", new Money(100, SEK));
		assertEquals(100, Nordea.getBalance("Bob").intValue() );
	}
	
	@Test
	public void testTransfer() throws AccountDoesNotExistException {		
		int preTransferAmountBob = SweBank.getBalance("Bob");
		int preTransferAmountUlrika = SweBank.getBalance("Ulrika");
		
		SweBank.transfer("Bob", "Ulrika", new Money(100, SEK));
		
		assertEquals(preTransferAmountBob-100, SweBank.getBalance("Bob").intValue());
		assertEquals(preTransferAmountUlrika+100, SweBank.getBalance("Ulrika").intValue());
	}
	
	@Test
	public void testTimedPayment() throws AccountDoesNotExistException {
		int preTransferAmountBob = SweBank.getBalance("Bob");
		int preTransferAmountBobNordea = Nordea.getBalance("Bob");
		
		SweBank.addTimedPayment("Bob", "Test", 1, 1, new Money(100, SEK), Nordea, "Bob");
		SweBank.tick();
		
		// Same amount
		assertEquals(preTransferAmountBob, SweBank.getBalance("Bob").intValue());
		assertEquals(preTransferAmountBobNordea, Nordea.getBalance("Bob").intValue());
		
		SweBank.tick();
		
		// 100 withdrawn
		assertEquals(preTransferAmountBob-100, SweBank.getBalance("Bob").intValue());
		assertEquals(preTransferAmountBobNordea+100, Nordea.getBalance("Bob").intValue());
		
		SweBank.tick();
		SweBank.tick();
		
		assertEquals(preTransferAmountBob-200, SweBank.getBalance("Bob").intValue());
		assertEquals(preTransferAmountBobNordea+200, Nordea.getBalance("Bob").intValue());
		
		SweBank.removeTimedPayment("Bob", "Test");
		
		SweBank.tick();
		SweBank.tick();
		SweBank.tick();
		SweBank.tick();
		
		assertEquals(preTransferAmountBob-200, SweBank.getBalance("Bob").intValue());
		assertEquals(preTransferAmountBobNordea+200, Nordea.getBalance("Bob").intValue());
	}
}
