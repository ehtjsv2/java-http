## 미니 톰캣 구현하기

[톰캣 구현 코드로 이동](https://github.com/ehtjsv2/java-http/tree/main/tomcat/src/main/java/org/apache)
[톰캣을 사용한 어플리케이션 코드로 이동](https://github.com/ehtjsv2/java-http/tree/main/tomcat/src/main/java/com)

### 학습목표
- 웹 서버 구현을 통해 HTTP 이해도를 높인다.
- HTTP의 이해도를 높혀 성능 개선할 부분을 찾고 적용할 역량을 쌓는다.
- 서블릿에 대한 이해도를 높인다.
- 스레드, 스레드풀을 적용해보고 동시성 처리를 경험한다.

### 지원 기능
- HTTP 1.1 지원
- 정적리소스 GET지원
- 동적리소스 (Servlet 기반GET, POST지원)
  - Query Parameter, Path Variable, JSON 요청본문 지원
- 멀티스레드 처리 (요청당 스레드 할당)
- 응답 헤더 및 쿠키 설정 기능
- 세션 관리 기능
