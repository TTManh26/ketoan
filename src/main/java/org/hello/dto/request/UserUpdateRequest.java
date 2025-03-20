package org.hello.dto.request;

import lombok.Data;

import javax.persistence.Column;
@Data
public class UserUpdateRequest {

    @Column(name = "password")
    private String password;

    @Column(name = "email")
    private String email;
}