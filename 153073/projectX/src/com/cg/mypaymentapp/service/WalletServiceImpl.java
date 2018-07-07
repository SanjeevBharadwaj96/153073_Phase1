package com.cg.mypaymentapp.service;

import java.math.BigDecimal;
import java.util.HashMap;
import java.util.Map;
import java.util.Random;
import java.util.Scanner;

import com.cg.mypaymentapp.Exception.InsufficientBalanceException;
import com.cg.mypaymentapp.Exception.InvalidInputException;
import com.cg.mypaymentapp.Repo.WalletRepo;
import com.cg.mypaymentapp.Repo.WalletRepoImpl;
import com.cg.mypaymentapp.beans.Customer;
import com.cg.mypaymentapp.beans.Wallet;

public class WalletServiceImpl implements WalletService {

	private WalletRepo repo;

	public WalletServiceImpl(Map<String, Customer> data) {
		repo = new WalletRepoImpl(data);
	}

	public WalletServiceImpl(WalletRepo repo) {
		super();
		this.repo = repo;
	}

	public WalletServiceImpl() {
		repo = new WalletRepoImpl(new HashMap<String, Customer>());
	}

	public Customer createAccount(String name, String mobileNo, BigDecimal amount) throws InvalidInputException {
		Scanner console = new Scanner(System.in);
		Customer customer = new Customer(name, mobileNo, new Wallet(amount));
		while (true) {

			try {
				if (validation(mobileNo)) {
					boolean cust = repo.save(customer);
					if (cust == true) {
						return customer;
					} else {
						return null;
					}
				} else {
					System.err.println("invalidmobile number:");
					System.out.println("enter proper 10 digit mobile number");
					customer.setMobileNo(console.next());
				}

			}

			catch (Exception e) {
				System.err.println("Wrong details!!start with a capital letter:");
				System.out.println("enter the details again:");
				customer.toString();
			}
		}

	}

	public boolean validation(String mobileNo) throws Exception {

		String pattern = "[1-9][0-9][0-9][0-9][0-9][0-9][0-9][0-9][0-9][0-9]";
		if (mobileNo.matches(pattern)) {
			return true;
		} else
			throw new InvalidInputException("enter proper numbe");

	}

	public Customer showBalance(String mobileNo) {
		Customer customer = repo.findOne(mobileNo);
		if (customer != null)
			return customer;
		else
			return null;

	}

	public Customer fundTransfer(String sourceMobileNo, String targetMobileNo, BigDecimal amount)
			throws InvalidInputException {
		try {
			Customer customer21 = repo.findOne(sourceMobileNo);
			Customer customer22 = repo.findOne(targetMobileNo);
			if ((customer21 != null) && (customer22 != null)) {
				depositAmount(targetMobileNo, amount);
				withdrawAmount(sourceMobileNo, amount);

			}
			return customer21;

		} catch (Exception e) {
			System.err.println("invalid input details");

		}
		return null;
	}

	public Customer depositAmount(String mobileNo, BigDecimal amount) throws InvalidInputException {
		Customer dcustomer = repo.findOne(mobileNo);
		try {
			if (dcustomer != null) {
				Wallet iAmount = dcustomer.getWallet();
				BigDecimal updatedBalance = iAmount.getBalance().add(amount);
				Wallet wallet = new Wallet(updatedBalance);
				dcustomer.setWallet(wallet);
				return dcustomer;
			} else {
				return null;
			}
		} catch (Exception e) {
			System.err.println("invalid input details");
		}
		return null;
	}

	public Customer withdrawAmount(String mobileNo, BigDecimal amount) throws InsufficientBalanceException {
		Customer wcustomer = repo.findOne(mobileNo);
		try {
			if (wcustomer != null) {
				Wallet iAmount = wcustomer.getWallet();
				BigDecimal updatedBalance = iAmount.getBalance().subtract(amount);
				Wallet wallet = new Wallet(updatedBalance);
				int ubal = Integer.parseInt(updatedBalance.toString());
				if (ubal < 0) {
					System.err.println("insufficient balance");
					return null;
				}
				wcustomer.setWallet(wallet);
				return wcustomer;
			} else {
				return null;
			}
		} catch (Exception e) {
			System.err.println("insufficient funds");
			return null;
		}
	}

}
