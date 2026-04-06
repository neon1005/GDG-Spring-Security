서버가 사용자의 상태를 기억하지 않는 방식: TOKEN 기반 인증, Stateless  
    
사용자(로그인 요청) -> 스프링 시큐리티(토큰 발급) -> 사용자(토큰 소지)   
사용자(토큰 제시) -> 인가 영역(토큰 내용만 검증) -> 접근 허용 또는 거부(토큰 유효하면 허용, 아니면 거부)  
서버는 토큰만 보고 판단하고 세션 저장소가 없다.  
  
토큰의 규약  
1. 토큰은 형식이 정해져 있어야 한다. (같은 규격이어야 읽을 수 있다  
2. 변조하기 어려워야 한다. (토큰 내용을 마음대로 바꿀 수 없다.   
-->> JWT(Json Web Token)  
  
JWT의 구조  
Header(alg, typ) . Payload(claims(sub, name, iat)) . Signature  
  
Header - 어떤 알고리즘으로 서명되었는가?  
payload - 사용자 정보  
Signature - Header/Payload 를 비밀키로 서명한 값  
GDG 6:33부터 ..
