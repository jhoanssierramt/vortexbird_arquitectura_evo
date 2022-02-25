package com.vobi.bank.repository;

import static org.junit.jupiter.api.Assertions.*;

import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.MethodOrderer.OrderAnnotation;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import com.vobi.bank.domain.Customer;
import com.vobi.bank.domain.DocumentType;

import lombok.extern.slf4j.Slf4j;

@SpringBootTest
@TestMethodOrder(OrderAnnotation.class)
@Slf4j
class CustomerRepositoryIT {

	@Autowired
	CustomerRepository customerRepository;
	
	@Autowired
	DocumentTypeRepository documentTypeRepository;
	
	
	@Test
	@Order(1)
	void debeValidarLasDependencias() {
		assertNotNull(customerRepository);
		assertNotNull(documentTypeRepository);
	}
	
	@Test
	@Order(2)
	void debeCrearCustomer() {
		//La prueba debe ser triple "A":
		
		//Arrange
		Integer idDocumentType=1;
		Integer idCustomer=14836554;
		
		Customer customer =null;
		DocumentType documentType=documentTypeRepository.findById(idDocumentType).get();
		
		customer = new Customer();
		customer.setAddress("Avenida siempre viva 742");
		customer.setCustId(idCustomer);
		customer.setDocumentType(documentType);
		customer.setEmail("hjsimpson@gmail.com");
		customer.setEnable("Y");
		customer.setName("Homero J Simpson");
		customer.setPhone("5550034");
		customer.setToken("adhkashdjhaskhkashd");
		
		//Act
		customer = customerRepository.save(customer);	
		
		//Assert
		assertNotNull(customer, "El customer es nulo no se pudo grabar");
	}
	
	@Test
	@Order(3)
	void debeModificarCustomer() {
		
		//Arrange
		Integer idCustomer=14836554;		
		Customer customer =null;
		
		customer = customerRepository.findById(idCustomer).get();
		//Solo modificamos el estado:
		customer.setEnable("N");
		
		//Act
		customer = customerRepository.save(customer);	
		
		//Assert
		assertNotNull(customer, "El customer es nulo no se pudo modificar");
	}
	
	@Test
	@Order(4)
	void debeBorrarUnCustomer() {
		//Arrange
		
		Integer idCustomer=14836554;
		Customer customer=null;
		Optional<Customer> customerOptional=null;
		
		assertTrue(customerRepository.findById(idCustomer).isPresent(),"No encontro el customer");
		
		customer=customerRepository.findById(idCustomer).get();
		
		//Act
		customerRepository.delete(customer);
		customerOptional=customerRepository.findById(idCustomer);
		
		//Assert
		
		assertFalse(customerOptional.isPresent(),"No pudo borrar el customer");
	}
	
	
	@Test
	@Order(5)
	void debeConsultarTodosLosCustomers() {
		//Arrange
		List<Customer> customers=null;
		
		//Act
		
		customers=customerRepository.findAll();
		
		customers.forEach(customer->log.info(customer.getName()));		
		
		//Assert
		
		assertFalse(customers.isEmpty(),"No consulto Customers");	
	}
	

}
