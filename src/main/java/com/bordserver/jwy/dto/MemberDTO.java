package com.bordserver.jwy.dto;


import com.bordserver.jwy.domain.Role;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class MemberDTO {
    private String account;
    private String password;
    private String nickname;
    private Role role;
    private boolean isWithDraw;
}
