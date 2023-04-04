package net.bumblebee.controller;

import net.bumblebee.entity.Customer;
import net.bumblebee.service.CustomerService;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;
import java.util.List;


@RestController
@CrossOrigin("http://localhost:3000")
public class CustomerController {

	private CustomerService customerService;

	public CustomerController(CustomerService customerService) {
		super();
		this.customerService = customerService;
	}

	@GetMapping("/customers")
	public ResponseEntity<List<Customer>> getAllCustomers() {
		List<Customer> customers = customerService.getAllCustomers();
		return ResponseEntity.ok().body(customers);
	}

	@GetMapping("/customers/{id}")
	public ResponseEntity<Customer> getCustomerById(@PathVariable Long id) {
		Customer customer = customerService.getCustomerById(id);
		if (customer == null) {
			return ResponseEntity.notFound().build();
		}
		return ResponseEntity.ok().body(customer);
	}

	@PostMapping("/customers/new")
	public ResponseEntity<Customer> createCustomer(@RequestBody Customer customer) {
		customerService.saveCustomer(customer);
		URI location = ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}")
				.buildAndExpand(customer.getId()).toUri();
		return ResponseEntity.created(location).body(customer);
	}

	@PutMapping("/customers/{id}")
	public ResponseEntity<Customer> updateCustomer(@PathVariable Long id, @RequestBody Customer customer) {
		Customer existingCustomer = customerService.getCustomerById(id);
		if (existingCustomer == null) {
			return ResponseEntity.notFound().build();
		}
		existingCustomer.setFirstName(customer.getFirstName());
		existingCustomer.setLastName(customer.getLastName());
		existingCustomer.setDateOfBirth(customer.getDateOfBirth());
		existingCustomer.setEmail(customer.getEmail());
		existingCustomer.setLoanAmount(customer.getLoanAmount());
		customerService.updateCustomer(existingCustomer);
		return ResponseEntity.ok().body(existingCustomer);
	}

	@DeleteMapping("/customers/{id}")
	public ResponseEntity<Void> deleteCustomer(@PathVariable Long id) {
		customerService.deleteCustomerById(id);
		return ResponseEntity.noContent().build();
	}

}
