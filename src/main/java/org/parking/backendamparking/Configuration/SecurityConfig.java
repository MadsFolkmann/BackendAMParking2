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
                        /* Public User endpoints */
                        .requestMatchers(antMatcher("/user")).permitAll()
                        .requestMatchers("/user/**").permitAll()
                        .requestMatchers(antMatcher("/user/update/**")).permitAll()
                        .requestMatchers(antMatcher("/user/delete/**")).permitAll()
                        .requestMatchers(antMatcher("/user/lejemaal/**")).permitAll()
                        .requestMatchers(antMatcher("/user/login")).permitAll()
                        .requestMatchers(antMatcher("/user/{id}")).permitAll()

                        /* Public Parking endpoints */
                        .requestMatchers(antMatcher("/parking")).permitAll()
                        .requestMatchers(antMatcher("/parking/active/user/**")).permitAll()
                        .requestMatchers(antMatcher("/parking/add")).permitAll()
                        .requestMatchers(antMatcher("/parking/update/**")).permitAll()
                        .requestMatchers(antMatcher("/parking/delete/**")).permitAll()
                        .requestMatchers(antMatcher("/parking/plateNumber/**")).permitAll()
                        .requestMatchers(antMatcher("/parking/user/**")).permitAll()
                        .requestMatchers(antMatcher("/parking/{id}")).permitAll()
                        .requestMatchers(antMatcher("/parking/active/plateNumber/**")).permitAll()

                        /* Public Cars endpoints */
                        .requestMatchers(antMatcher("/cars")).permitAll()
                        .requestMatchers(antMatcher("/cars/**")).permitAll()
                        .requestMatchers(antMatcher("/cars/update/**")).permitAll()
                        .requestMatchers(antMatcher("/cars/delete/**")).permitAll()
                        .requestMatchers(antMatcher("/cars/user/**")).permitAll()
                        .requestMatchers(antMatcher("/cars/{plateNumber}")).permitAll()

                        /* Public Cases endpoints */
                        .requestMatchers(antMatcher("/case")).permitAll()
                        .requestMatchers(antMatcher("/case/add")).permitAll()
                        .requestMatchers(antMatcher("/case/update/**")).permitAll()
                        .requestMatchers(antMatcher("/case/delete/**")).permitAll()
                        .requestMatchers(antMatcher("/case/user/**")).permitAll()

                        /* Public PArea endpoints */
                        .requestMatchers(antMatcher("/pArea")).permitAll()
                        .requestMatchers(antMatcher("/pArea/add")).permitAll()
                        .requestMatchers(antMatcher("/pArea/update/**")).permitAll()
                        .requestMatchers(antMatcher("/pArea/delete/**")).permitAll()
                        .requestMatchers(antMatcher("/pArea/{areaName}")).permitAll()
                        .requestMatchers(antMatcher("/pArea/{id}")).permitAll()

                        /* Public RentalUnitEndpoint endpoints */
                        .requestMatchers(antMatcher("/rentalUnit/check/**")).permitAll()


                        /* Swagger (API docs) */
                        .requestMatchers(antMatcher("/v3/api-docs/**")).permitAll()
                        .requestMatchers(antMatcher("/swagger-ui.html")).permitAll()
                        .requestMatchers(antMatcher("/swagger-ui/**")).permitAll()


                        /* All other requests require authentication */
                        .anyRequest().authenticated()
                )
                .httpBasic();

        return http.build();
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }
}
