package com.bibhu.customer.service;

import java.security.SecureRandom;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import org.springframework.data.domain.PageRequest;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;

import com.bibhu.customer.entity.Address;
import com.bibhu.customer.entity.Customer;
import com.bibhu.customer.entity.Order;
import com.bibhu.customer.entity.OrderItem;
import com.bibhu.customer.entity.Products;
import com.bibhu.customer.model.CustomerDTO;
import com.bibhu.customer.model.OrdersList;
import com.bibhu.customer.model.OrdersResponse;
import com.bibhu.customer.repo.AddressRepository;
import com.bibhu.customer.repo.CustomerRepository;
import com.bibhu.customer.repo.OrderItemRepository;
import com.bibhu.customer.repo.OrderRepository;
import com.bibhu.customer.repo.ProductRepo;

import jakarta.mail.MessagingException;
import jakarta.mail.internet.MimeMessage;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class CustomerServiceImpl implements CustomerService {

	
	private final CustomerRepository customerRepo; // Field injection
	
	private final OrderRepository orderRepo;

	private final OrderItemRepository orderItemRepo;

	private final AddressRepository addressRepo;
	
	private final ProductRepo prodRepo;

	public static boolean ableToSendTempPwdFlag = false;
	
	private final JavaMailSender mailSender;

	@Override
	public boolean existsByEmailAndPassword(CustomerDTO customer) {
		return customerRepo.existsByEmailAndPasswordAndRoleNot(customer.getUsername(), customer.getPassword(), "ADMIN");
	}

	@Override
	public Map<String, String> register(CustomerDTO customer, String html, String tempPwd) throws MessagingException {
		boolean existsFlag = false;
		String msg = "";
		String tempPwdMsg = "";
		Customer customer1 = customerRepo.findByEmail(customer.getEmail());
		Customer customer2 = new Customer();
		if (customer1 == null) {
			customer2.setName(customer.getName());
			customer2.setEmail(customer.getEmail());
			customer2.setPhno(customer.getPhoneNo());
			customer2.setRole(customer.getRole());
			customer2.setPassword(tempPwd);
			this.customerRepo.save(customer2);
			if (customer.getRole() == "CUSTOMER") {
				tempPwdMsg = sendRandomPwdToMail(customer.getEmail(), html);
			}
			existsFlag = false;
			msg = "Account Created Successfully with Email Id: " + customer.getEmail();
		} else {
			existsFlag = true;
		}
		Map<String, String> map = new HashMap<>();
		map.put("CustomerExistsFlag", String.valueOf(existsFlag));
		map.put("Status", msg);
		map.put("tempPwdSentStatus", tempPwdMsg);
		return map;
	}

	public String sendRandomPwdToMail(String email, String html) throws MessagingException {
		String to = email;
		String subject = "Temporary Password from Ecomm@Bibhu";
		String body = html;

		MimeMessage message = mailSender.createMimeMessage();
		MimeMessageHelper helper = new MimeMessageHelper(message, true);
		helper.setTo(to);
		helper.setSubject(subject);
		helper.setText(body, true);
		mailSender.send(message);
		this.ableToSendTempPwdFlag = false;
		return "Check your mail, We have already sent a system generated temporary password";
	}

	@Override
	public String getPasswordFromEmail(String email) {
		Customer customer = customerRepo.findByEmail(email);
		return customer.getPassword();
	}

	public String generateRandomPwd() {
		
		String upper = "ABCDEFGHIJKLMNOPQRSTUVWXYZ";
		String lower = "abcdefghijklmnopqrstuvwxyz";
		String digits = "0123456789";
		String special = "!@#$%^&*(),.?\":{}|<>";

		String allChars = upper + lower + digits + special;

		SecureRandom random = new SecureRandom();
		List<Character> password = new ArrayList<>();
		password.add(upper.charAt(random.nextInt(upper.length()))); // One uppercase
		password.add(lower.charAt(random.nextInt(lower.length()))); // One lowercase
		password.add(digits.charAt(random.nextInt(digits.length()))); // One digit
		password.add(special.charAt(random.nextInt(special.length()))); // One special char
		int remainingLength = 8 + random.nextInt(8) - 4; // (8 to 15 total)
		for (int i = 0; i < remainingLength; i++) {
			password.add(allChars.charAt(random.nextInt(allChars.length())));
		}
		Collections.shuffle(password, random);

		StringBuilder result = new StringBuilder();
		for (char ch : password) {
			result.append(ch);
		}
		return result.toString();
	}

	@Override
	public boolean generateOrUpdatePassword(String email, String tempPwd) {
		Optional<Customer> optCust = Optional.ofNullable(customerRepo.findByEmail(email));
		if (optCust.isPresent()) {
			Customer customerObj = optCust.get();
			customerObj.setPassword(tempPwd);
			customerRepo.save(customerObj);
			return true;
		}
		return false;
	}

	@Override
	public OrdersResponse<OrdersList> getAllOrdersByCustomer(String email, Long lowerBound, Long upperBound) {
		int pageSize = Math.toIntExact(upperBound - lowerBound + 1);
		int pageNumber = Math.toIntExact(lowerBound / pageSize);

		PageRequest pageRequest = PageRequest.of(pageNumber, pageSize);
		org.springframework.data.domain.Page<Order> ordersList = orderRepo.findByEmail(email, pageRequest);

		List<OrdersList> listOfOrders = ordersList.getContent().stream().map(order -> {
			OrdersList oList = new OrdersList();
			oList.setOrderId(order.getOrderId());
			oList.setOrderTrackingNum(order.getOrderTrackingNum());
			oList.setRazorPayOrderId(order.getRazorPayOrderId());
			oList.setRazorPayPaymentId(order.getRazorPayPaymentId());
			oList.setEmail(order.getEmail());
			oList.setOrderStatus(order.getOrderStatus());
			oList.setTotalPrice(order.getTotalPrice());
			oList.setTotalQuantity(order.getTotalQuantity());
			oList.setTotalQuantity(order.getTotalQuantity());
			oList.setDateCreated(order.getDateCreated());
			oList.setDateCreated(order.getDateCreated());
			oList.setLastUpdated(order.getLastUpdated());
			oList.setDeliveryDate(order.getDeliveryDate());
			return oList;
		}).toList();

		return new OrdersResponse<>(listOfOrders, ordersList.getTotalElements());
	}

	@Override
	public String getCustomerNameByEmail(String email) {
		Customer customer = customerRepo.findByEmail(email);
		return customer.getName();
	}

	@Override
	public List<OrderItem> getOrderItemsList(Long orderId) {
		return orderItemRepo.findByOrder_OrderId(orderId);
	}

	@Override
	public Order getOrderDetails(Long orderId) {
		Optional<Order> order = orderRepo.findById(orderId);
		if (order.isPresent()) {
			return order.get();
		}
		return null;
	}

	@Override
	public Address getAddressDetails(Long addressId) {
		Optional<Address> address = addressRepo.findById(addressId);
		if (address.isPresent()) {
			return address.get();
		}
		return null;
	}

	@Override
	public Customer getCustomerDetais(Long customerId) {
		Optional<Customer> customer = customerRepo.findById(customerId);
		if (customer.isPresent()) {
			return customer.get();
		}
		return null;
	}

	@Override
	public Products getProductId(String url) {
		return prodRepo.findByProductImageUrl(url);
	}

}
