package com.devhub.api.infra.security;

import com.devhub.api.domain.usuario.UserRole;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;

@Configuration
@EnableWebSecurity
public class SecurityConfiguration {

    @Autowired
    private SecurityFilter securityFilter;

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        return http.csrf(csrf -> csrf.disable())
                .cors(Customizer.withDefaults())
                .sessionManagement(sm -> sm.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
                .authorizeHttpRequests(req -> {
                    //LOGIN
                    req.requestMatchers(HttpMethod.POST, "/login").permitAll();
                    //CADASTRO
                    req.requestMatchers(HttpMethod.POST, "/freelancers").permitAll();
                    req.requestMatchers(HttpMethod.POST, "/freelancers/**").permitAll();
                    req.requestMatchers(HttpMethod.POST, "/contratantes").permitAll();
                    //ENCERRAMENTO DE SERVICO
                    req.requestMatchers(HttpMethod.POST, "/servicos").hasRole("CONTRATANTE");
                    req.requestMatchers("/servicos/**").permitAll();
                    //SWAGGER
                    req.requestMatchers(HttpMethod.GET, "/swagger-ui/**").permitAll();
                    req.requestMatchers("/v3/api-docs/**").permitAll();
                    req.requestMatchers("/swagger-resources/**").permitAll();

                    req.requestMatchers("/h2").permitAll();
                    //SERVIÇO
                    req.requestMatchers("/avaliacoes-freelancer/**").hasRole("CONTRATANTE");
                    req.requestMatchers("/h2-console").permitAll();

                    req.anyRequest().authenticated();
                })
                .addFilterBefore(securityFilter, UsernamePasswordAuthenticationFilter.class)
                .build();
    }

    @Bean
    public AuthenticationManager authenticationManager(AuthenticationConfiguration authenticationConfiguration) throws Exception{
        return authenticationConfiguration.getAuthenticationManager();
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }
}
