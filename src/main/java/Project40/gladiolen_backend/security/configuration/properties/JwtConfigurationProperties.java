package Project40.gladiolen_backend.security.configuration.properties;

import org.springframework.boot.context.properties.ConfigurationProperties;

import lombok.Data;

@ConfigurationProperties(prefix = "project40.gladiolen-backend.app")
@Data
public class JwtConfigurationProperties {

  private JWT jwt = new JWT();

  @Data
  public class JWT {

    private String secretKey;
  }
}