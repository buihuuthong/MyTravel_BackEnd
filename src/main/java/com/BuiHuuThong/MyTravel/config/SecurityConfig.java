package com.BuiHuuThong.MyTravel.config;

import com.BuiHuuThong.MyTravel.authentication.FirebaseAuthenticationFilter;
import com.BuiHuuThong.MyTravel.authentication.FirebaseAuthenticationProvider;
import com.BuiHuuThong.MyTravel.authentication.FirebaseService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.ProviderManager;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.web.server.SecurityWebFiltersOrder;
import org.springframework.security.config.web.server.ServerHttpSecurity;
import org.springframework.security.web.server.SecurityWebFilterChain;
import org.springframework.web.server.WebFilter;

import java.util.Collections;
import java.util.List;

@EnableWebSecurity
@EnableGlobalMethodSecurity(prePostEnabled = true)
public class SecurityConfig {

	private final FirebaseService firebaseService;
	private final FirebaseAuthenticationFilter firebaseAuthenticationFilter;
	private final FirebaseAuthenticationProvider firebaseAuthenticationProvider;

	@Value("${app.cors.allowed-origins}")
	private List<String> CORS_ALLOWED_ORIGINS;
	@Autowired
	public SecurityConfig(FirebaseService firebaseService, FirebaseAuthenticationFilter firebaseAuthenticationFilter,
						  FirebaseAuthenticationProvider firebaseAuthenticationProvider) {
		this.firebaseService = firebaseService;
		this.firebaseAuthenticationFilter = firebaseAuthenticationFilter;
		this.firebaseAuthenticationProvider = firebaseAuthenticationProvider;
	}
	@Bean
	public SecurityWebFilterChain securityWebFilterChain(ServerHttpSecurity http) {
		http
				.csrf().disable()
				.authorizeExchange()
				.pathMatchers("/api-docs/**", "/swagger-ui/**").permitAll()
				.anyExchange().authenticated()
				.and()
				.addFilterBefore((WebFilter) firebaseAuthenticationFilter, SecurityWebFiltersOrder.AUTHENTICATION);
		return http.build();
	}

	@Bean
	public FilterRegistrationBean<FirebaseAuthenticationFilter> firebaseAuthenticationFilterRegistrationBean() {
		FilterRegistrationBean<FirebaseAuthenticationFilter> filterRegistrationBean =
				new FilterRegistrationBean<>();
		filterRegistrationBean.setFilter(firebaseAuthenticationFilter);
		filterRegistrationBean.setOrder(1);
		return filterRegistrationBean;
	}

	@Bean
	public AuthenticationManager authenticationManager() {
		return new ProviderManager(Collections.singletonList(firebaseAuthenticationProvider));
	}
}
