package com.bordserver.jwy.dto.response;

import com.bordserver.jwy.dto.MemberDTO;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;

@Getter
@AllArgsConstructor
@RequiredArgsConstructor
public class LoginResponse {
    enum LoginStatus {
        SUCCESS, FAIL, DELETED
    }

    @NonNull
    private LoginStatus result;
    private MemberDTO memberDTO;
    private static final LoginResponse FAIL = new LoginResponse(LoginStatus.FAIL);

    public static LoginResponse success(MemberDTO memberDTO) {
        return new LoginResponse(LoginStatus.SUCCESS, memberDTO);
    }
}
