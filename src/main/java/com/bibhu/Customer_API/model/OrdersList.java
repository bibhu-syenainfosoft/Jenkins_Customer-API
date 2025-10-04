package com.bibhu.Customer_API.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class OrdersList {
    private Integer orderId;
    private String orderTrackingNum;
    private String razorPayOrderId;
    private String email;
    private String orderStatus;
    private double totalPrice;
    private int totalQuantity;
    private String razorPayPaymentId;
    private LocalDate dateCreated;
    private LocalDate lastUpdated;
    private String deliveryDate;
}
