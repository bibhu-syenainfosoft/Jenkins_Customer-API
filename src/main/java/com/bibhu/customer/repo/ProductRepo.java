package com.bibhu.customer.repo;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.web.bind.annotation.CrossOrigin;

import com.bibhu.customer.entity.Products;

//@CrossOrigin("http://localhost:4200/")
@CrossOrigin(origins = "*")
public interface ProductRepo extends JpaRepository<Products, Long> {
    public Products findByProductImageUrl(String url);

}
