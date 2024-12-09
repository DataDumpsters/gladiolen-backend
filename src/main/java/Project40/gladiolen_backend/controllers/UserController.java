package Project40.gladiolen_backend.controllers;

import Project40.gladiolen_backend.dto.ForgotPasswordRequestDto;
import Project40.gladiolen_backend.models.Role;
import Project40.gladiolen_backend.models.User;
import Project40.gladiolen_backend.security.utility.JwtUtils;
import Project40.gladiolen_backend.services.UserService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@RestController
//@RequestMapping("/api/user")
@RequiredArgsConstructor
public class UserController {
    private final UserService userService;
    private final JwtUtils jwtUtils;


    @GetMapping(value = "/user", produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseStatus(value = HttpStatus.OK)
    @Operation(summary = "Returns logged in users account details")
    public ResponseEntity<?> loggedInUserDetailsRetrievalHandler(
            @Parameter(hidden = true)
            @RequestHeader(required = true, name = "Authorization") final String header) {

        return userService.getDetails(jwtUtils.extractUserId(header));
    }

    @DeleteMapping(value = "/user", produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseStatus(value = HttpStatus.OK)
    @Operation(summary = "Deletes a user account")
    public ResponseEntity<?> userAccountDeletionHandler(
            @Parameter(hidden = true)
            @RequestHeader(required = true, name = "Authorization") final String header) {

        return userService.deleteAccount(jwtUtils.extractUserId(header));
    }


    @PostMapping(value = "/forgot-password", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseStatus(HttpStatus.OK)
    @Operation(summary = "Handles forgot password requests")
        public ResponseEntity<?> forgotPasswordHandler(@RequestBody ForgotPasswordRequestDto forgotPasswordDto) {
            return userService.forgotPassword(forgotPasswordDto);
        }

}

//    private final UserService userService;
//
//    @PostMapping
//    @ResponseStatus(HttpStatus.CREATED)
//    public void createUser
//            (@RequestBody User user) {
//        userService.createUser(user);
//    }
//
//    @GetMapping
//    @ResponseStatus(HttpStatus.OK)
//    public User getUserByEmail(@RequestParam String email) {
//        return userService.getUserByEmail(email);
//    }
//
//    @GetMapping("/{id}")
//    @ResponseStatus(HttpStatus.OK)
//    public User getUserById(@PathVariable Long id) {
//        return userService.getUserById(id);
//    }
//
//    @GetMapping("/all")
//    @ResponseStatus(HttpStatus.OK)
//    public List<User> getAllUsers() {
//        return userService.getAllUsers();
//    }
//
//    @PutMapping("/{id}")
//    @ResponseStatus(HttpStatus.OK)
//    public void updateUser
//            (@PathVariable Long id, @RequestBody User user) {
//        userService.updateUser(id, user);
//    }
//
//    @DeleteMapping("/{id}")
//    @ResponseStatus(HttpStatus.OK)
//    public void deleteUser(@PathVariable Long id) {
//        userService.deleteUser(id);
//    }
//
//    @GetMapping("/roles")
//    @ResponseStatus(HttpStatus.OK)
//    public List<String> getRoles() {
//        return Stream.of(Role.values())
//                .map(Role::name)
//                .collect(Collectors.toList());
//    }
//    Email validation check
//@GetMapping("/check-email")
//public boolean checkEmailExists(@RequestParam String email) {
//    return userService.getUserByEmail(email) != null;
//}