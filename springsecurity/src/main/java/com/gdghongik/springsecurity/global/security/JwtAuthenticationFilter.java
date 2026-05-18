package com.gdghongik.springsecurity.global.security;

import com.gdghongik.springsecurity.domain.member.entity.MemberRole;
import io.jsonwebtoken.Claims;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.AbstractAuthenticationProcessingFilter;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.sql.rowset.serial.SerialException;
import java.io.IOException;

@RequiredArgsConstructor
//필터 형식을 위한 클래스 상속
public class JwtAuthenticationFilter extends OncePerRequestFilter {

    private final JwtProvider jwtProvider;


    //filter로 들어온 요청을 통해서 어떤 동작을 할 것이냐 정의
    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
            throws ServletException, IOException {

        String token = resolveToken(request);
        if (token != null && jwtProvider.isValid(token)) {
            Claims claims = jwtProvider.getClaims(token);

            long memberId = Long.parseLong(claims.getSubject());
            String username = claims.get("username", String.class);
            MemberRole role = MemberRole.valueOf(claims.get("role", String.class));

            CustomUserDetails userDetails = new CustomUserDetails(memberId, username, null, role);
            //password를 넘어가지 않게 null로
            
            //세션 인증 방식과 비슷
            UsernamePasswordAuthenticationToken authentication = new UsernamePasswordAuthenticationToken(userDetails, null, userDetails.getAuthorities());

            SecurityContextHolder.getContext().setAuthentication(authentication);
        }
        filterChain.doFilter(request, response);

    }

    private String resolveToken(HttpServletRequest request) {
        String bearer = request.getHeader("Authorization");

        if (bearer != null && bearer.startsWith("Bearer ")) {
            return bearer.substring(7); //7개문자 이후부터 헤더 안 토큰값
        }
        return null;
    }


}
