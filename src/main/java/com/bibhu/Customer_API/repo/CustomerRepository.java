package com.bibhu.Customer_API.repo;

import com.bibhu.Customer_API.entity.Customer;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface CustomerRepository extends JpaRepository<Customer, Long> {

   public boolean existsByEmailAndPasswordAndRoleNot(String email, String password, String role);
   public Customer findByEmail(String email);
   public Optional<Customer> findById(Long customerId);

//   @Query("select c.password from Customer c where c.email = :email")
//   public String getPasswordThroughEmail(String email);
}

