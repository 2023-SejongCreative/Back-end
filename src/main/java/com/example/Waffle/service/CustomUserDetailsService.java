package com.example.Waffle.service;

import com.example.Waffle.entity.UserEntity;
import com.example.Waffle.repository.UserRepository;
import com.example.Waffle.token.CustomUserDetails;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Service
public class CustomUserDetailsService implements UserDetailsService {

    private final UserRepository userRepository;

    @Override
    public CustomUserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        UserEntity userEntity = userRepository.findByemail(email)
                .orElseThrow(() -> new UsernameNotFoundException("사용자를 찾을 수 없습니다."));

        CustomUserDetails customUserDetails = new CustomUserDetails(userEntity);

        return customUserDetails;

    }

}
