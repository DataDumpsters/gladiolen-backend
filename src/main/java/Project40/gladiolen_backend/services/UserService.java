package Project40.gladiolen_backend.services;

import Project40.gladiolen_backend.constant.OtpContext;
import Project40.gladiolen_backend.dto.*;
import Project40.gladiolen_backend.mail.EmailService;
import Project40.gladiolen_backend.models.*;
import Project40.gladiolen_backend.repositories.UnionRepository;
import Project40.gladiolen_backend.repositories.UserRepository;
import Project40.gladiolen_backend.security.utility.JwtUtils;
import com.google.common.cache.LoadingCache;
import jakarta.annotation.PostConstruct;
import jakarta.transaction.Transactional;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.*;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;

@Slf4j
@Service
@AllArgsConstructor
public class UserService {

    private final UserRepository userRepository;
    private final TshirtService tshirtService;
    private final UnionRepository unionRepository;
    private final PasswordEncoder passwordEncoder;
    private final LoadingCache<String, Integer> oneTimePasswordCache;
    private final EmailService emailService;
    private final JwtUtils jwtUtils;

    @PostConstruct
    public void init() {
        if (userRepository.count() < 2) {
            Tshirt tshirt1 = Tshirt.builder()
                    .size(Size.M)
                    .sex(Sex.M)
                    .job(Job.Medewerker)
                    .quantity(1)
                    .build();
            Tshirt tshirt2 = Tshirt.builder()
                    .size(Size.L)
                    .sex(Sex.V)
                    .job(Job.Medewerker)
                    .quantity(1)
                    .build();

            User user1 = new User();
            user1.setFirstName("Joan");
            user1.setLastName("Doe");
            user1.setPhoneNumber("0123456789");
            user1.setRole(Role.Admin);
            user1.setEmail("joandoe@test.com");
            user1.setPassword(passwordEncoder.encode("password"));
            user1.setRegistryNumber("12345678");
            user1.setTshirt(tshirt1);
            user1.setActive(true);

            User user2 = new User();
            user2.setFirstName("Charel");
            user2.setLastName("Doe");
            user2.setPhoneNumber("0123456789");
            user2.setRole(Role.Lid);
            user2.setEmail("Chareldoe@test.com");
            user2.setPassword(passwordEncoder.encode("password123"));
            user2.setRegistryNumber("12345678");
            user2.setTshirt(tshirt2);
            user2.setActive(true);

            userRepository.save(user1);
            userRepository.save(user2);
        }
    }

    public ResponseEntity<?> createAccount(
            final SignupRequestDto userAccountCreationRequestDto) {
        if (userRepository.existsByEmail(userAccountCreationRequestDto.getEmailId())) {
            throw new ResponseStatusException(HttpStatus.CONFLICT,
                    "User account already exists for provided email-id");
        }

        final var user = new User();
        user.setEmail(userAccountCreationRequestDto.getEmailId());
        user.setPassword(passwordEncoder.encode(userAccountCreationRequestDto.getPassword()));
        user.setEmailVerified(false);
        user.setActive(true);
        final var savedUser = userRepository.save(user);

        sendOtp(savedUser, "Verify your account");
        return ResponseEntity.ok(getOtpSendMessage());
    }

    public ResponseEntity<?> login(final LoginRequestDto userLoginRequestDto) {
        final User user = userRepository.findByEmail(userLoginRequestDto.getEmailId())
                .orElseThrow(
                        () -> new ResponseStatusException(HttpStatus.UNAUTHORIZED, "Invalid login credentials"));

        if (!passwordEncoder.matches(userLoginRequestDto.getPassword(), user.getPassword())) {
            throw new ResponseStatusException(HttpStatus.UNAUTHORIZED, "Invalid login credentials");
        }

        if (!user.isActive()) {
            throw new ResponseStatusException(HttpStatus.UNAUTHORIZED, "Account not active");
        }

        sendOtp(user, "2FA: Request to log in to your account");
        return ResponseEntity.ok(getOtpSendMessage());
    }

