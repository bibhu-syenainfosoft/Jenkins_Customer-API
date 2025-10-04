package com.bibhu.Customer_API.repo;

import com.bibhu.Customer_API.entity.OrderItem;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface OrderItemRepository extends JpaRepository<OrderItem, Long> {
   public List<OrderItem> findByOrder_OrderId(Long orderId);
}
