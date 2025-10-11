package com.bibhu.Customer_API.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.SequenceGenerator;
import lombok.Data;

@Entity
@Data
public class Address {
	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "address_seq_gen")
	@SequenceGenerator(name = "address_seq_gen", sequenceName = "address_seq", allocationSize = 1)
	private Long id;
    private String street;
    private String city;
    private String state;
    private String zipCode;
    @ManyToOne
    private Customer customer;
}
