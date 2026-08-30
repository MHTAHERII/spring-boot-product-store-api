package com.mh.productStore.config;



import com.mh.productStore.security.JwtFilter;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.security.config.http.SessionCreationPolicy;
@Configuration
public class SecurityConfig {
    private final JwtFilter jwtFilter;
    public SecurityConfig(JwtFilter jwtFilter) {
        this.jwtFilter = jwtFilter;
    }

    @Bean
    public SecurityFilterChain securityFilterChain (HttpSecurity http) throws Exception {
        return http.csrf(csrf -> csrf.disable()).authorizeHttpRequests(auth -> auth.requestMatchers("/hello","/about","/token","/register","/swagger-ui/**","/v3/api-docs/**","/actuator/**").permitAll()
                .requestMatchers(HttpMethod.GET,"/products/**").hasAnyRole("USER","ADMIN")
                .requestMatchers(HttpMethod.POST,"/products").hasRole("ADMIN")
                .requestMatchers(HttpMethod.PUT,"/products").hasRole("ADMIN")
                .anyRequest().authenticated()).addFilterBefore(jwtFilter, UsernamePasswordAuthenticationFilter.class)
                .sessionManagement(session->session.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
                .build();

    }

//    @Bean
//    public UserDetailsService userDetailsService() {
//        UserDetails admin = User.withUsername("admin").password("{noop}1234").roles("ADMIN").build();
//        UserDetails user = User.withUsername("user").password("{noop}1234").roles("USER").build();
//        return new InMemoryUserDetailsManager(admin,user);
//    }

    @Bean
    public AuthenticationManager authenticationManager(AuthenticationConfiguration config
    ) throws Exception {
        return config.getAuthenticationManager();
    }
    //به اسپرینگ دستور میدیم که شی احراز هویت پیشفرض خودتو بساز و به عنوان bean داخل کانتینر قرار بده تا بتوانم اون رو تزریق کنم


}