    public ResponseEntity<LoginSuccessDto> verifyOtp(
            final OtpVerificationRequestDto otpVerificationRequestDto) {
        User user = userRepository.findByEmail(otpVerificationRequestDto.getEmailId())
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.UNAUTHORIZED, "Invalid email-id"));

        Integer storedOneTimePassword = null;
        try {
            storedOneTimePassword = oneTimePasswordCache.get(user.getEmail());
        } catch (ExecutionException e) {
            log.error("FAILED TO FETCH PAIR FROM OTP CACHE: ", e);
            throw new ResponseStatusException(HttpStatus.NOT_IMPLEMENTED);
        }

        if (storedOneTimePassword.equals(otpVerificationRequestDto.getOneTimePassword())) {
            if (otpVerificationRequestDto.getContext().equals(OtpContext.SIGN_UP)) {
                user.setEmailVerified(true);
                user = userRepository.save(user);
                return ResponseEntity
                        .ok(LoginSuccessDto.builder().accessToken(jwtUtils.generateAccessToken(user))
                                .refreshToken(jwtUtils.generateRefreshToken(user)).build());
            } else if (otpVerificationRequestDto.getContext().equals(OtpContext.LOGIN)) {
                return ResponseEntity
                        .ok(LoginSuccessDto.builder().accessToken(jwtUtils.generateAccessToken(user))
                                .refreshToken(jwtUtils.generateRefreshToken(user)).build());
            } else if (otpVerificationRequestDto.getContext().equals(OtpContext.ACCOUNT_DELETION)) {
                user.setActive(false);
                user = userRepository.save(user);
                return ResponseEntity.ok().build();
            } else if (otpVerificationRequestDto.getContext().equals(OtpContext.RESET_PASSWORD)) {
                return ResponseEntity
                        .ok(LoginSuccessDto.builder().accessToken(jwtUtils.generateAccessToken(user))
                                .refreshToken(jwtUtils.generateRefreshToken(user)).build());
            }
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST);
        } else {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST);
        }
    }

    public ResponseEntity<?> deleteAccount(final long userId) {
        final var user = userRepository.findById(userId).get();
        sendOtp(user, "2FA: Confirm account Deletion");
        return ResponseEntity.ok(getOtpSendMessage());
    }

    public ResponseEntity<?> getDetails(final long userId) {
        final var user = userRepository.findById(userId).get();
        final var response = new HashMap<String, String>();
        response.put("email_id", user.getEmail());
        response.put("created_at", user.getCreatedAt().toString());
        return ResponseEntity.ok(response);
    }

    public ResponseEntity<?> requestPasswordReset(final EmailRequestDto emailRequestDto) {
        final User user = userRepository.findByEmail(emailRequestDto.getEmailId())
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Email not found"));
        sendOtp(user, "Password Reset OTP");
        return ResponseEntity.ok(getOtpSendMessage());
    }

