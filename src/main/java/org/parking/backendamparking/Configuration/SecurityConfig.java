package org.parking.backendamparking.Configuration;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import static org.springframework.security.web.util.matcher.AntPathRequestMatcher.antMatcher;

@Configuration
@EnableWebSecurity
public class SecurityConfig {

    private final JwtUtil jwtUtil;

    public SecurityConfig(JwtUtil jwtUtil) {
        this.jwtUtil = jwtUtil;
    }

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
                
                .csrf(csrf -> csrf.disable())
                .authorizeHttpRequests(auth -> auth
                        /* Public User endpoints */
                        .requestMatchers(antMatcher("/user")).hasAnyAuthority( "ADMIN", "PVAGT")
                        .requestMatchers(antMatcher("/user/update/**")).hasAnyAuthority("USER", "ADMIN", "PVAGT")
                        .requestMatchers(antMatcher("/user/delete/**")).hasAnyAuthority("USER", "ADMIN", "PVAGT")
                        .requestMatchers(antMatcher("/user/lejemaal/**")).hasAnyAuthority("USER", "ADMIN", "PVAGT")
                        .requestMatchers(antMatcher("/user/login")).permitAll()
                        .requestMatchers(antMatcher("/user/{id}")).hasAnyAuthority("USER", "ADMIN", "PVAGT")

                        /* Parking endpoints */
                        .requestMatchers(antMatcher("/parking")).hasAnyAuthority("USER", "ADMIN")
                        .requestMatchers(antMatcher("/parking/active/user/**")).hasAnyAuthority("USER", "ADMIN")
                        .requestMatchers(antMatcher("/parking/add")).hasAnyAuthority("USER", "ADMIN")
                        .requestMatchers(antMatcher("/parking/delete/**")).hasAnyAuthority("USER", "ADMIN")
                        .requestMatchers(antMatcher("/parking/plateNumber/**")).hasAnyAuthority("PVAGT", "ADMIN")
                        .requestMatchers(antMatcher("/parking/user/**")).hasAnyAuthority("USER", "ADMIN")
                        .requestMatchers(antMatcher("/parking/{id}")).hasAnyAuthority("USER", "ADMIN", "PVAGT")
                        .requestMatchers(antMatcher("/parking/active/plateNumber/**")).hasAnyAuthority("PVAGT", "ADMIN")

                        /* Cars endpoints */
                        .requestMatchers(antMatcher("/cars")).hasAnyAuthority( "ADMIN", "PVAGT")
                        .requestMatchers(antMatcher("/cars/**")).permitAll()
                        .requestMatchers(antMatcher("/cars/update/**")).hasAnyAuthority("USER", "ADMIN", "PVAGT")
                        .requestMatchers(antMatcher("/cars/delete/**")).hasAnyAuthority("USER", "ADMIN", "PVAGT")
                        .requestMatchers(antMatcher("/cars/user/**")).hasAnyAuthority("USER", "ADMIN", "PVAGT")
                        .requestMatchers(antMatcher("/cars/{plateNumber}")).hasAnyAuthority("USER", "ADMIN", "PVAGT")

                        /* Cases endpoints */
                        .requestMatchers(antMatcher("/case")).hasAnyAuthority("ADMIN", "PVAGT")
                        .requestMatchers(antMatcher("/case/add")).hasAnyAuthority("ADMIN", "PVAGT")
                        .requestMatchers(antMatcher("/case/update/**")).hasAnyAuthority("ADMIN", "PVAGT")
                        .requestMatchers(antMatcher("/case/delete/**")).hasAnyAuthority("ADMIN", "PVAGT")
                        .requestMatchers(antMatcher("/case/user/**")).hasAnyAuthority("ADMIN", "PVAGT")

                        /* Public PArea endpoints */
                        .requestMatchers(antMatcher("/pArea")).hasAnyAuthority("ADMIN", "PVAGT", "USER")
                        .requestMatchers(antMatcher("/pArea/add")).hasAnyAuthority("ADMIN", "PVAGT")
                        .requestMatchers(antMatcher("/pArea/update/**")).hasAnyAuthority("ADMIN", "PVAGT")
                        .requestMatchers(antMatcher("/pArea/delete/**")).hasAnyAuthority("ADMIN", "PVAGT")
                        .requestMatchers(antMatcher("/pArea/{areaName}")).hasAnyAuthority("ADMIN", "PVAGT", "USER")
                        .requestMatchers(antMatcher("/pArea/{id}")).hasAnyAuthority("ADMIN", "PVAGT", "USER")

                        /* RentalUnitEndpoint endpoints */
                        .requestMatchers(antMatcher("/rentalUnit/check/**")).permitAll()


                        /* Swagger (API docs) */
                        .requestMatchers(antMatcher("/v3/api-docs/**")).permitAll()
                        .requestMatchers(antMatcher("/swagger-ui.html")).permitAll()
                        .requestMatchers(antMatcher("/swagger-ui/**")).permitAll()


                        /* All other requests require authentication */
                        .anyRequest().authenticated()
                )
                .addFilterBefore(new JwtAuthenticationFilter(jwtUtil), UsernamePasswordAuthenticationFilter.class);

        return http.build();
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }
}
