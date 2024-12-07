package Project40.gladiolen_backend.security;

import java.util.UUID;

import Project40.gladiolen_backend.repositories.UserRepository;
import org.springframework.context.annotation.Bean;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import Project40.gladiolen_backend.repositories.UserRepository;
import Project40.gladiolen_backend.security.utility.SecurityUtils;

import lombok.AllArgsConstructor;

@Service
@AllArgsConstructor
public class CustomUserDetailService implements UserDetailsService {

  private final UserRepository userRepository;

  @Override
  public UserDetails loadUserByUsername(String userId) throws UsernameNotFoundException {
    return SecurityUtils.convert(userRepository.findById(Long.valueOf(userId))
      .orElseThrow(() -> new UsernameNotFoundException("Bad Credentials")));
  }

  @Bean
  public PasswordEncoder passwordEncoder() {
    return new BCryptPasswordEncoder();
  }
}