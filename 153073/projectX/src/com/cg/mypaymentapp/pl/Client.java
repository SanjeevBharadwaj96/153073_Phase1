package com.cg.mypaymentapp.pl;

import java.math.BigDecimal;
import java.util.Scanner;

import com.cg.mypaymentapp.beans.Customer;
import com.cg.mypaymentapp.beans.Wallet;
import com.cg.mypaymentapp.service.WalletService;
import com.cg.mypaymentapp.service.WalletServiceImpl;

public class Client {
	public static void main(String[] args) {

		WalletService service;
		{
			service = new WalletServiceImpl();
		}

		Scanner console = new Scanner(System.in);

		String ans;
		int choice;
		do {
			System.out.println("My Payment APP");
			System.out.println("enter the choice");
			System.out.println("1.Create Account");
			System.out.println("2.Show Balance");
			System.out.println("3.Fund Transfer");
			System.out.println("4.Deposit Amount");
			System.out.println("5. Withdraw AMount");
			choice = console.nextInt();

			switch (choice) {
			case 1:
				System.out.println("Create Account");
				System.out.println("enter the name of the customer");
				String name = console.next();
				System.out.println("enter the mobile number");
				String mobileNumber = console.next();
				System.out.println("enter the amount");
				BigDecimal amount = console.nextBigDecimal();
				Wallet wallet = new Wallet(amount);
				Customer customer1 = service.createAccount(name, mobileNumber, amount);
				System.out.println(customer1);
				break;
			case 2:
				System.out.println("Show balance");
				System.out.println("enter the mobile number of the customer:");
				String mobileNo = console.next();
				Customer customer = service.showBalance(mobileNo);
				System.out.println(customer);
				break;
			case 3:
				System.out.println("Fund Tranfer");
				System.out.println("enter the source mobile Number");
				String sourceMobileNumber = console.next();
				System.out.println("enter the target mobile number");
				String targetMobileNumber = console.next();
				System.out.println("enter the amount to be transferred:");
				BigDecimal tAmount = console.nextBigDecimal();
				Customer customer2 = service.fundTransfer(sourceMobileNumber, targetMobileNumber, tAmount);
				System.out.println(customer2);
				break;
			case 4:
				System.out.println("Deposit amount");
				System.out.println("enter the mobile number:");
				String dMobileNumber = console.next();
				System.out.println("enter the amount to be deposited:");
				BigDecimal dAmount = console.nextBigDecimal();
				Customer dCustomer = service.depositAmount(dMobileNumber, dAmount);
				System.out.println(dCustomer);
				break;
			case 5:
				System.out.println("withdraw amount");
				System.out.println("enter the mobile number");
				String wMobileNumber = console.next();
				System.out.println("enter the amount to be withdrwan:");
				BigDecimal wAmount = console.nextBigDecimal();
				Customer wCustomer = service.withdrawAmount(wMobileNumber, wAmount);
				System.out.println(wCustomer);
				break;

			}

			System.out.println("DO YOU WANT TO CONTINUE:");
			ans = console.next();
		} while ((ans.equals("yes")) || (ans.equals("y") || (ans.equals("YES"))));
	}
}
