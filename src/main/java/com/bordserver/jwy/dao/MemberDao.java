package com.bordserver.jwy.dao;

import com.bordserver.jwy.domain.Member;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;

public interface MemberDao extends JpaRepository<Member, Long> {
    boolean existsByAccount(String account);

    Optional<Member> findByAccountAndPassword(String account, String password);

    Optional<Member> findByAccount(String account);
}
