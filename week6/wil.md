**스프링 부트 실행 flow:**  
SpringsecurityApplication 의 run()을 따라가보자. (Ctrl + 좌클릭)  
  
prepareContext(): Bean 정의, 초기 설정  
refreshContext(): Bean 생성, 의존성 주입  
  
refreshContext() 를 따라가보자.  
WebServerApplicationContext에서 refresh 가 동작을 한다.  
refresh 는 onRefresh 를 호출을 하고 onRefresh 에서 웹서버를 만든다.  
refresh 의 마지막은 동작은 finish refresh, tomcat 웹 서버가 start 한다.  
  
**SecurityFilterChain 생성 flow:**  
configurer 을 넣어놓고 이 설정들을 스프링이 구현하고 있는 builder class의 configurer에 저장을 하고  
build를 할때 설정을 가져와서 이니설 라이즈하고 configure 한다.  
  
**FilterChainProxy 생성 flow:**  
13:56 부터, pdf 참조  




