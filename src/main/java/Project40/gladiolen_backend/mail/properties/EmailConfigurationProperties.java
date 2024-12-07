package Project40.gladiolen_backend.mail.properties;

import org.springframework.boot.context.properties.ConfigurationProperties;

import lombok.Data;

@Data
@ConfigurationProperties(prefix = "spring.mail")
public class EmailConfigurationProperties {

	private String username;
}