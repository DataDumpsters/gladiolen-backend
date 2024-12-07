package Project40.gladiolen_backend.mail.properties;

import org.springframework.boot.context.properties.ConfigurationProperties;

import lombok.Data;

@Data
@ConfigurationProperties(prefix = "project40.gladiolen-backend.app")
public class OneTimePasswordConfigurationProperties {

  private OTP otp = new OTP();

  @Data
  public class OTP {

    private Integer expirationMinutes;
  }
}