package com.bordserver.jwy.dto.request;

import lombok.Getter;
import lombok.NonNull;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
public class LoginRequest {
    @NonNull
    private String account;
    @NonNull
    private String password;
}
