package Project40.gladiolen_backend.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import lombok.Builder;
import lombok.Getter;
import lombok.extern.jackson.Jacksonized;

@Getter
@Builder
@Jacksonized
public class SignupRequestDto {

  @Email
  @NotBlank
  private final String emailId;

  @NotBlank
  private final String password;

  @NotBlank
  private final String firstName;

  @NotBlank
  private final String lastName;

  @NotBlank
  private final String phoneNumber;

  @NotBlank
  private final String registryNumber;

  @NotBlank
  private final String role;
}