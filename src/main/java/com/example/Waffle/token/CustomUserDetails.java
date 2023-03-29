package com.example.Waffle.token;

import com.example.Waffle.entity.UserEntity;
import lombok.Getter;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;
import java.util.Collections;

@Getter
public class CustomUserDetails implements UserDetails {

    private UserEntity userEntity;

    public CustomUserDetails(UserEntity userEntity){
        this.userEntity = userEntity;
    }


    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return Collections.emptyList();
    }

    @Override
    public String getPassword() {
        return userEntity.getPassword();
    }

    @Override
    public String getUsername() {
        return userEntity.getName();
    }

    /**
     * 계정 만료 여부
     * true : 만료 안됨
     * false : 만료
     */
    @Override
    public boolean isAccountNonExpired() {
        return false;
    }

    /**
     * 계정 잠김 여부
     * true : 잠기지 않음
     * false : 잠김
     */
    @Override
    public boolean isAccountNonLocked() {
        return false;
    }

    /**
     * 비밀번호 만료 여부
     * true : 만료 안됨
     * false : 만료
     */
    @Override
    public boolean isCredentialsNonExpired() {
        return false;
    }

    /**
     * 사용자 활성화 여부
     * ture : 활성화
     * false : 비활성화
     */
    @Override
    public boolean isEnabled() {
        return false;
    }


}

