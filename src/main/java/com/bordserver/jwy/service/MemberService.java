package com.bordserver.jwy.service;

import com.bordserver.jwy.domain.Member;
import com.bordserver.jwy.dto.MemberDTO;
import org.springframework.stereotype.Service;

@Service
public interface MemberService {
    void register(MemberDTO userDTO);

    MemberDTO login(String account, String password);

    boolean isDuplicated(String userId);

    Member getMemberInfo(String userId);

    void updatePassword(String account, String beforePassword, String afterPassword);

    void deleteId(String account, String password);
}
