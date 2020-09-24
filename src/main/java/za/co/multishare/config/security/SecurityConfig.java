package za.co.multishare.config.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import za.co.multishare.service.JwtTokenProvider;

import java.util.Collections;

@Configuration
public class SecurityConfig extends WebSecurityConfigurerAdapter {

    @Autowired
    JwtTokenProvider jwtTokenProvider;

    @Bean
    @Override
    public AuthenticationManager authenticationManagerBean() throws Exception {
        return super.authenticationManagerBean();
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http.httpBasic()
                .and()
                .cors().configurationSource(corsConfigurationSource())
                .and()
                .authorizeRequests()
                .antMatchers(HttpMethod.POST, "/api/auth/sign-in").permitAll()
                .antMatchers(HttpMethod.POST, "/api/registration/**").permitAll()
                .antMatchers("/api/feed/**").hasAnyRole("USER", "ADMIN")
                .antMatchers("/api/friends/**").hasAnyRole("USER", "ADMIN")
                .antMatchers("/api/post/**").hasAnyRole("USER", "ADMIN")
                .antMatchers("/api/user-info/**").hasAnyRole("USER", "ADMIN")
                .and()
                .csrf().disable()
                .formLogin().disable()
                .apply(new JwtSecurityConfigurer(jwtTokenProvider));
    }

    private CorsConfigurationSource corsConfigurationSource() {
        CorsConfigurationSource corsConfigurationSource = httpServletRequest -> {
            CorsConfiguration config = new CorsConfiguration();
            config.setAllowedHeaders(Collections.singletonList("*"));
            config.setAllowedMethods(Collections.singletonList("*"));
            config.addAllowedOrigin("*");
            config.setAllowCredentials(true);
            return config;
        };

        return corsConfigurationSource;
    }
}

