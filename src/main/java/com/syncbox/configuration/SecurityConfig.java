package com.syncbox.configuration;


import com.syncbox.helper.handler.CustomLoginFailureHandler;
import com.syncbox.helper.handler.CustomLoginSuccessHandler;
import com.syncbox.helper.handler.CustomLogoutHandler;
import com.syncbox.services.implementation.SecurityCustomUserDetailService;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;

import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
public class SecurityConfig {

    private final SecurityCustomUserDetailService securityCustomUserDetailService;

    private final PasswordEncoder passwordEncoder;

    private final CustomLogoutHandler LogoutHandler;

    private final CustomLoginSuccessHandler successHandler;

    private final CustomLoginFailureHandler failureHandler;


    public SecurityConfig(
            SecurityCustomUserDetailService securityCustomUserDetailService,
            PasswordEncoder passwordEncoder, CustomLogoutHandler logoutHandler,
            CustomLoginSuccessHandler successHandler,
            CustomLoginFailureHandler failureHandler
    ) {
        this.securityCustomUserDetailService = securityCustomUserDetailService;
        this.passwordEncoder = passwordEncoder;
        LogoutHandler = logoutHandler;
        this.successHandler = successHandler;
        this.failureHandler = failureHandler;
    }



//    Configuration of AuthenticationProvider
    @Bean
    public AuthenticationProvider authenticationProvider(){
        DaoAuthenticationProvider authenticationProvider = new DaoAuthenticationProvider();
//        UserDetailsService Object
        authenticationProvider.setUserDetailsService(securityCustomUserDetailService);
//        PasswordEncoder Object
        authenticationProvider.setPasswordEncoder(this.passwordEncoder);
        return authenticationProvider;
    }
//    Configuration of SecurityFilterChain
    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity httpSecurity) throws Exception {
//       Configure URLS
        httpSecurity.authorizeHttpRequests(authorize->{
            authorize.requestMatchers("/user/**").authenticated();
            authorize.anyRequest().permitAll();
        });
//      configure login form
        httpSecurity.formLogin(formLogin->{
            formLogin.loginPage("/sign-in");
            formLogin.loginProcessingUrl("/authenticate");
            formLogin.failureHandler(this.failureHandler);
            formLogin.successHandler(this.successHandler);
            formLogin.usernameParameter("email");
            formLogin.passwordParameter("password");

        });
//        configure logout
        httpSecurity.logout(logout->{
            logout.logoutUrl("/logout");
            logout.logoutSuccessHandler(this.LogoutHandler);
        });
        httpSecurity.csrf(AbstractHttpConfigurer::disable);


        httpSecurity.oauth2Login(auth->{
            auth.loginPage("/sign-in");
            auth.successHandler(this.successHandler);
        });
//        Form Default login
//        httpSecurity.formLogin(Customizer.withDefaults());
        return httpSecurity.build();
    }


//    IN MEMORY DEFAULT USERS
//    @Bean
//    public UserDetailsService userDetailsService(){
//        UserDetails user1 = User.withDefaultPasswordEncoder()
//                .username("admin")
//                .password("admin")
//                .roles("ADMIN","USER")
//                .build();
//        UserDetails user2 = User.withDefaultPasswordEncoder()
//                .username("sarvech")
//                .password("sarvech")
//                .roles("USER")
//                .build();
//        return new InMemoryUserDetailsManager(user1,user2);
//    }

}
