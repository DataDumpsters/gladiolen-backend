package Project40.gladiolen_backend.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;

import lombok.Builder;
import lombok.Getter;
import lombok.extern.jackson.Jacksonized;

@Getter
@Builder
@Jacksonized
public class LoginRequestDto {

  @Email
  @NotBlank
  private final String emailId;

  @NotBlank
  private final String password;
}
