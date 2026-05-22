**스프링 부트 실행 flow:**  
SpringsecurityApplication.run()을 따라가보자.  
↓   
WebServerApplicationContext 준비  
↓   
prepareContext(): 초기 설정   
↓  
refreshContext():  
refreshContext() 내부에서 ServletWebServerApplicationContext 의 refresh() 동작.  
refresh() 는 onRefresh() 를 호출을 하고 onRefresh 에서 내장 Tomcat 객체를 생성한다.  
그리고 Bean 생성, 의존성 주입  
↓  
finishRefresh()   
↓  
tomcat 웹 서버가 start 한다.    

<br>
  
**SecurityFilterChain 생성 flow:**  
HttpSecurity는 Spring Security가 제공하는 builder 객체로  
개발자가 작성한 보안 설정들을 Configurer 형태로 저장한다.  
  
http.build()가 호출되면 저장된 설정들이 초기화되고 구성되면서 SecurityFilterChain이 생성된다.  
  
SecurityFilterChain 은 크게 두 가지 정보를 가진다.  
  
1. 어떤 요청에 적용될 것인지 판단하는 RequestMatcher  
2. 해당 요청에 적용할 Security Filter 목록  

<br>

**FilterChainProxy 생성 flow:**     
SecurityFilterChain은 실제 서블릿 컨테이너에 직접 등록되는 Filter가 아니다.  
   
여러 개의 SecurityFilterChain을 가지고 있다가  
현재 들어온 요청에 맞는 체인을 선택하고   
그 안의 Security Filter들을 실행하는  
핵심 Filter( Spring Security 전체를 대표 )의 구현체가 FilterChainProxy이다.  
  
securityFilterChains (등록한 필터 체인들을 담은 리스트 형태) 를  
FilterChainProxy 의 생성자 인수로 전달받아  
Spring Security 전체 요청 처리를 담당하는 FilterChainProxy가 생성된다.  
 
<br>
  
**DelegatingFilterProxy 등록 및 생성 flow:**  
  
서블릿 컨테이너는 자신의 표준 방식으로 등록된 Filter는 알고 있지만,  
Spring ApplicationContext 안에 존재하는 Bean을 직접 알지 못한다.  
  
이 문제를 해결하기 위해 Spring이 제공하는 Filter가 DelegatingFilterProxy이다.  
이 객체는 서블릿 컨테이너가 받은 요청을 Spring Bean으로 위임하는 연결 다리 역할을 한다.  
  
DelegatingFilterProxy 자체가 인증이나 인가를 수행하는 것은 아니다.  
  
위임 방법:  
DelegatingFilterProxy는  
DEFAULT_FILTER_NAME 을 가진 Filter Bean에게 요청을 위임한다.    
그것은 FilterChainProxy 이다.    
   
DEFAULT_FILTER_NAME은 "springSecurityFilterChain"이라는 이름이며  
DelegatingFilterProxy가 요청을 위임할 대상 Filter Bean의 이름이다.  
  
DelegatingFilterProxy #(에 정의된 메서드) doFilter()는 Spring Bean Filter에게 요청을 위임한다.  
FilterChainProxy#doFilter()는 매칭되는 SecurityFilterChain을 선택하고 내부 Security Filter들을 실행한다.  

<br>
    
**클라리언트 요청의 필터 통과 flow:**   
클라이언트 요청이 들어와 Tomcat에 도착한다.  
DelegatingFilterProxy 가 요청을 받아 doFilter()가 호출된다.  
  
Spring ApplicationContext에서 DEFAULT_FILTER_NAME 이름의 Filter Bean에게 위임한다.  
  
그것이 FilterChainProxy 이고  
  
FilterChainProxy는 자신이 가지고 있는 List<SecurityFilterChain>을 확인한다.  
  
처음 매칭된 SecurityFilterChain 하나에 리퀘스트가 넘어가 인증인가 방식이 동작한다.  
  
보안 검사를 통하면 Controller 로 넘어간다.  
  
  
<img width="1399" height="852" alt="securityPNG" src="https://github.com/user-attachments/assets/6d758cac-3500-4b5e-97c4-dc7e3e7a228e" />
  
출처: [GDG Hongik] 스프링 시큐리티 스터디 6주차 (2026-1)  

**완강 후기:**
로그인 처리와 인증/인가의 동작은
거의 모든 애플리케이션에서 필수적인 기능인 만큼 
정말 중요한 강의를 수강했다는 느낌이 들었습니다.

앞으로 큰 도움이 될 수 있을 것이란 생각이 들었고
유익한 강의 만들어주셔서 정말 감사드립니다!
