모든 시큐리티 설정 ->SecurityFilterChain 빈을 등록하는 것부터 시작  
세션 기반 인증 방식이 JSESSIONID 아이디를 통해 어떻게 동작하는가? : 세션ID가 JSESSIONID   
사용자(로그인 요청) -인증-> 스프링 시큐리티(세션ID 발급) -세션ID-> 사용자(세션ID 소지)   
스프링 시큐리티(매핑 기록) -저장-> 세션 저장소(세션ID->인가 정보)  
사용자(세션ID 제시) -조회-> 인가 영역(세션ID 확인 요청) -조회-> 세션 저장소(인가 여부 확인) ->결과 반환  
  
Session 기반 인증: Stateful (서버가 사용자의 상태를 저장)  
  
로그인 구현  
1. 로그인 요청을 authToken으로 변환  
2. AuthenticationManager로 인증  
   -> 1번의 객체를 통해 인증 시도  
   -> 실패 시 AuthenticationException  
   -> 성공 시 Authentication 객체 반  
4. 현재 인증 정보를 저장(SecurityContext가 담당)  
5. 세션을 생성하고 JSESSIONID를 발급  
6. 세션에 인증 정보를 저장
  
+) MemberRole 의 value 로 ROLE_ 접두사를 붙이는가  
스프링 시큐리티가 역할 권한으로 인식하기 쉬운 표준 형태로   
ROLE_ 은 스프링 시큐리티에게 맞춰서 표현하는 규칙이다.  
