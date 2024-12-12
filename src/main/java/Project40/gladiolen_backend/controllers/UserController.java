package Project40.gladiolen_backend.controllers;

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
import java.util.Optional;
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

    @GetMapping(value = "/user/all", produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseStatus(value = HttpStatus.OK)
    @Operation(summary = "Returns all user account details")
    public List<User> allUserDetailsRetrievalHandler() {
        return userService.getAllUsers();
    }

    @DeleteMapping(value = "/user", produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseStatus(value = HttpStatus.OK)
    @Operation(summary = "Deletes a user account")
    public ResponseEntity<?> userAccountDeletionHandler(
            @Parameter(hidden = true)
            @RequestHeader(required = true, name = "Authorization") final String header) {

        return userService.deleteAccount(jwtUtils.extractUserId(header));
    }

    @PostMapping(value = "/admin/user", produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseStatus(HttpStatus.CREATED)
    @Operation(summary = "Creates a new user account")
    public ResponseEntity<?> createAccount(@RequestBody User user) {
        userService.createUser(user);
        return ResponseEntity.status(HttpStatus.CREATED).build();
    }

    @PostMapping(value= "/create-user", produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseStatus(HttpStatus.CREATED)
    @Operation(summary = "Creates a new user account")
    public ResponseEntity<?> createUser(@RequestBody User user) {
        userService.createUser(user);
        return ResponseEntity.status(HttpStatus.CREATED).build();
    }

//    @PostMapping(value = "/user", produces = MediaType.APPLICATION_JSON_VALUE)
//    @ResponseStatus(HttpStatus.CREATED)
//    @Operation(summary = "Creates a new user account based on role")
//    public ResponseEntity<?> createUserBasedOnRole(@RequestBody User user) {
//        if (user.getRole() == Role.Lid) {
//            userService.createUser(user);
//        } else {
//            userService.createAccount(user);
//        }
//        return ResponseEntity.status(HttpStatus.CREATED).build();
//    }

    @PutMapping(value = "/admin/user/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseStatus(HttpStatus.OK)
    @Operation(summary = "Updates a user account by ID")
    public ResponseEntity<?> updateUser(@PathVariable Long id, @RequestBody User user) {
        userService.updateUser(id, user);
        return ResponseEntity.ok().build();
    }

    @DeleteMapping(value = "/admin/user/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseStatus(HttpStatus.OK)
    @Operation(summary = "Deletes a user account by ID")
    public ResponseEntity<?> deleteUser(@PathVariable Long id) {
        userService.deleteUser(id);
        return ResponseEntity.ok().build();
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
    @GetMapping("/user/check-email/")
    @ResponseStatus(HttpStatus.OK)
    public Optional<User> getUserByEmail(@RequestParam String email) {
        return userService.getUserByEmail(email);
    }

    @GetMapping("/user/{id}")
    @ResponseStatus(HttpStatus.OK)
    public User getUserById(@PathVariable Long id) {
        return userService.getUserById(id);
    }
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
    @GetMapping("/roles")
    @ResponseStatus(HttpStatus.OK)
    public List<String> getRoles() {
        return Stream.of(Role.values())
                .map(Role::name)
                .collect(Collectors.toList());
    }
//    Email validation check
//@GetMapping("/check-email")
//public boolean checkEmailExists(@RequestParam String email) {
//    return userService.getUserByEmail(email) != null;
//}

}