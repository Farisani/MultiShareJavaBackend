package za.co.multishare.business.config;

import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;

@Configuration
@ComponentScan({"za.co.multishare.repository.config", "za.co.multishare.business.service"})
public class BusinessConfig {
}
