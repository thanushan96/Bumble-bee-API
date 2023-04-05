package net.bumblebee.controller;

import net.bumblebee.entity.Customer;
import net.bumblebee.service.CustomerService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.MockitoJUnitRunner;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@RunWith(MockitoJUnitRunner.class)
public class CustomerControllerTest {

    @Mock
    private CustomerService customerService;

    @InjectMocks
    private CustomerController customerController;

    @Test
    public void getAllCustomers() {
        List<Customer> customers = new ArrayList<>();
        customers.add(new Customer(1L, "John", "Doe", LocalDate.of(1990, 1, 1), "johndoe@example.com", 1000.0));
        customers.add(new Customer(2L, "Jane", "Doe", LocalDate.of(1995, 2, 2), "janedoe@example.com", 2000.0));

        Mockito.when(customerService.getAllCustomers()).thenReturn(customers);

        ResponseEntity<List<Customer>> response = customerController.getAllCustomers();

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(customers, response.getBody());
    }

    @Test
    public void getCustomerById() {
        Customer customer = new Customer(1L, "John", "Doe", LocalDate.of(1990, 1, 1), "johndoe@example.com", 1000.0);

        Mockito.when(customerService.getCustomerById(1L)).thenReturn(customer);
        Mockito.when(customerService.getCustomerById(2L)).thenReturn(null);

        ResponseEntity<Customer> response1 = customerController.getCustomerById(1L);
        ResponseEntity<Customer> response2 = customerController.getCustomerById(2L);

        assertEquals(HttpStatus.OK, response1.getStatusCode());
        assertEquals(customer, response1.getBody());

        assertEquals(HttpStatus.NOT_FOUND, response2.getStatusCode());
    }

    @Test
    public void createCustomer() {
        Customer customer = new Customer(null, "John", "Doe", LocalDate.of(1990, 1, 1), "johndoe@example.com", 1000.0);
        Customer savedCustomer = new Customer(1L, "John", "Doe", LocalDate.of(1990, 1, 1), "johndoe@example.com", 1000.0);

        Mockito.when(customerService.saveCustomer(customer)).thenReturn(savedCustomer);

        ResponseEntity<Customer> response = customerController.createCustomer(customer);

        assertEquals(HttpStatus.CREATED, response.getStatusCode());
        assertEquals(savedCustomer, response.getBody());
    }

    @Test
    public void updateCustomer() {
        Customer customer = new Customer(1L, "John", "Doe", LocalDate.of(1990, 1, 1), "johndoe@example.com", 1000.0);
        Customer updatedCustomer = new Customer(1L, "Jane", "Doe", LocalDate.of(1995, 2, 2), "janedoe@example.com", 2000.0);

        Mockito.when(customerService.getCustomerById(1L)).thenReturn(customer);

        ResponseEntity<Customer> response = customerController.updateCustomer(1L, updatedCustomer);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(updatedCustomer, response.getBody());
    }

    @Test
    public void deleteCustomer() {
        ResponseEntity<Void> response = customerController.deleteCustomer(1L);

        assertEquals(HttpStatus.NO_CONTENT, response.getStatusCode());
        Mockito.verify(customerService).deleteCustomerById(1L);
    }
}
