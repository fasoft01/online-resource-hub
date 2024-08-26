//package zw.co.fasoft.security;
//
//import lombok.extern.slf4j.Slf4j;
//import org.springframework.core.convert.converter.Converter;
//import org.springframework.security.authentication.AbstractAuthenticationToken;
//import org.springframework.security.core.GrantedAuthority;
//import org.springframework.security.core.authority.SimpleGrantedAuthority;
//import org.springframework.security.oauth2.jwt.Jwt;
//import org.springframework.security.oauth2.jwt.JwtClaimNames;
//import org.springframework.security.oauth2.server.resource.authentication.JwtAuthenticationToken;
//import org.springframework.security.oauth2.server.resource.authentication.JwtGrantedAuthoritiesConverter;
//import org.springframework.stereotype.Component;
//
//import java.util.Collection;
//import java.util.Map;
//import java.util.Objects;
//import java.util.Set;
//import java.util.stream.Collectors;
//import java.util.stream.Stream;
//
//@Component
//@Slf4j
//public class JwtAuthConverter implements Converter<Jwt, AbstractAuthenticationToken> {
//
//    private final JwtGrantedAuthoritiesConverter jwtGrantedAuthoritiesConverter = new JwtGrantedAuthoritiesConverter();
//
//
//    private final String principalAttribute = "preferred_username";
//    private final String resourceId = "InstalipaGateway";
//
//    @Override
//
//    public AbstractAuthenticationToken convert(Jwt jwt) {
//        Collection<GrantedAuthority> authorities = Stream.concat(
//                jwtGrantedAuthoritiesConverter.convert(jwt).stream(),
//                extractResourceRoles(jwt).stream()).collect(Collectors.toSet());
//        return new JwtAuthenticationToken(jwt, authorities, getPrincipalClaimName(jwt));
//
//    }
//
//    private String getPrincipalClaimName(Jwt jwt) {
//        String claimName = JwtClaimNames.SUB;
//        if (principalAttribute != null) {
//            claimName = principalAttribute;
//        }
//        return jwt.getClaim(claimName);
//    }
//
//    private Collection<? extends GrantedAuthority> extractResourceRoles(Jwt jwt) {
//        Map<String, Object> resourceAccess = jwt.getClaim("resource_access");
//        Map<String, Object> resource;
//        Collection<String> resourceRoles;
//        if (Objects.isNull(resourceAccess)
//                || (resource = (Map<String, Object>) resourceAccess.get(resourceId)) == null
//                || (resourceRoles = (Collection<String>) resource.get("roles")) == null) {
//            return Set.of();
//        }
//        log.info("roles : {}", resourceRoles);
//        return resourceRoles.stream()
//                .map(role -> new SimpleGrantedAuthority("ROLE_" + role))
//                .collect(Collectors.toSet());
//    }
//
//}
