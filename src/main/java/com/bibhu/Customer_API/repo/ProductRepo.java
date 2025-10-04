package com.bibhu.Customer_API.repo;

import com.bibhu.Customer_API.entity.Products;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.web.bind.annotation.CrossOrigin;

//@CrossOrigin("http://localhost:4200/")
@CrossOrigin(origins = "*")
public interface ProductRepo extends JpaRepository<Products, Long> {
    public Products findByProductImageUrl(String url);

}
