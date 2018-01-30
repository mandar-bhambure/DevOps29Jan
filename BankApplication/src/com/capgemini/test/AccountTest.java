package com.capgemini.test;

import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import com.capgemini.exceptions.InsufficientBalanceException;
import com.capgemini.exceptions.InsufficientInitialAmountException;
import com.capgemini.exceptions.InvalidAccountNumberException;
import com.capgemini.model.Account;
import com.capgemini.repository.AccountRepository;
import com.capgemini.service.AccountService;
import com.capgemini.service.AccountServiceImpl;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.when;
public class AccountTest {

	AccountService accountService;
	
	@Mock
	AccountRepository accountRepository;
	@Before
	public void setUp() throws Exception {
		MockitoAnnotations.initMocks(this);
		
		accountService = new AccountServiceImpl(accountRepository);
	}

	/*
	 * create account
	 * 1.when the amount is less than 500 then system should throw exception
	 * 2.when the valid info is passed account should be created successfully
	 */
	
	@Test(expected=com.capgemini.exceptions.InsufficientInitialAmountException.class)
	public void whenTheAmountIsLessThan500SystemShouldThrowException() throws InsufficientInitialAmountException
	{
		accountService.createAccount(101, 400);
	}
	
	@Test
	public void whenTheValidInfoIsPassedAccountShouldBeCreatedSuccessfully() throws InsufficientInitialAmountException
	{
		Account account =new Account();
		account.setAccountNumber(101);
		account.setAmount(5000);
		when(accountRepository.save(account)).thenReturn(true);
		assertEquals(account, accountService.createAccount(101, 5000));
	}

	@Test(expected=com.capgemini.exceptions.InvalidAccountNumberException.class)
	public void whenTheAccountNumberIsNotValidDepositThrowException() throws InvalidAccountNumberException
	{
		accountService.depositAccount(0, 5000);
	}
	
	@Test
	public void whenTheValidInfoIsPassedAmountShouldBeDepositedSuccessfully() throws InvalidAccountNumberException
	{
		Account account =new Account();
		account.setAccountNumber(101);
		account.setAmount(5000);
		when(accountRepository.searchAccount(101)).thenReturn(account);
		assertEquals(account.getAmount()+5000, accountService.depositAccount(101, 5000));
	}
	
	@Test(expected=com.capgemini.exceptions.InvalidAccountNumberException.class)
	public void whenTheAccountNumberIsNotValidWithdrawThrowException() throws InvalidAccountNumberException, InsufficientBalanceException
	{
		accountService.withdrawAccount(0, 3000);
	}
	
	@Test
	public void whenTheValidInfoIsPassedAmountShouldBeWithdrawnSuccessfully() throws InvalidAccountNumberException, InsufficientBalanceException
	{
		Account account =new Account();
		account.setAccountNumber(101);
		account.setAmount(3000);
		when(accountRepository.searchAccount(101)).thenReturn(account);
		assertEquals(account.getAmount()-3000, accountService.withdrawAccount(101, 3000));
	}

	
}
