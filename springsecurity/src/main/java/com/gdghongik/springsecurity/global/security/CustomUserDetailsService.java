package com.gdghongik.springsecurity.global.security;

import com.gdghongik.springsecurity.domain.member.entity.Member;
import com.gdghongik.springsecurity.domain.member.repository.MemberRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class CustomUserDetailsService implements UserDetailsService {
    private final MemberRepository memberRepository;// DB에 맴버가 있다면 인증

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        Member member = memberRepository.findByUsername(username);
        if (member == null) {
            throw new UsernameNotFoundException(username);
        }

        //CustomUserDetails가 UserDetails 구현 -> UserDetails 반환
        return CustomUserDetails.from(member);
    }
}
