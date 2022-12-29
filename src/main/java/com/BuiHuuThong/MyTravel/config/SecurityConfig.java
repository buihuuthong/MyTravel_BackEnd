package com.BuiHuuThong.MyTravel.config;

import com.BuiHuuThong.MyTravel.authentication.FirebaseAuthenticationFilter;
import com.BuiHuuThong.MyTravel.authentication.FirebaseAuthenticationProvider;
import com.BuiHuuThong.MyTravel.authentication.FirebaseService;
import com.BuiHuuThong.MyTravel.component.ApiRequestLoggingFilter;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.ProviderManager;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.config.web.server.SecurityWebFiltersOrder;
import org.springframework.security.config.web.server.ServerHttpSecurity;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.security.web.server.SecurityWebFilterChain;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;
import org.springframework.web.server.WebFilter;

import java.util.Collections;
import java.util.List;

@EnableWebSecurity
@EnableGlobalMethodSecurity(prePostEnabled = true)
@RequiredArgsConstructor
public class SecurityConfig {
	private final UserDetailsService userDetailsService;
	private final FirebaseAuthenticationFilter firebaseAuthenticationFilter;
	private final ApiRequestLoggingFilter apiRequestLoggingFilter;
	private final FirebaseAuthenticationProvider firebaseAuthenticationProvider;

	@Value("${app.cors.allowed-origins}")
	private List<String> CORS_ALLOWED_ORIGINS;

	@Bean
	public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
		http.cors();
		http.csrf().disable();
		http.httpBasic().disable().formLogin().disable().logout().disable();
		http.headers().cacheControl().disable().frameOptions().disable();
		http.sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS);
		http.authorizeRequests().anyRequest().permitAll();
		http.addFilterBefore(apiRequestLoggingFilter, UsernamePasswordAuthenticationFilter.class);
		http.addFilterBefore(firebaseAuthenticationFilter, UsernamePasswordAuthenticationFilter.class);
		return http.build();
	}

	@Bean
	public AuthenticationManager authenticationManager(HttpSecurity http) throws Exception {
		// Sử dụng 2 AuthenticationProvider để xác thực JWT và username/password (khi login)
		AuthenticationManagerBuilder builder = http.getSharedObject(AuthenticationManagerBuilder.class);
		builder.authenticationProvider(firebaseAuthenticationProvider);
		builder.authenticationProvider(daoAuthenticationProvider());
		return builder.build();
	}

	@Bean
	public DaoAuthenticationProvider daoAuthenticationProvider() {
		var provider = new DaoAuthenticationProvider();
		provider.setPasswordEncoder(passwordEncoder());
		provider.setUserDetailsService(userDetailsService);
		return provider;
	}

	@Bean
	public PasswordEncoder passwordEncoder() {
		return new BCryptPasswordEncoder();
	}

	// Tên method phải là "corsConfigurationSource"
	@Bean
	public CorsConfigurationSource corsConfigurationSource() {
		var corsConfig = new CorsConfiguration();
		corsConfig.setAllowedOrigins(CORS_ALLOWED_ORIGINS);
		corsConfig.setAllowedMethods(List.of("GET", "POST", "PUT", "PATCH", "DELETE", "OPTIONS"));
		corsConfig.setAllowedHeaders(Collections.singletonList("*"));
		corsConfig.setAllowCredentials(false); // Disable if no cookies sent
		var source = new UrlBasedCorsConfigurationSource();
		source.registerCorsConfiguration("/**", corsConfig);
		return source;
	}
}
