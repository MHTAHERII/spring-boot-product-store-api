package com.mh.productStore.config;



import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
public class SecurityConfig {

    @Bean
    public SecurityFilterChain securityFilterChain (HttpSecurity http) throws Exception {
        return http.csrf(csrf -> csrf.disable()).authorizeHttpRequests(auth -> auth.requestMatchers("/hello","/about","/token","/register").permitAll()
                .requestMatchers(HttpMethod.GET,"/products/**","/token").hasAnyRole("USER","ADMIN")
                .requestMatchers(HttpMethod.POST,"/products").hasRole("ADMIN")
                .requestMatchers(HttpMethod.PUT,"/products").hasRole("ADMIN")

                .anyRequest().authenticated()).httpBasic(Customizer.withDefaults()).build();
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

    @Bean
    public PasswordEncoder passwordEncoder() {
        PasswordEncoder encoder = new BCryptPasswordEncoder();

        System.out.println(encoder.encode("1234"));

        return encoder;
    }

}
