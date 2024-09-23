
# springsecurity_study_
#### 🔥이 글의 목적은 직접 다시 코드를 작성해보면서 springsecurity를 보다 더 이해하기 위함이다.

공부 기술 : SpringSecurity, JWT

#### 일반적인 인증과정

1. 클라이언트의 로그인 시도
2. **AuthenticationFilter** 동작
   1. **ServletFilter**에 의해 **SecurityFilter**로 작업위임
   2. **SecurityFilter**중 **UsernamePasswordAuthenticationFilter**가 인증처리
3. **UsernameAuthenticationToken** 발급
4. **AuthenticationManager**에 인증 객체 전달
5. **AuthenticationManager**는 **AuthenticationProvider**에게 인증객체 전달
6. **AuthenticationProvider**는 **UserDetailService**에 인증객체전달
7. **UserDetailService**에서 **UserDetails**객체 반환 (```loadUserByUsername()```동작)
8. 객체를 **AuthenticationProvider** 검증하고 인증에 성공하면 **AuthenticationManager**에 권한을 담은 객체 전달
9. **AuthenticationManager**가 **AuthenticationFilter**에 전달
10. 인증된 객체를 **SecurityContextHolder**에 저장

#### 시나리오
* 사용자가 회원가입과 로그인을 하는 과정을 담고 있다.

#### 구현 방식 
* SpringBoot 3.x.x로 오면서 이전에 ```WebSecurityConfigurerAdapter```를 상속받아 configure을 구현하는 방식 대신에 SecurityFilterChain을 등록해서 람다식으로 처리하는 방식으로 구현
* ```DAOAuthenticationProvider```를 통해 사용자 검증
  * ```UserDetailService```를 커스텀 하여 구현 및 사용
  * ```BCryptPasswordEncoder``` 사용
  * ```UserDetail```을 커스텀 하여 구현 및 사용
* JWT를 활용한 인증 위해 추가 수정 (JwrFilter , JwtService)
  * SecurityFilter에 ```addFilter()```를 통해 ```JwtFilter```를 추가
  * HMAC-SHA algorithms 을 사용 
    * ```Keys.hmacShaKeyFor()```
  * 포워딩 등으로 동일한 요청에 한번만 filter를 거치게 하기위해 ```OnceperRequestFilter```를 상속해 JwtFilter를 생성
    
#### 인증과정 I
1. 사용자의 요청이 들어오면 DispatcherServlet 이전에 SecurityFilter 작동
2. SecurityFilter 의 설정에 따라 사용자가 요청한 url에 인증 및 인가가 필요한지 확인
   1. **인증필요**
      1. @Bean을 통해 등록된 ```AuthenticationManager```가 ```AuthenticationProvider```를 찾는다.
      2. ```AuthenticationProvider```를 통해 인증 및 검사를 진행 (본인은 ```DAOAuthenticationProvider```를 통해 구현)
      3. 주요 과정은 ```AbstractUserDetailsAuthenticationProvider```를 상속받아 구현하고 있는 ```DAOAuthenticationProvider```가 ```authenticate```메소드를 오버라이드하여 비밀번호 검증 및 인증을 처리한다. 
      4. 이 과정에서 본인이 등록한 ```CustomUserDetailService```를 통해 ```retreiveUser()```를 호출되고 사용자의 정보와 등록된 정보가 일치하는지 검증한다.
      5. 인증에 성공하면 ```Authentication```객체가 반환되고 이 객체는 SecurityContext에 저장된다. 
      6. 인증에 실패하면 ```BadCredentialsException``` 예외가 발생한다.
   2. **인증 불필요**
      1. ```.permitAll()```등으로 요청 url이 승인되었다면 DispatcherServlet으로 전달
      2. DispatcherServlet 이 요청을 처리할 컨트롤러를 호출하고 응답을 반환.

#### 인증과정 II (JWT 포함한 과정)
1. 사용자의 요청을 SecurityFilterChain 에 등록된 JwtFilter 가 가로채 요청 헤더의 jwt 를 확인한다. 이때 JwtFilter 는 모든 HTTP 요청에 대해 실행되며, 요청이 인증을 요구하는지 여부와 상관없이 JWT 검증 작업을 수행한다.
   1. **유효한 JWT** 
      * ```SecurityContextHolder.getContext().setAuthentication(authToken)``` 를 통해 SecurityContextHolder 에 인증 정보를 설정하여 이후의 요청 처리가 인증된 사용자로서 진행.
   2. **JWT가 없거나 유효하지 않음**
      * ```인증과정 I```를 거치고 인증에 성공하면 jwt를 발급받는다.  
      
#### 주의한 점
* 생략되는 과정을 최대한 생각하고 구현 
  * ex) ```SecurityConfig```클래스에 ```AuthenticationProvider``` 를 @Bean으로 등록하면, Spring Security는 이를 자동으로 감지하고 AuthenticationManager를 구성하여 인증 작업을 수행.
* 인증을 하는 과정에서 사용자의 요청파라미터에 ```username``` 이 없으면 ```UsernamePasswordAuthenticationFilter```가 사용자의 정보를 가져오지 못한다. 
  * JwtFilter에서 먼저 사용자 인증을 거치기 위해 ```http.addFilterBefore(jwtFilter, UsernamePasswordAuthenticationFilter.class);```를 사용
* 로그인을 할 때에는 jwt를 생성 
* ```/login```으로의 요청은 ```.permit()```을 허용해야한다.




Postman으로 테스트를 진행했으며 이후 테스트코드를 작성할 예정
