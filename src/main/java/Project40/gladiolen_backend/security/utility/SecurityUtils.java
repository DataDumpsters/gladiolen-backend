package Project40.gladiolen_backend.security.utility;

import java.util.List;

import org.springframework.security.core.userdetails.User;

import lombok.experimental.UtilityClass;

@UtilityClass
public class SecurityUtils {

  public User convert(Project40.gladiolen_backend.models.User user) {
    return new User(user.getEmail(), user.getPassword(), List.of());
  }
}