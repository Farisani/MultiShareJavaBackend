package za.co.multishare.web.config.security;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

@Configuration
@ConfigurationProperties(prefix = "jwt")
@Data
public class JwtProperties {

	private String secretKey = "afuckingsecretkey";

	//validity in milliseconds
	private long validityInMs = 3600000; // 1h
}
