package za.co.multishare.web.config;

import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;

@Configuration
@ComponentScan({"za.co.multishare.web.endpoint", "za.co.multishare.business.config"})
public class WebConfig {
}
