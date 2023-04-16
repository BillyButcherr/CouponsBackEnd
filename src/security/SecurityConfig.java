package com.webApp.security;

import com.nimbusds.jose.JOSEException;
import com.nimbusds.jose.jwk.JWK;
import com.nimbusds.jose.jwk.JWKSet;
import com.nimbusds.jose.jwk.RSAKey;
import com.nimbusds.jose.jwk.gen.RSAKeyGenerator;
import com.nimbusds.jose.jwk.source.ImmutableJWKSet;
import com.nimbusds.jose.jwk.source.JWKSource;
import com.nimbusds.jose.proc.JWKSecurityContext;
import com.nimbusds.jose.proc.SecurityContext;
import com.webApp.services.JpaUserDetailsService;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.ProviderManager;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.oauth2.server.resource.OAuth2ResourceServerConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.oauth2.jwt.JwtDecoder;
import org.springframework.security.oauth2.jwt.JwtEncoder;
import org.springframework.security.oauth2.jwt.NimbusJwtDecoder;
import org.springframework.security.oauth2.jwt.NimbusJwtEncoder;
import org.springframework.security.oauth2.server.resource.authentication.JwtAuthenticationConverter;
import org.springframework.security.oauth2.server.resource.authentication.JwtGrantedAuthoritiesConverter;
import org.springframework.security.oauth2.server.resource.web.BearerTokenAuthenticationEntryPoint;
import org.springframework.security.oauth2.server.resource.web.access.BearerTokenAccessDeniedHandler;
import org.springframework.security.web.SecurityFilterChain;

import java.security.KeyPair;
import java.security.KeyPairGenerator;

@Configuration
@EnableWebSecurity
@EnableMethodSecurity
public class SecurityConfig {

    private RSAKey rsaKey;
    private final JpaUserDetailsService jpaUserDetailsService;

    public SecurityConfig(JpaUserDetailsService jpaUserDetailsService) throws JOSEException {
        this.jpaUserDetailsService = jpaUserDetailsService;
        RSAKeyGenerator rsaKeyGenerator = new RSAKeyGenerator(2048);
        rsaKey = rsaKeyGenerator.generate();
    }
    private static final String VIEWER_WHITELIST[] = {
            "/home/**", "/css/*", "/js/*",
            "/api/login/*", "/api/public/**",
            "/login/**", "/signup", "/token"
    };
    private static final String ADMIN_WHITELIST[] = {
            "/admin/**", "/api/admin/**"
    };
    private static final String COMPANY_WHITELIST[] = {
            "/companies/**", "/api/companies/**"
    };
    private static final String CUSTOMER_WHITELIST[] = {
            "/customers/**", "/api/customers/**"
    };

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception{
        return http
                .cors().and()
                .csrf().disable()
                .authorizeHttpRequests(auth -> auth

                        .mvcMatchers(VIEWER_WHITELIST).permitAll()

                        .mvcMatchers(ADMIN_WHITELIST).hasAuthority("ROLE_ADMIN")
                        .mvcMatchers(COMPANY_WHITELIST).hasAuthority("ROLE_COMPANY")
                        .mvcMatchers(CUSTOMER_WHITELIST).hasAuthority("ROLE_CUSTOMER")

                        .anyRequest().authenticated()

                )
                .sessionManagement(session -> session.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
                .oauth2ResourceServer(OAuth2ResourceServerConfigurer::jwt)
                .exceptionHandling((ex) -> ex
                        .authenticationEntryPoint(new BearerTokenAuthenticationEntryPoint())
                        .accessDeniedHandler(new BearerTokenAccessDeniedHandler())
                )
//                .userDetailsService(jpaUserDetailsService)
//                .httpBasic(Customizer.withDefaults())
                .build();
    }
    @Bean
    AuthenticationManager authenticationManager(UserDetailsService userDetailsService){
        DaoAuthenticationProvider daoAuthenticationProvider = new DaoAuthenticationProvider();
        daoAuthenticationProvider.setUserDetailsService(jpaUserDetailsService);
        return new ProviderManager(daoAuthenticationProvider);
    }

    @Bean
    public PasswordEncoder passwordEncoder(){
        return new BCryptPasswordEncoder(10);
    }
    @Bean
    JwtEncoder jwtEncoder() throws JOSEException {
        JWK jwk = new RSAKey.Builder(rsaKey.toRSAPublicKey()).privateKey(rsaKey.toPrivateKey()).build();
        JWKSource<SecurityContext> jwks = new ImmutableJWKSet<>(new JWKSet(jwk));
        return new NimbusJwtEncoder(jwks);
    }
    @Bean
    JwtDecoder jwtDecoder() throws JOSEException {
        return NimbusJwtDecoder.withPublicKey(rsaKey.toRSAPublicKey()).build();
    }
    @Bean
    JwtAuthenticationConverter jwtAuthenticationConverter(){
        final JwtGrantedAuthoritiesConverter grantedAuthoritiesConverter = new JwtGrantedAuthoritiesConverter();
        grantedAuthoritiesConverter.setAuthorityPrefix("");

        final JwtAuthenticationConverter authenticationConverter = new JwtAuthenticationConverter();
        authenticationConverter.setJwtGrantedAuthoritiesConverter(grantedAuthoritiesConverter);
        return authenticationConverter;
    }
}
