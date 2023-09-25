package com.kodigo.shopping.online.store.security.configuration;

import com.kodigo.shopping.online.store.security.dto.Roles;
import com.kodigo.shopping.online.store.security.filter.JwtRequestFilter;
import com.kodigo.shopping.online.store.security.service.CustomUserDetailsService;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.PropertySource;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;
import org.springframework.web.filter.CorsFilter;

import javax.servlet.http.HttpServletResponse;
import java.util.Arrays;

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

		http.authorizeRequests().antMatchers("/actuator/**").permitAll().antMatchers("/auth/authenticate").permitAll()
				.antMatchers("/user").hasAuthority("ROLE_Admin").antMatchers("/**")
				.hasAnyRole(Roles.Guest.toString(), Roles.Admin.toString()).antMatchers("/**")
				.hasAnyRole(Roles.Guest.toString(), Roles.Admin.toString()).antMatchers("/**").permitAll()
				.antMatchers("/**").permitAll().antMatchers("/**").hasAnyRole(Roles.Admin.toString()).anyRequest()
				.authenticated();
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

//    @Bean
//    public CorsFilter corsFilter() {
//        String[] origins = allowedOrigins.split(",");
//        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
//        CorsConfiguration config = new CorsConfiguration();
//        config.setAllowCredentials(true);
//        config.setAllowedOrigins(Arrays.asList(origins));
//        config.setAllowedHeaders(Arrays.asList("*"));
//        config.setAllowedMethods(Arrays.asList("GET", "POST", "PUT", "DELETE"));
//        source.registerCorsConfiguration("/**", config);
//        return new CorsFilter(source);
//    }

}
