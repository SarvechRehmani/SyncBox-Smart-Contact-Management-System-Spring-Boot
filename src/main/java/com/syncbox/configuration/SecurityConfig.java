package com.syncbox.configuration;


import com.syncbox.services.implementation.SecurityCustomUserDetailService;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
public class SecurityConfig {

    private SecurityCustomUserDetailService securityCustomUserDetailService;

    public SecurityConfig(SecurityCustomUserDetailService securityCustomUserDetailService) {
        this.securityCustomUserDetailService = securityCustomUserDetailService;
    }


    @Bean
    public PasswordEncoder passwordEncoder(){
        return new BCryptPasswordEncoder();
    }

//    Configuration of AuthenticationProvider
    @Bean
    public AuthenticationProvider authenticationProvider(){
        DaoAuthenticationProvider authenticationProvider = new DaoAuthenticationProvider();
//        UserDetailsService Object
        authenticationProvider.setUserDetailsService(securityCustomUserDetailService);
//        PasswordEncoder Object
        authenticationProvider.setPasswordEncoder(passwordEncoder());
        return authenticationProvider;
    }

//    Configuration of SecurityFilterChain
    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity httpSecurity) throws Exception {
//       Configure URLS
        httpSecurity.authorizeHttpRequests(authorize->{
//            authorize.requestMatchers("/","sign-up","/sign-in").permitAll();
            authorize.requestMatchers("/user/**").authenticated();
            authorize.anyRequest().permitAll();
        });

        httpSecurity.formLogin(formLogin->{
            formLogin.loginPage("/sign-in")
                    .loginProcessingUrl("/authenticate");
            formLogin.successForwardUrl("/user/dashboard");
            formLogin.failureForwardUrl("/sign-in?error=true");
            formLogin.usernameParameter("email");
            formLogin.passwordParameter("password");
//            formLogin.successHandler();
//            formLogin.failureHandler();
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