//    public ResponseEntity<?> resetPassword(final ResetPasswordRequestDto resetPasswordRequestDto) {
//        final User user = userRepository.findByEmail(resetPasswordRequestDto.getEmailId())
//                .orElseThrow(
//                        () -> new ResponseStatusException(HttpStatus.UNAUTHORIZED, "Invalid login credentials"));
//        user.setPassword(passwordEncoder.encode(resetPasswordRequestDto.getNewPassword()));
//        userRepository.save(user);
//        final var response = new HashMap<String, String>();
//        response.put("email_id", user.getEmail());
//        response.put("created_at", user.getCreatedAt().toString());
//        return ResponseEntity.ok(response);
//    }

    public ResponseEntity<?> resetPassword(final String header, final ResetPasswordRequestDto resetPasswordRequestDto) {
        String email = jwtUtils.extractEmail(header);
        final User user = userRepository.findByEmail(email)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.UNAUTHORIZED, "Invalid email-id"));
        user.setPassword(passwordEncoder.encode(resetPasswordRequestDto.getNewPassword()));
        userRepository.save(user);
        final var response = new HashMap<String, String>();
        response.put("email_id", user.getEmail());
        response.put("created_at", user.getCreatedAt().toString());
        return ResponseEntity.ok(response);
    }

    private void sendOtp(final User user, final String subject) {
        oneTimePasswordCache.invalidate(user.getEmail());

        final var otp = new Random().ints(1, 100000, 999999).sum();
        oneTimePasswordCache.put(user.getEmail(), otp);
        log.info("OTP sent :: {}", otp);

        CompletableFuture.supplyAsync(() -> {
            emailService.sendEmail(user.getEmail(), subject, "OTP: " + otp);
            return HttpStatus.OK;
        }).thenAccept(status -> {
            System.out.println("Email sent successfully with status: " + status);
        });
    }

    private Map<String, String> getOtpSendMessage() {
        final var response = new HashMap<String, String>();
        response.put("message",
                "OTP sent successfully sent to your registered email-address. verify it using /verify-otp endpoint");
        return response;
    }

    public ResponseEntity<?> refreshToken(final TokenRefreshRequestDto tokenRefreshRequestDto) {
        if (jwtUtils.isTokenExpired(tokenRefreshRequestDto.getRefreshToken())) {
            throw new ResponseStatusException(HttpStatus.UNAUTHORIZED, "Refresh token has expired");
        }
        final var user = userRepository.findByEmail(
                        jwtUtils.extractEmail(tokenRefreshRequestDto.getRefreshToken()))
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.BAD_REQUEST));
        return ResponseEntity.ok(
                LoginSuccessDto.builder().refreshToken(tokenRefreshRequestDto.getRefreshToken())
                        .accessToken(jwtUtils.generateAccessToken(user)).build());
    }

    @Transactional
    public void createUser(User user) {
        if (userRepository.findByEmail(user.getEmail()).isPresent()) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Er bestaat reeds een gebruiker met dit emailadres");
        }
        Tshirt savedTshirt = null;
        if (user.getTshirt() != null) {
            savedTshirt = tshirtService.createTshirt(user.getTshirt());
        }
        Union managedUnion = null;
        if (user.getUnion() != null && user.getUnion().getId() != null) {
            managedUnion = unionRepository.findById(user.getUnion().getId())
                    .orElseThrow(() -> new ResponseStatusException(HttpStatus.BAD_REQUEST, "Union not found"));
        }
        User user1 = User.builder()
                .firstName(user.getFirstName())
                .lastName(user.getLastName())
                .phoneNumber(user.getPhoneNumber())
                .email(user.getEmail())
                .role(user.getRole())
                .registryNumber(user.getRegistryNumber())
                .password(passwordEncoder.encode(user.getPassword()))
                .union(managedUnion)
                .tshirt(savedTshirt)
                .shifts(user.getShifts())
                .isActive(true)
                .build();
        userRepository.save(user1);
    }

    public Optional<User> getUserByEmail(String email) {
        return userRepository.findByEmail(email);
    }

    public User getUserById(Long id) {
        return userRepository.findById(id).orElse(null);
    }

    public List<User> getAllUsers() {
        return userRepository.findAll();
    }

    @Transactional
    public void updateUser(Long id, User user) {
        User user1 = userRepository.findById(id).orElse(null);
        if (user1 != null) {
            if (user1.getTshirt() != null) {
                tshirtService.updateTshirt(user1.getTshirt().getId(), user.getTshirt());
            }
            user1.setFirstName(user.getFirstName());
            user1.setLastName(user.getLastName());
            user1.setPhoneNumber(user.getPhoneNumber());
            user1.setEmail(user.getEmail());
            user1.setRole(user.getRole());
            user1.setRegistryNumber(user.getRegistryNumber());
            user1.setPassword(user.getPassword());
            user1.setUnion(user.getUnion());
            user1.setTshirt(user.getTshirt());
            user1.setShifts(user.getShifts());
            userRepository.save(user1);
        }
    }

    public void deleteUser(Long id) {
        User user = userRepository.findById(id).orElse(null);
        if (user != null) {
            tshirtService.deleteTshirt(user.getTshirt().getId());
        }
        userRepository.deleteById(id);
    }
}