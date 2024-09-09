# springsecurity_study_
### 🔥이 글의 목적은 직접 다시 코드를 작성해보면서 springsecurity를 보다 더 이해하기 위함이다.

공부 기술 : SpringSecurity, JWT

### 시나리오
1. 사용자는 이메일, 이름, 패스워드를 입력하여 회원가입을 할 수 있다.
   1. 이메일이 존재한다면 예외처리
   2. 입력 방식을 제한하지는 않는다.
2. 사용자는 이메일, 패스워드를 통해 로그인을 할 수 있다.
3. 처음 로그인한 사용자는 "GUEST" 권한을 갖는다.
4. 임의의 조건(ex: 게시글 5개 이상 작성 등)에 의해 "USER" 권한을 가지게 된다.

### 구현 방식
* SpringBoot 3.x.x로 오면서 이전에 ```WebSecurityConfigurerAdapter```를 상속받아 configure을 구현하는 방식
  대신에 SecurityFilterChain을 등록해서 람다식으로 처리하는 방식으로 구현
* JWT 관련 구현방식 추가
* ```DAOAuthenticationProvider```를 통해 사용자 검증
  * ```UserDetailService```를 커스텀 하여 구현 및 사용
  * ```BCryptPasswordEncoder``` 사용
  * ```UserDetail```을 커스텀 하여 구현 및 사용
* 

### 인증과정 (JWT Filter를 제외한 과정  -> 수정필요)
1. 사용자의 요청이 들어오면 DispatcherServlet 이전에 SecurityFilter 작동
2. SecurityFilter 의 설정에 따라 사용자가 요청한 url에 인증 및 인가가 필요한지 확인
   1. 인증필요
      1. ```AuthenticationProvider```를 통해 인증 및 검사를 진행 (본인은 ```DAOAuthenticationProvider```를 통해 구현)
      2. 주요 과정은 ```AbstractUserDetailsAuthenticationProvider```를 상속받아 구현하고 있는 ```DAOAuthenticationProvider```가 ```authenticate```메소드를 오버라이드하여 비밀번호 검증 및 인증을 처리한다. 
      3. 인증에 성공하면 ```Authentication```객체가 반환되고 이 객체는 SecurityContext에 저장된다. 
      4. 인증에 실패하면 ```BadCredentialsException``` 예외가 발생한다.
   2. 인증 불필요
      1. ```.permitAll()```등으로 요청 url이 승인되었다면 DispatcherServlet으로 전달
      2. DispatcherServlet 이 요청을 처리할 컨트롤러를 호출하고 응답을 반환.


Postman으로 테스트를 진행했으며 이후 테스트코드를 작성할 예정
