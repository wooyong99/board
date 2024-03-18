package com.bordserver.jwy.domain;

import com.bordserver.jwy.dto.MemberDTO;
import com.bordserver.jwy.global.BaseEntity;
import jakarta.persistence.Convert;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
@ToString(of = {"id", "account", "password", "nickname", "role", "isWithDraw"})
public class Member extends BaseEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String account;
    private String password;
    private String nickname;
    private boolean isWithDraw;
    @Convert(converter = RoleConverter.class)
    private Role role;

    public MemberDTO toDto() {
        return MemberDTO.builder()
                .account(this.account)
                .nickname(this.nickname)
                .role(this.getRole())
                .isWithDraw(this.isWithDraw)
                .build();
    }
}
