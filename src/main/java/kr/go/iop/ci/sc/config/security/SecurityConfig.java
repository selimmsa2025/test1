package kr.go.iop.ci.sc.config.security;

import lombok.RequiredArgsConstructor;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

/**
 * org.egovframe.cloud.userservice.SecurityConfig
 * <p>
 * Spring Security Config 클래스.
 * AuthenticationFilter 를 추가하고 로그인 인증처리를 한다
 *
 * @author 표준프레임워크센터 jaeyeolkim
 * @version 1.0
 * @since 2021/06/30
 *
 * <pre>
 * << 개정이력(Modification Information) >>
 *
 *     수정일        수정자            수정내용
 *  ----------    --------    ---------------------------
 *  2021/06/30    jaeyeolkim  최초 생성
 *  2023.11.13    양정숙        MSA팀 적용
 * </pre>
 */
@Configuration
@RequiredArgsConstructor
@EnableWebSecurity // Spring Security 설정들을 활성화시켜 준다
public class SecurityConfig {
	
	private final TokenProvider tokenProvider;

	/**
	 * 스프링 시큐리티 설정
	 *
	 * @param http
	 * @throws Exception
	 */
	@Bean
	public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {

	    // 로그인 인증정보를 받아 토큰을 발급할 수 있도록 필터를 등록해준다.
	    JwtAuthenticationFilter jwtAuthenticationFilter = new JwtAuthenticationFilter(tokenProvider);

	    http
	        .csrf(csrf -> csrf.disable())
	        .cors(cors -> {})  // cors().and() 대신 람다 형식으로 변경
	        .headers(headers -> headers.frameOptions().disable())
	        .sessionManagement(session -> session
	            .sessionCreationPolicy(SessionCreationPolicy.STATELESS))
	        .authorizeHttpRequests(auth -> auth
	            .requestMatchers("/actuator/**", "/v1/**").permitAll()
	            .anyRequest().authenticated())
	        .addFilterBefore(jwtAuthenticationFilter, UsernamePasswordAuthenticationFilter.class);

	    return http.build();
	}
}

