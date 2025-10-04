package com.bibhu.Customer_API.entity;

import jakarta.persistence.*;
import lombok.Data;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import java.math.BigDecimal;
import java.time.Instant;

//@Entity
//@Data
//@EntityListeners(AuditingEntityListener.class)
//public class Products {
//    @Id
//    @GeneratedValue(strategy=GenerationType.SEQUENCE)
//    private long productId;
//    private String productName;
//    private String productDescription;
//    private BigDecimal productPrice;
//    private String productImageUrl;
//    private String productTitle;
//    private String status;
//    private long noOfStocks;
//    @CreatedDate
//    @Column(nullable = false, updatable = false)
//    @JsonFormat(pattern="yyyy-MM-dd HH:mm:ss", timezone="Asia/Hyderabad")
//    private Instant createdDate = Instant.now();
//
//    @LastModifiedDate
//    private Instant updatedDate;
//
//    @ManyToOne
//    @JoinColumn(name = "category_Id",nullable = false)
//    private Categories categories;
//}


@Entity
@Data
public class Products {
    @Id
    @GeneratedValue(strategy=GenerationType.SEQUENCE)
    private long productId;
    private String productName;
    private String productDescription;
    private BigDecimal productPrice;
    private String productImageUrl;
    private String productTitle;
    private String status;
    private long noOfStocks;
    @Column(nullable = false, updatable = false)
    @CreationTimestamp
    private Instant createdDate = Instant.now();

    @UpdateTimestamp
    private Instant updatedDate;

    @ManyToOne
    @JoinColumn(name = "category_Id",nullable = false)
    private Categories categories;
}
