package zw.co.fasoft;

import org.apache.tomcat.util.net.openssl.ciphers.Authentication;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.data.domain.AuditorAware;
import org.springframework.web.client.RestTemplate;

import java.util.Optional;

@SpringBootApplication
public class OnlineResourceHubApplication {

    public static void main(String[] args) {
        SpringApplication.run(OnlineResourceHubApplication.class, args);
    }

    @Bean
    public RestTemplate restTemplate(RestTemplateBuilder builder) {
        return builder.build();
    }

//    @Bean
//    public AuditorAware<String> auditorProvider() {
//        return () -> {
//            Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
//            if (authentication == null || !authentication.isAuthenticated()) {
//                return Optional.empty();
//            }
//
//            Object principal = authentication.getPrincipal();
//            if (principal instanceof UserDetails) {
//                return Optional.of(((UserDetails) principal).getUsername());
//            } else if (principal instanceof Jwt) {
//                Jwt jwt = (Jwt) principal;
//
//                return Optional.of(jwt.getClaimAsString("preferred_username"));
//            } else if (principal instanceof String) {
//                return Optional.of((String) principal);
//            } else {
//                return Optional.empty();
//            }
//        };
//    }
}
