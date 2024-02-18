package ecom.market.inventory.service.security;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;

import java.util.Arrays;

@Configuration
@EnableWebSecurity
@EnableMethodSecurity(prePostEnabled = true)
public class SecurityConfig {

    private final JwtAuthConverter jwtAuthConverter;

    public SecurityConfig(JwtAuthConverter jwtAuthConverter) {
        this.jwtAuthConverter = jwtAuthConverter;
    }

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity httpSecurity) throws Exception {
        return httpSecurity
                .csrf(csrf->csrf.disable())
                .cors(Customizer.withDefaults())
                .headers(header->header.frameOptions(fo->fo.disable()))
                .authorizeHttpRequests(authReq->
                        authReq.requestMatchers("/swagger-ui/**","/v3/**", "/h2-console/**")
                        .permitAll())
                .authorizeHttpRequests(authReq->authReq.anyRequest().authenticated())
                // ça veut dire a chaque fois j'envoie une requete vers inventory service il faut tjr
                // envoyer un jwt (access_token) dans le header Authorization
                // et pour vérifier la signature notre ms a besoin d'un public key,
                // il faut indiquer from were to get the public key

                // .oauth2ResourceServer(o2rs->o2rs.jwt(Customizer.withDefaults()))

                // pour indiquer quand tu récupère le jwt, utilise ce converter, c'est lui qui va montrer
                // comment récuperer les authorities
                .oauth2ResourceServer(o2rs->o2rs.jwt(jwt->jwt.jwtAuthenticationConverter(jwtAuthConverter)))
                .build();
    }

    @Bean
    CorsConfigurationSource corsConfigurationSource() {
        CorsConfiguration corsConfiguration = new CorsConfiguration();
        corsConfiguration.setAllowedOrigins(Arrays.asList("*"));
        corsConfiguration.setAllowedMethods(Arrays.asList("*"));
        corsConfiguration.setAllowedHeaders(Arrays.asList("*"));
        corsConfiguration.setExposedHeaders(Arrays.asList("*"));

        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        source.registerCorsConfiguration("/**",corsConfiguration);
        return source;
    }
}
