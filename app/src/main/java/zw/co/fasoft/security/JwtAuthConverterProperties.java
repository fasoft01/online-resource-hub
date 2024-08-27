package zw.co.fasoft.security;



import lombok.Data;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;
import org.springframework.validation.annotation.Validated;

@Data
@Validated
@Configuration
public class JwtAuthConverterProperties {
    @Value("InstalipaGateway")
    private String resourceId;
    @Value("preferred_username")
    private String principalAttribute;
}








