package com.syncbox.configuration;


import com.syncbox.services.implementation.SecurityCustomUserDetailService;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.AuthenticationFailureHandler;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;

import java.io.IOException;
import java.util.List;
import java.util.Set;

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
//
////    Configuration of SecurityFilterChain
    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity httpSecurity) throws Exception {
//       Configure URLS

        httpSecurity.authorizeHttpRequests(authorize->{
//            authorize.requestMatchers("/","sign-up","/sign-in").permitAll();
            authorize.requestMatchers("/user/**").authenticated();
            authorize.anyRequest().permitAll();
        });

        httpSecurity.formLogin(formLogin->{
            formLogin.loginPage("/sign-in");
            formLogin.loginProcessingUrl("/authenticate");
//            formLogin.successForwardUrl("/user/dashboard");
            formLogin.failureUrl("/sign-in?error=true");
            formLogin.usernameParameter("email");
            formLogin.passwordParameter("password");

            // Customize handlers
            formLogin.successHandler(new AuthenticationSuccessHandler() {
                @Override
                public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response, Authentication authentication) throws IOException, ServletException {
                    Set<String> roles =  AuthorityUtils.authorityListToSet(authentication.getAuthorities());
                    if(roles.contains("ROLE_ADMIN")){
                        response.sendRedirect("/admin/dashboard");
                    }else{
                        response.sendRedirect("/user/dashboard");
                    }

                }
            });
//            formLogin.failureHandler(new AuthenticationFailureHandler() {
//                @Override
//                public void onAuthenticationFailure(HttpServletRequest request, HttpServletResponse response, AuthenticationException exception) throws IOException, ServletException {
//
//                }
//            });

        });

//        httpSecurity.formLogin(formLogin -> {
//            formLogin
//                    .loginPage("/sign-in") // Custom login page
//                    .loginProcessingUrl("/authenticate") // Endpoint for Spring Security to handle login
//                    .defaultSuccessUrl("/user/dashboard") // Redirect on successful authentication
//                    .failureUrl("/sign-in?error=true") // Redirect on failed authentication
//                    .usernameParameter("email") // Input field name for username
//                    .passwordParameter("password"); // Input field name for password
//        });

        httpSecurity.logout(logout -> {
            logout.logoutUrl("/logout")
               .logoutSuccessUrl("/sign-in?logout=true");
        });
        httpSecurity.csrf(AbstractHttpConfigurer::disable);

//        Form Default login
//        httpSecurity.formLogin(Customizer.withDefaults());
        return httpSecurity.build();
    }

//    @Bean
//    public SecurityFilterChain securityFilterChain(HttpSecurity httpSecurity) throws Exception {
//        // Configure URL-based access
//        httpSecurity.authorizeHttpRequests(authorize -> {
//            authorize
//                    .requestMatchers("/", "/sign-up", "/sign-in").permitAll() // Public access URLs
//                    .requestMatchers("/user/**").authenticated() // Authenticated users only
//                    .anyRequest().permitAll(); // Permit all other requests (adjust based on your app's needs)
//        });
//
//        // Configure form-based login
//        httpSecurity.formLogin(formLogin -> {
//            formLogin
//                    .loginPage("/sign-in") // Custom login page
//                    .loginProcessingUrl("/authenticate") // Endpoint for Spring Security to handle login
//                    .defaultSuccessUrl("/user/dashboard") // Redirect on successful authentication
//                    .failureUrl("/sign-in?error=true") // Redirect on failed authentication
//                    .usernameParameter("email") // Input field name for username
//                    .passwordParameter("password"); // Input field name for password
//        });
//
//        // Configure logout
//        httpSecurity.logout(logout -> {
//            logout
//                    .logoutUrl("/logout") // Endpoint for logging out
//                    .logoutSuccessUrl("/sign-in"); // Redirect after successful logout
//        });
//
//        // Disable CSRF for development or specific use cases
//        httpSecurity.csrf(AbstractHttpConfigurer::disable);
//
//        // Build and return the SecurityFilterChain
//        return httpSecurity.build();
//    }



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
