package com.bibhu.Customer_API.controller;

import com.bibhu.Customer_API.entity.*;
import com.bibhu.Customer_API.model.CustomerDTO;
import com.bibhu.Customer_API.model.OrdersList;
import com.bibhu.Customer_API.model.OrdersResponse;
import com.bibhu.Customer_API.service.CustomerService;
import com.bibhu.Customer_API.service.EmailTemplateService;
import jakarta.mail.MessagingException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@CrossOrigin(origins = "*")
@RequestMapping("/ecomm/customer")
public class CustomerController {

    private CustomerService customerService;
    private EmailTemplateService emailTempService;

    public CustomerController(CustomerService service, EmailTemplateService emailTempService) {
        this.customerService = service;
        this.emailTempService = emailTempService;
    }

    @GetMapping("/check")
    public ResponseEntity<String> check() {
        return new ResponseEntity<>("Customer-API2 is Working...", HttpStatus.OK);
    }

    @PostMapping("/login")
    public ResponseEntity<Boolean> login(@RequestBody CustomerDTO customer) {
        boolean loginFlag = customerService.existsByEmailAndPassword(customer);
        return new ResponseEntity<Boolean>(loginFlag, HttpStatus.OK);
    }

    @PostMapping("/register")
    public ResponseEntity<Map<String, String>> register(@RequestBody CustomerDTO customer) throws MessagingException {
        String tempPwd = customerService.generateRandomPwd();
        String html = emailTempService.buildTempPwdHtml(customer.getEmail(), tempPwd);
        return new ResponseEntity<Map<String, String>>(customerService.register(customer, html, tempPwd), HttpStatus.OK);
    }

    @GetMapping("/generatePassword")
    public ResponseEntity<Map<String, Boolean>> generatePassword(@RequestParam String email) throws MessagingException {
        String tempPwd = customerService.generateRandomPwd();
        boolean customerExistingFlag = customerService.generateOrUpdatePassword(email, tempPwd);
        if (customerExistingFlag) {
            String html = emailTempService.buildTempPwdHtml(email, tempPwd);
            customerService.sendRandomPwdToMail(email, html);
        }
        Map<String, Boolean> map = new HashMap();
        map.put("SuccessFlag", customerExistingFlag);
        return new ResponseEntity<Map<String, Boolean>>(map, HttpStatus.OK);
    }

    @PostMapping("/resetPassword")
    public ResponseEntity<Map<String, Object>> resetPassword(@RequestBody CustomerDTO customer) {
        String existingTempPwd = customerService.getPasswordFromEmail(customer.getEmail());
        boolean successFlag = false;
        if (existingTempPwd != null && existingTempPwd.equals(customer.getOldPassword())) {
            successFlag = customerService.generateOrUpdatePassword(customer.getEmail(), customer.getPassword());
        }
        Map<String, Object> map = new HashMap();
        map.put("oldPasswordFlag", successFlag);
        map.put("Status", successFlag ? "Your password has been reset successfully" : "Old Password does not match with System generated Password");
        return new ResponseEntity<Map<String, Object>>(map, HttpStatus.OK);
    }

    @GetMapping("/getOrdersByCustomer")
    public ResponseEntity<Map<String, Object>> getOrdersByCustomer(@RequestParam String email, @RequestParam Long lowerBound, @RequestParam Long upperBound) {

    	OrdersResponse<OrdersList> listOfOrders = customerService.getAllOrdersByCustomer(email,lowerBound,upperBound);
        String custName = customerService.getCustomerNameByEmail(email);

        Map<String, Object> map = new HashMap();
        map.put("ordersList", listOfOrders.ordersList);
        map.put("count", listOfOrders.recCount);
        map.put("customerName", custName);
        return new ResponseEntity<Map<String, Object>>(map, HttpStatus.OK);
    }

    @GetMapping("/getOrderDetails")
    public ResponseEntity<Map<String, Object>> getOrderDetails(@RequestParam Long orderId) {

        List<OrderItem> orderItemsList = customerService.getOrderItemsList(orderId);
        Order order = customerService.getOrderDetails(orderId);
        Address address = customerService.getAddressDetails(order.getAddress().getId());
        Customer customer = customerService.getCustomerDetais(order.getCustomer().getId());

        Map<String, Object> map = new HashMap();
        map.put("orderItems", orderItemsList);
        map.put("deliveryDate", order.getDeliveryDate());
        map.put("addressDetails", address);
        map.put("customerDetails", customer);
        return new ResponseEntity<Map<String, Object>>(map, HttpStatus.OK);
    }

    @GetMapping("/getProductId")
    public ResponseEntity<Long> getProductId(@RequestParam String url) {
        Products products = customerService.getProductId(url);
        return new ResponseEntity<Long>(products.getProductId(), HttpStatus.OK);
    }

}