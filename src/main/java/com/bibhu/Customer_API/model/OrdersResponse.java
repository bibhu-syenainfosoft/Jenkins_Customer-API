package com.bibhu.Customer_API.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class OrdersResponse<T> {

    public List<T> ordersList;
    public Long recCount;
}
