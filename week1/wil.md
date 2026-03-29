week1 정리  
인증(Authentication) -> 로그인 -> Security가 신분증 확인하고 출입증 발급
인가(Authorization) -> 리소스 접근 제어 -> Security가 출입증을 확인  

Spring 환경 인증/인가 담당 -> Spring Security(건물 보안 시스템)  

서블릿 필터(Servlet Filter): 요청/응답을 가로채서 전처리/후처리 하는 장치(ex. 요청 로깅, 인코딩 설정)  

인증 흐름:  
1. 사용자가 ID+PW 제출  
2. 인증 필터  
3. AuthenticationManager  
4. UserDetailsService  
이후 인가


Record:  
기본적으로 필드값이 바뀌지 않는 불변 객체  
메서드나 생성자를 자동으로 만들어줘서 간결하고 효율적인 데이터 객체를 만들 수 있다.  
DTO(Data Transfer Object)를 만드는데 특화되어있는 클래스 타입  
  
Record의 장점:  
가독성, 불변성, 패턴 매칭 통합, DTO 명시  

