package com.example.securitybase.config.security;

import com.google.common.collect.ImmutableList;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configurers.ExpressionUrlAuthorizationConfigurer;
import org.springframework.security.oauth2.config.annotation.web.configuration.EnableResourceServer;
import org.springframework.security.oauth2.config.annotation.web.configuration.ResourceServerConfigurerAdapter;
import org.springframework.security.oauth2.config.annotation.web.configurers.ResourceServerSecurityConfigurer;
import org.springframework.security.oauth2.provider.token.DefaultTokenServices;
import org.springframework.security.oauth2.provider.token.TokenStore;
import org.springframework.security.oauth2.provider.token.store.JwtAccessTokenConverter;
import org.springframework.security.oauth2.provider.token.store.JwtTokenStore;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;

@Configuration
@EnableResourceServer
public class OAuth2ResourceServerConfig extends ResourceServerConfigurerAdapter {

    @Autowired
    SecurityConfigSettings securityConfigSettings;

    @Value("${rsa.public.key}")
    private String jwtPublicKey;

    public static final String[] EXCLUDE_URL_PATTERN = new String[]{

            "/", "/error", "/csrf", "/resources/**",
            "/ws/info", "/ws/**", "/soap/**",
            "/v3/api-docs**", "/v3/api-docs/**", "/swagger-ui/**", "/swagger-ui.html", "/swagger-ui.html/**", "/swagger-resources/**", "/swagger-ui.html#!/**", "/swagger-ui/index.html", "/swagger-ui/index.html#!/**",
            "/webjars/**", "/v2/api-docs", "/js/**",
            "/actuator/**", "/health/**", "/heapdump/**", "/metrics/**", "/prometheus/**",
            "/css/**", "/favicon.ico", "/csrf",
            "/login/**", "/oauth/**", "/.well-known/**",

            "/**/test/email",
            "/**/user/login",
            "/**/v1/sysJobSchedule/**",
            "/**/actuator",
            "/**/services/integratedCRA/getThongTinTaiSan",
            "/**/services/integratedCRA/getTsByInfo",
            "/**/actuator/**",

// "/**/CmvCsImportInfo/**",

            "/**/staticfiles/accessfiles/**",
            "/**/common/captcha/**",
            "/**/user/login",
            "/**/user/loginApp",
            "/**/user/loginUrl"
    };

    @Bean
    public MyAuthenticationEntryPoint myAuthenticationEntryPoint() {
        return new MyAuthenticationEntryPoint();
    }

    @Override
    public void configure(HttpSecurity http) throws Exception {

        configAnonymousUrl(http.cors().and().csrf().disable()
                .authorizeRequests())
                .anyRequest().authenticated()
                .and().httpBasic();
    }



    private ExpressionUrlAuthorizationConfigurer<HttpSecurity>.ExpressionInterceptUrlRegistry configAnonymousUrl(ExpressionUrlAuthorizationConfigurer<HttpSecurity>.ExpressionInterceptUrlRegistry http) {
        for (SecurityConfigSettings.AnonymousUrlConfig config : securityConfigSettings.getAnonymousUrlConfigs()) {
            http.antMatchers(config.getHttpMethod(), config.getUrls()).permitAll();
        }
        return http;
    }


    @Override
    public void configure(ResourceServerSecurityConfigurer config) {
        config.tokenServices(tokenServices());
        config.authenticationEntryPoint(myAuthenticationEntryPoint());
    }

    @Bean
    public TokenStore tokenStore() {
        return new JwtTokenStore(accessTokenConverter());
    }

    @Bean
    public JwtAccessTokenConverter accessTokenConverter() {
        JwtAccessTokenConverter converter = new JwtAccessTokenConverter();
        converter.setVerifierKey(jwtPublicKey);
        return converter;
    }

    @Bean
    @Primary
    public DefaultTokenServices tokenServices() {
        DefaultTokenServices defaultTokenServices = new DefaultTokenServices();
        defaultTokenServices.setTokenStore(tokenStore());
        return defaultTokenServices;
    }

    // To enable CORS
    @Bean
    public CorsConfigurationSource corsConfigurationSource() {
        final CorsConfiguration configuration = new CorsConfiguration();

        configuration.setAllowedOriginPatterns(ImmutableList.of("*")); // set access from all domains
        configuration.setAllowedMethods(ImmutableList.of("GET", "POST", "PUT", "DELETE"));
        configuration.setAllowCredentials(true);
        configuration.setAllowedHeaders(ImmutableList.of("Authorization", "Cache-Control", "Content-Type", "*"));

        final UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        source.registerCorsConfiguration("/**", configuration);

        return source;
    }
}
