package com.bordserver.jwy.config;


import com.bordserver.jwy.dao.MemberDao;
import com.bordserver.jwy.domain.Member;
import com.bordserver.jwy.exception.LoginFailException;
import com.bordserver.jwy.service.impl.CustomUserDetails;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.password.PasswordEncoder;

public class SessionProvider {
    private final MemberDao memberDao;
    private final PasswordEncoder passwordEncoder;

    public SessionProvider(MemberDao memberDao, PasswordEncoder passwordEncoder) {
        this.memberDao = memberDao;
        this.passwordEncoder = passwordEncoder;
    }

    public Authentication getAuthentication(String account) {
        Member member = memberDao.findByAccount(account).orElseThrow(() -> new LoginFailException("존재하지 않는 유저입니다."));
        CustomUserDetails userDetails = new CustomUserDetails(member);
        return new UsernamePasswordAuthenticationToken(userDetails, "", userDetails.getAuthorities());
    }
}
