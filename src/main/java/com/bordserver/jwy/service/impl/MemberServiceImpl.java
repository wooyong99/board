package com.bordserver.jwy.service.impl;

import com.bordserver.jwy.dao.MemberDao;
import com.bordserver.jwy.domain.Member;
import com.bordserver.jwy.dto.MemberDTO;
import com.bordserver.jwy.exception.DuplicateEmailException;
import com.bordserver.jwy.exception.LoginFailException;
import com.bordserver.jwy.exception.NotFoundMemberException;
import com.bordserver.jwy.service.MemberService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
@Slf4j
public class MemberServiceImpl implements MemberService, UserDetailsService {
    private final MemberDao dao;
    private final PasswordEncoder passwordEncoder;
    private final AuthenticationManagerBuilder authenticationManagerBuilder;

    public Member dtoToEntity(MemberDTO dto) {
        return Member.builder()
                .nickname(dto.getNickname())
                .account(dto.getAccount())
                .password(passwordEncoder.encode(dto.getPassword()))
                .role(dto.getRole())
                .isWithDraw(dto.isWithDraw())
                .build();
    }

    // 클라이언트가 입력한 username값을 이용해 Dao 객체에서 Load하여
    // UserDetails로 반환한다. -> xxxProvider 객체에게 돌려주게된다.
    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        Member member = dao.findByAccount(username)
                .orElseThrow(() -> new UsernameNotFoundException(String.format("%s은(는) 없는 계정입니다. 다시 확인해주세요.", username))
                );
        return new CustomUserDetails(member);
    }

    @Override
    public void register(MemberDTO memberDTO) {
        isDuplicated(memberDTO.getAccount());
        dao.save(dtoToEntity(memberDTO));
    }

    @Override
    public MemberDTO login(String account, String password) {
        UsernamePasswordAuthenticationToken authenticationToken =
                new UsernamePasswordAuthenticationToken(account, password);
        Authentication authentication;
        try {
            authentication = authenticationManagerBuilder.getObject()
                    .authenticate(authenticationToken);
        } catch (BadCredentialsException e) {
            log.error("존재하지 않는 유저입니다.");
            throw new LoginFailException("존재하지 않는 유저입니다");
        }
        SecurityContextHolder.getContext().setAuthentication(authentication);
        Member member = ((CustomUserDetails) authentication.getPrincipal()).getMember();
        return member.toDto();
    }

    @Override
    public boolean isDuplicated(String account) {
        if (dao.existsByAccount(account)) {
            log.error("중복된 아이디입니다.");
            throw new DuplicateEmailException("중복된 아이디입니다.");
        }
        ;
        return false;
    }

    @Override
    public Member getMemberInfo(String account) {
        return dao.findByAccount(account).orElseThrow(
                () -> new NotFoundMemberException(String.format("%s 의 계정을 가진 사용자는 존재하지 않습니다.", account))
        );
    }

    @Override
    public void updatePassword(String account, String beforePassword, String afterPassword) {
        Member member = dao.findByAccount(account)
                .orElseThrow(() -> new NotFoundMemberException(String.format("%s 의 계정을 가진 사용자는 존재하지 않습니다.", account)));
        if (passwordEncoder.matches(beforePassword, member.getPassword())) {
            member.setPassword(passwordEncoder.encode(afterPassword));
        } else {
            throw new IllegalArgumentException("이전 비밀번호를 다시 입력해주세요.");
        }
        dao.save(member);
    }

    @Override
    public void deleteId(String account, String password) {

    }
}
