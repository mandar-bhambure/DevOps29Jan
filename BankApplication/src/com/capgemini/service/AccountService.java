package com.capgemini.service;

import com.capgemini.exceptions.InsufficientBalanceException;
import com.capgemini.exceptions.InsufficientInitialAmountException;
import com.capgemini.exceptions.InvalidAccountNumberException;
import com.capgemini.model.Account;

public interface AccountService {

	Account createAccount(int accountNumber, int amount) throws InsufficientInitialAmountException;
	
	int depositAccount(int accountNumber, int amount) throws InvalidAccountNumberException;

	int withdrawAccount(int accountNumber, int amount) throws InvalidAccountNumberException, InsufficientBalanceException;
	
}