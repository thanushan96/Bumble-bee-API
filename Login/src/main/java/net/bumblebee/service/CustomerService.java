package net.bumblebee.service;
import net.bumblebee.entity.Customer;

import java.util.List;

public interface CustomerService {
	List<Customer> getAllCustomers();
	
	Customer saveCustomer(Customer customer);
	
	Customer getCustomerById(Long id);
	
	Customer updateCustomer(Customer customer);
	
	void deleteCustomerById(Long id);
}
