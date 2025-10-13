package com.bibhu.customer.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CustomerDTO {

    private String username;
    private String password;
    private String oldPassword;
    private String email;
    private String phoneNo;
    private String name;
    private String role;
}
