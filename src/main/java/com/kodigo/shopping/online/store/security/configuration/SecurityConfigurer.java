package com.kodigo.shopping.online.store.security.configuration;

import com.kodigo.shopping.online.store.security.dto.Roles;
import com.kodigo.shopping.online.store.security.filter.JwtRequestFilter;
import com.kodigo.shopping.online.store.security.service.CustomUserDetailsService;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.PropertySource;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import javax.servlet.http.HttpServletResponse;

@EnableWebSecurity
@PropertySource("classpath:application.yml")
public class SecurityConfigurer extends WebSecurityConfigurerAdapter {

	String allowedOrigins;

	private final CustomUserDetailsService customUserDetailsService;
	private final JwtRequestFilter requestFilter;

	public SecurityConfigurer(CustomUserDetailsService customUserDetailsService, JwtRequestFilter requestFilter) {
		this.customUserDetailsService = customUserDetailsService;
		this.requestFilter = requestFilter;
	}

	@Override
	protected void configure(AuthenticationManagerBuilder auth) throws Exception {
		auth.userDetailsService(customUserDetailsService).passwordEncoder(passwordEncoder());
	}

	@Override
	protected void configure(HttpSecurity http) throws Exception {
		http = http.cors().and().csrf().disable();
		http = http.sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS).and();
		http = http.exceptionHandling().authenticationEntryPoint((request, response, ex) -> {
			response.sendError(HttpServletResponse.SC_UNAUTHORIZED, ex.getMessage());
		}).and();

		http.authorizeRequests().antMatchers("/actuator/**").permitAll()
				.antMatchers("/auth/authenticate").permitAll()
				.antMatchers("/user").hasAuthority("ROLE_Admin")
				.antMatchers("/category", "/product").hasAnyRole(Roles.Guest.toString(), Roles.Admin.toString())
				.antMatchers(HttpMethod.DELETE, "/product/{id}").hasAuthority("ROLE_Admin") // Limitar el DELETE específico
				.antMatchers("/**").permitAll()
				.anyRequest().authenticated();
		http.addFilterBefore(requestFilter, UsernamePasswordAuthenticationFilter.class);
	}

	@Bean
	@Override
	public AuthenticationManager authenticationManagerBean() throws Exception {
		return super.authenticationManagerBean();
	}

	@Bean
	public BCryptPasswordEncoder passwordEncoder() {
		return new BCryptPasswordEncoder();
	}

}
