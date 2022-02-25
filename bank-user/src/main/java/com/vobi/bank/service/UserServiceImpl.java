package com.vobi.bank.service;

import java.util.List;
import java.util.Optional;
import java.util.Set;

import javax.validation.ConstraintViolation;
import javax.validation.ConstraintViolationException;
import javax.validation.Validator;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.vobi.bank.domain.Users;
import com.vobi.bank.repository.UserRepository;

@Service
public class UserServiceImpl implements UserService{

	@Autowired
	UserRepository customerRepository;
	
	@Autowired
	Validator validator;
	
	@Override
	@Transactional(readOnly = true)
	public List<Users> findAll() {
		// TODO Auto-generated method stub
		return customerRepository.findAll();
	}

	@Override
	@Transactional(readOnly = true)
	public Optional<Users> findById(String email) {
		// TODO Auto-generated method stub
		return customerRepository.findById(email);
	}

	@Override
	@Transactional(readOnly = false,propagation = Propagation.REQUIRED,rollbackFor = Exception.class)
	public Users save(Users entity) throws Exception {
		// TODO Auto-generated method stub
		if(entity==null) {
			throw new Exception("El user es nulo");
		}
		
		validate(entity);
		
		if(customerRepository.existsById(entity.getUserEmail())) {
			throw new Exception("El user ya existe");
		}
		
		return customerRepository.save(entity);
	}

	@Override
	@Transactional(readOnly = false,propagation = Propagation.REQUIRED,rollbackFor = Exception.class)
	public Users update(Users entity) throws Exception {
		// TODO Auto-generated method stub
		if(entity==null) {
			throw new Exception("El user es nulo");
		}
		
		validate(entity);
		
		if(customerRepository.existsById(entity.getUserEmail())==false) {
			throw new Exception("El user no existe");
		}
		
		return customerRepository.save(entity);
	}

	@Override
	@Transactional(readOnly = false,propagation = Propagation.REQUIRED,rollbackFor = Exception.class)
	public void delete(Users entity) throws Exception {
		// TODO Auto-generated method stub
		if(entity==null) {
			throw new Exception("El customer es nulo");
		}
		
		if(entity.getUserEmail()==null) {
			throw new Exception("El user id es nulo");
		}
		
		if(customerRepository.existsById(entity.getUserEmail())==false) {
			throw new Exception("El user no existe");
		}
		
		/*
		findById(entity.getUserEmail()).ifPresent(customer->{
			if(customer.getAccounts()!=null && customer.getAccounts().isEmpty()==false) {
				throw new RuntimeException("El customer tiene cuentas asociadas");
			}
			
			if(customer.getRegisteredAccounts()!=null && customer.getRegisteredAccounts().isEmpty()==false) {
				throw new RuntimeException("El customer tiene cuentas registradas asociadas");
			}		
		});
		*/
		
		customerRepository.deleteById(entity.getUserEmail());

	}

	@Override
	@Transactional(readOnly = false,propagation = Propagation.REQUIRED,rollbackFor = Exception.class)
	public void deleteById(String id) throws Exception {
		// TODO Auto-generated method stub
		if(id==null)
			throw new Exception("El email es nulo");
		
		if(customerRepository.existsById(id)==false) {
			throw new Exception("El user no existe");
		}
		
		delete(customerRepository.findById(id).get());
	}

	@Override
	public void validate(Users entity) throws Exception {
		// TODO Auto-generated method stub
		Set<ConstraintViolation<Users>> constraintViolations=validator.validate(entity);
		if(constraintViolations.isEmpty()==false) {
			throw new ConstraintViolationException(constraintViolations);
		}	
	}

	@Override
	@Transactional(readOnly = true)
	public Long count() {
		// TODO Auto-generated method stub
		return customerRepository.count();
	}

}
