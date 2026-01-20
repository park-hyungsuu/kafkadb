/*
 * package com.hyungsuu.common.config;
 * 
 * import org.springframework.context.annotation.Bean; import
 * org.springframework.context.annotation.Configuration; import
 * org.springframework.security.config.annotation.method.configuration.
 * EnableMethodSecurity; import
 * org.springframework.security.config.annotation.web.builders.HttpSecurity;
 * import org.springframework.security.config.annotation.web.configuration.
 * EnableWebSecurity; import
 * org.springframework.security.config.annotation.web.configuration.
 * WebSecurityCustomizer; import
 * org.springframework.security.config.http.SessionCreationPolicy; import
 * org.springframework.security.web.SecurityFilterChain;
 * 
 * import lombok.RequiredArgsConstructor;
 * 
 * @Configuration
 * 
 * @EnableWebSecurity
 * 
 * @RequiredArgsConstructor
 * 
 * @EnableMethodSecurity public class SecurityConfig {
 * 
 * 
 * 
 * 
 *//**
	 * 특정 HTTP 요청에 대한 웹 기반 보안 구성
	 *//*
		 * @Bean public SecurityFilterChain securityFilterChain(HttpSecurity http)
		 * throws Exception { http // .authorizeHttpRequests(auth -> auth
		 * .requestMatchers("/login").permitAll() // 로그인 경로는 허용
		 * .requestMatchers("/admin/**").hasRole("ADMIN") // 어드민 경로는 ADMIN 권한 필요
		 * .anyRequest().authenticated() // 그 외 경로는 인증 필요 ) .sessionManagement(session
		 * -> session .sessionCreationPolicy(SessionCreationPolicy.STATELESS) // 세션 정책
		 * 설정 );
		 * 
		 * return http.build(); }
		 * 
		 * 
		 * @Bean public WebSecurityCustomizer webSecurityCustomizer() { return web -> {
		 * web.ignoring().equals("/api1/templet/getJwtToken"); // 필터를 타면 안되는 경로 }; } }
		 */