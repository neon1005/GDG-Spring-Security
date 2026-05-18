package com.gdghongik.springsecurity.domain.auth.controller;

import com.gdghongik.springsecurity.domain.auth.dto.LoginRequest;
import com.gdghongik.springsecurity.domain.auth.dto.LoginResponse;
import com.gdghongik.springsecurity.domain.member.dto.MemberCreateRequest;
import com.gdghongik.springsecurity.domain.member.service.MemberService;
import com.gdghongik.springsecurity.global.security.CustomUserDetails;
import com.gdghongik.springsecurity.global.security.JwtProvider;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.context.HttpSessionSecurityContextRepository;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/auth")
@RequiredArgsConstructor
public class AuthController {

    private final MemberService memberService;
    private final AuthenticationManager authenticationManager;
    private final JwtProvider jwtProvider;

    @PostMapping("/signup")
    public ResponseEntity<Void> signup(@RequestBody MemberCreateRequest request){
        memberService.createMember(request);
        return ResponseEntity.ok().build();
    } // 회원가입 로직

    @PostMapping("/login")
    public ResponseEntity<LoginResponse> login(@RequestBody LoginRequest request, HttpServletRequest httpRequest) {
        // AuthenticationManager가 다룰 수 있도록 요청을 토큰 객체에 담는다
        UsernamePasswordAuthenticationToken token = new UsernamePasswordAuthenticationToken(request.username(), request.password());
        // AuthenticationManager가 객체를 통해 인증시도, 실패하면 Exception
        // 매니저에 넘겨줘야함 -> 빈 선언 필요

        Authentication authentication = authenticationManager.authenticate(token);

        /* token 인증 방식 실습 위해 세션 인증 방식 주석 처리
        //현재 인증정보 저장
        SecurityContext context = SecurityContextHolder.createEmptyContext();
        context.setAuthentication(authentication);
        SecurityContextHolder.setContext(context);

        //세션 생성 JSESSIONID 발급
        HttpSession session =  httpRequest.getSession(true);

        //세션에 인증 정보 저장
        session.setAttribute(HttpSessionSecurityContextRepository.SPRING_SECURITY_CONTEXT_KEY, context);

         */

        //인증된 사용자 정보로 JWT Access Token을 발급
        CustomUserDetails userDetails = (CustomUserDetails) authentication.getPrincipal();
        String accessToken = jwtProvider.generateToken(userDetails);


        return ResponseEntity.ok(new LoginResponse(accessToken));

    } // 로그인 로직

    @PostMapping("/logout")
    public ResponseEntity<Void> logout(HttpServletRequest httpRequest) {
        /* token 인증 방식 실습 위해 세션 인증 방식 주석 처리
        HttpSession session = httpRequest.getSession(false);
        if (session != null) {
            session.invalidate();
        }
        SecurityContextHolder.clearContext();

        */

        return ResponseEntity.ok().build();
    }

}
