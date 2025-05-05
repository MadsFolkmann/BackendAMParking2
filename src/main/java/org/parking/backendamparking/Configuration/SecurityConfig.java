package org.parking.backendamparking.Configuration;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import static org.springframework.security.web.util.matcher.AntPathRequestMatcher.antMatcher;

@Configuration
@EnableWebSecurity
public class SecurityConfig {

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
                .csrf(csrf -> csrf.disable())
                .authorizeHttpRequests(auth -> auth
                        /* User */
                        .requestMatchers(antMatcher("/user")).permitAll()
                        .requestMatchers(antMatcher("/user/add")).permitAll()
                        .requestMatchers(antMatcher("/user/update/**")).permitAll()
                        .requestMatchers(antMatcher("/user/delete/**")).permitAll()
                        .requestMatchers(antMatcher("/user/lejemaal/**")).permitAll()
                        .requestMatchers(antMatcher("/user/login")).permitAll()
                        .requestMatchers(antMatcher("/user/{id}")).permitAll()
                        .requestMatchers(antMatcher("/user")).permitAll()


                        /* Parking */

                        .requestMatchers(antMatcher("/parking")).permitAll()
                        .requestMatchers(antMatcher("/parking/add")).permitAll()
                        .requestMatchers(antMatcher("/parking/update/**")).permitAll()
                        .requestMatchers(antMatcher("/parking/delete/**")).permitAll()
                        .requestMatchers(antMatcher("/parking/plateNumber/**")).permitAll()
                        .requestMatchers(antMatcher("/parking/user/**")).permitAll()
                        .requestMatchers(antMatcher("/parking/{id}")).permitAll()


                        /* Cars */
                        .requestMatchers(antMatcher("/cars")).permitAll()
                        .requestMatchers("/cars/**").permitAll()
                        .requestMatchers(antMatcher("/cars/update/**")).permitAll()
                        .requestMatchers(antMatcher("/cars/delete/**")).permitAll()
                        .requestMatchers(antMatcher("/cars/user/**")).permitAll()
                        .requestMatchers(antMatcher("/cars/{plateNumber}")).permitAll()


                        /* Tillad Swagger */
                        .requestMatchers(antMatcher("/v3/api-docs/**")).permitAll()
                        .requestMatchers(antMatcher("/swagger-ui.html")).permitAll()
                        .requestMatchers(antMatcher("/swagger-ui/**")).permitAll()
                        .anyRequest().authenticated()


                )
                .formLogin(form -> form.permitAll())
                .httpBasic();
        return http.build();
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }
}
