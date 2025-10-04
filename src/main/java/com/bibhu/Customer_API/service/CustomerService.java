package com.bibhu.Customer_API.service;

import com.bibhu.Customer_API.entity.*;
import com.bibhu.Customer_API.model.CustomerDTO;
import com.bibhu.Customer_API.model.OrdersList;
import com.bibhu.Customer_API.model.OrdersResponse;

import jakarta.mail.MessagingException;

import java.util.List;
import java.util.Map;

public interface CustomerService {

    public boolean existsByEmailAndPassword(CustomerDTO customer);
    public Map<String,String> register(CustomerDTO customer, String html, String tempPwd) throws MessagingException;
    public String generateRandomPwd();
    public boolean generateOrUpdatePassword(String email,String tempPwd);
    public String sendRandomPwdToMail(String email, String tempPwd) throws MessagingException;
    public String getPasswordFromEmail(String email);
    public OrdersResponse<OrdersList> getAllOrdersByCustomer(String email, Long lowerBound, Long upperBound);
    public String getCustomerNameByEmail(String email);
    public List<OrderItem> getOrderItemsList(Long orderId);
    public Order getOrderDetails(Long orderId);
    public Address getAddressDetails(Long addressId);
    public Customer getCustomerDetais(Long customerId);
    public Products getProductId(String url);
}
