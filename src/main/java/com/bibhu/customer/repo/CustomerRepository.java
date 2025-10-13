package com.bibhu.customer.repo;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.bibhu.customer.entity.Customer;

import java.util.Optional;

@Repository
public interface CustomerRepository extends JpaRepository<Customer, Long> {

   public boolean existsByEmailAndPasswordAndRoleNot(String email, String password, String role);
   public Customer findByEmail(String email);
   public Optional<Customer> findById(Long customerId);
}

