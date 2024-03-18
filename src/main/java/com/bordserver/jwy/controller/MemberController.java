package com.bordserver.jwy.controller;

import com.bordserver.jwy.domain.Role;
import com.bordserver.jwy.dto.MemberDTO;
import com.bordserver.jwy.dto.request.LoginRequest;
import com.bordserver.jwy.dto.request.UpdatePasswordRequest;
import com.bordserver.jwy.dto.response.LoginResponse;
import com.bordserver.jwy.service.impl.MemberServiceImpl;
import com.bordserver.jwy.utils.SessionUtil;
import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/users")
@Slf4j
public class MemberController {
    private final MemberServiceImpl memberService;
    private static final ResponseEntity<LoginResponse> FAIL_RESPONSE = new ResponseEntity<>(HttpStatus.BAD_REQUEST);
    private static LoginResponse loginResponse;

    @PostMapping("sign-up")
    @ResponseStatus(HttpStatus.CREATED)
    public void register(@RequestBody MemberDTO userDTO) {
        memberService.register(userDTO);
    }

    @PostMapping("sign-in")
    public ResponseEntity<LoginResponse> login(@RequestBody LoginRequest req, HttpSession session) {
        MemberDTO memberInfo = memberService.login(req.getAccount(), req.getPassword());
        if (memberInfo != null) {
            loginResponse = LoginResponse.success(memberInfo);
            if (memberInfo.getRole() == Role.ROLE_ADMIN) {
                SessionUtil.setLoginAdminId(session, req.getAccount());
            } else {
                SessionUtil.setLoginMemberId(session, req.getAccount());
            }
            return new ResponseEntity<LoginResponse>(loginResponse, HttpStatus.OK);
        } else {
            throw new RuntimeException("Login Error ! 유저 정보가 없거나 지워진 유저 정보입니다.");
        }
    }

    @GetMapping("my-info")
    public MemberDTO memberInfo(HttpSession session) {
        String account = SessionUtil.getLoginMemberId(session);
        if (account == null) {
            account = SessionUtil.getLoginAdminId(session);
        }
        return memberService.getMemberInfo(account).toDto();
    }

    @PutMapping("logout")
    public void logout(HttpSession session) {
        SessionUtil.clear(session);
    }

    @PatchMapping("password")
    public ResponseEntity<LoginResponse> updatePassword(@RequestBody UpdatePasswordRequest updatePasswordRequest,
                                                        HttpSession session) {
        String account = SessionUtil.getLoginMemberId(session);
        try {
            memberService.updatePassword(account, updatePasswordRequest.getBeforePassword(),
                    updatePasswordRequest.getNewPassword());
            return new ResponseEntity<LoginResponse>(loginResponse, HttpStatus.OK);
        } catch (IllegalArgumentException e) {
            log.error("updatePassword Fail");
            return FAIL_RESPONSE;
        }


    }

    @DeleteMapping
    public ResponseEntity<LoginResponse> delete() {
        return null;
    }

}
