package com.cg.mypaymentapp.Repo;

import com.cg.mypaymentapp.beans.Customer;

public interface WalletRepo {

	public boolean save(Customer customer);

	public Customer findOne(String mobileNo);
}
