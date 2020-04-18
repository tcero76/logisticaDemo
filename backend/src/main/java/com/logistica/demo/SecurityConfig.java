package com.logistica.demo;


import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.BeanIds;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.security.web.authentication.logout.LogoutSuccessHandler;

import com.logistica.demo.security.UnauthorizedHandler;
import com.logistica.demo.security.JwtAuthenticationFilter;
import com.logistica.demo.service.UsuarioServiceImpl;
import org.springframework.web.filter.OncePerRequestFilter;

@Configuration
@EnableGlobalMethodSecurity(
		  prePostEnabled = true, 
		  securedEnabled = true, 
		  jsr250Enabled = true)
public class SecurityConfig  extends WebSecurityConfigurerAdapter {
	
	private static final Logger logger = LoggerFactory.getLogger(SecurityConfig.class);
	
	@Autowired
	private UsuarioServiceImpl usuarioservice;
	
	@Override
	public void configure(AuthenticationManagerBuilder authenticationManagerBuilder) {
		try {
			authenticationManagerBuilder
				.userDetailsService(usuarioservice)
				.passwordEncoder(passwordEncoder());
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

    @Autowired
    private UnauthorizedHandler unauthorizedHandler;
    
    @Autowired
	@Qualifier("JwtAuthenticationFilter")
    private OncePerRequestFilter jwtAuthenticationFilter;
    
    @Bean(BeanIds.AUTHENTICATION_MANAGER)
    public AuthenticationManager authenticationManagerBean() throws Exception {
        return super.authenticationManagerBean();
    }
    
    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    private LogoutSuccessHandler logoutSuccessHandler = new LogoutSuccessHandler() {

		@Override
		public void onLogoutSuccess(HttpServletRequest request, HttpServletResponse response,
				Authentication authentication) throws IOException, ServletException {

			logger.info("Usuario logout");
		}
    };
    
    @Override
	public void configure(HttpSecurity http) throws Exception {
        http
    	.addFilterBefore(jwtAuthenticationFilter, UsernamePasswordAuthenticationFilter.class)
		        .cors().and()
		        .csrf().disable()
				.exceptionHandling()
				.authenticationEntryPoint(unauthorizedHandler)
			.and()
                .sessionManagement()
                .sessionCreationPolicy(SessionCreationPolicy.STATELESS)
            .and()
		        .logout()
		        .logoutUrl("/perform_logout")
		        .logoutSuccessHandler(logoutSuccessHandler)
		        .deleteCookies("JSESSIONID")
	        .and()
		        .authorizeRequests()
		        .antMatchers("/ubicar/**").permitAll()
		        .antMatchers("/signin").permitAll()
		        .antMatchers("/user/usuario").permitAll()
		        .anyRequest()
				.authenticated();
    }
   }
