package com.cg.mypaymentapp.Test;
import static org.junit.Assert.assertEquals;

import java.math.BigDecimal;
import java.util.HashMap;
import java.util.Map;

import junit.framework.Assert;

import org.junit.Before;
import org.junit.Test;
import org.omg.CORBA.RepositoryIdHelper;

import com.cg.mypaymentapp.Exception.InvalidInputException;
import com.cg.mypaymentapp.beans.Customer;
import com.cg.mypaymentapp.beans.Wallet;
import com.cg.mypaymentapp.service.WalletService;
import com.cg.mypaymentapp.service.WalletServiceImpl;


public class TestClass {

	
	WalletService service;
	
	@Before
	public void initData(){
		 Map<String,Customer> data= new HashMap<String, Customer>();
		 Customer cust1=new Customer("Amit", "9900112212",new Wallet(new BigDecimal(9000)));
		 Customer cust2=new Customer("Ajay", "9963242422",new Wallet(new BigDecimal(6000)));
		 Customer cust3=new Customer("Yogini", "9922950519",new Wallet(new BigDecimal(7000)));
				
		 data.put("9900112212", cust1);
		 data.put("9963242422", cust2);	
		 data.put("9922950519", cust3);	
			service= new WalletServiceImpl(data);
			
	}
@Test(expected=InvalidInputException.class)
public void testvalidateMobileNo() throws Exception {
	service.validation("984");
}

@Test
public void testvalidateShowBalance() {
	Customer customer=service.showBalance("9900112212");
	String bal=customer.getWallet().getBalance().toString();
	assertEquals("9000", bal);
}

@Test
public void testFundTransferFirst() {
	Customer customer=service.fundTransfer("9900112212","9963242422",new BigDecimal(1000));
	String bal=customer.getWallet().getBalance().toString();
	assertEquals("8000", bal);
}

@Test
public void testWithdrawAmount() {
	Customer customer=service.withdrawAmount("9900112212",new BigDecimal(9000));
	String bal=customer.getWallet().getBalance().toString();
	assertEquals("0", bal);
}

@Test
public void testDepositAmount() {
	Customer customer=service.depositAmount("9900112212",new BigDecimal(1000));
	String bal=customer.getWallet().getBalance().toString();
	assertEquals("10000", bal);
}

@Test
public void testObjectNameVerification() {
	WalletService service= new WalletServiceImpl();
	Customer customer=service.createAccount("asd","9900112212",new BigDecimal(1000));
	assertEquals("asd", customer.getName());
}

@Test
public void testObjectNumberVerification() {
	WalletService service= new WalletServiceImpl();
	Customer customer=service.createAccount("asd","9900112212",new BigDecimal(500));
	assertEquals("9900112212", customer.getMobileNo());
}




}


