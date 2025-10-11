package com.bibhu.Customer_API.entity;

import jakarta.persistence.*;
import lombok.Data;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import java.time.LocalDate;

@Entity
@Data
@Table(name = "orders")
public class Order {
	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "order_seq_gen")
	@SequenceGenerator(name = "order_seq_gen", sequenceName = "order_seq", allocationSize = 1)
    private Long id;
    @Column(name = "id",insertable=false, updatable=false)
    private Integer orderId;

    @Column(name = "order_tracking_num")
    private String orderTrackingNum;

    @Column(name = "razorpay_order_id")
    private String razorPayOrderId;

    @Column(name = "email")
    private String email;

    @Column(name = "order_status")
    private String orderStatus;

    @Column(name = "totalprice")
    private double totalPrice;

    @Column(name = "totalquantity")
    private int totalQuantity;

    @Column(name = "razorpay_payment_id")
    private String razorPayPaymentId;

    @ManyToOne
    @JoinColumn(name = "customer_id")
    private Customer customer;

    @ManyToOne
    @JoinColumn(name = "address_id")
    private Address address;

    @CreationTimestamp
    @Column(name="date_created", updatable = false)
    private LocalDate dateCreated;

    @UpdateTimestamp
    @Column(name="last_updated", insertable = false)
    private LocalDate lastUpdated;

    @Column(name="delivery_date", insertable = false)
    private String deliveryDate;
    
    //@ElementCollection
    //private List<OrderItem> orderItems;
}
