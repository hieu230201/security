package com.example.securitybase.config.security;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;

import java.util.stream.Collectors;
import java.util.stream.Stream;

import static org.springframework.security.config.Customizer.withDefaults;

@Configuration
@EnableWebSecurity
//@EnableGlobalMethodSecurity(prePostEnabled = true)
public class SecurityConfig extends WebSecurityConfigurerAdapter {

//    private final CustomAuthenticationProviderRemote authenticationProviderRemote;
//
//    public SecurityConfig(CustomAuthenticationProviderRemote authenticationProviderRemote) {
//        this.authenticationProviderRemote = authenticationProviderRemote;
//    }

    @Override
    protected void configure(AuthenticationManagerBuilder auth) {
// var authenticationProviderRemote = AppContext.getBean(CustomAuthenticationProviderRemote.class);

//        auth.authenticationProvider(authenticationProviderRemote);
    }

    @Bean
    @Override
    public AuthenticationManager authenticationManagerBean() throws Exception {
        return super.authenticationManagerBean();
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        String[] excludeUrl = Stream.of(OAuth2ResourceServerConfig.EXCLUDE_URL_PATTERN).flatMap(Stream::of).map(Object::toString).toArray(String[]::new);

        http.cors().and()
                .csrf().disable()
                .antMatcher("/**")
                .authorizeRequests()
                .antMatchers(excludeUrl).permitAll()
                .anyRequest().authenticated()
                .and().httpBasic();

    }

    @Override
    public void configure(final WebSecurity web) {
        var excludeUrl = Stream.of(OAuth2ResourceServerConfig.EXCLUDE_URL_PATTERN).flatMap(Stream::of).map(Object::toString).collect(Collectors.toList());
        excludeUrl.remove("/oauth/**");

        web.ignoring().antMatchers(excludeUrl.toArray(String[]::new));
    }
}
