package za.co.multishare.web.config.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import za.co.multishare.web.config.filter.CorsFilter;
import za.co.multishare.web.security.service.JwtTokenProvider;

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
                .and().
                cors()
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
}

