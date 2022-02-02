package cz.osu.gamingblog.controllers;

import cz.osu.gamingblog.exceptions.EntityDoesNotExistException;
import cz.osu.gamingblog.exceptions.WrongUserCredentialsException;
import cz.osu.gamingblog.requests.LoginRequest;
import cz.osu.gamingblog.requests.RegistrationRequest;
import cz.osu.gamingblog.services.interfaces.IAuthenticationService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/authentication")
@Slf4j
public class AuthenticationController {
    private final IAuthenticationService _authenticationService;

    public AuthenticationController(IAuthenticationService authenticationService) {
        _authenticationService = authenticationService;
    }

    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody LoginRequest loginRequest) {
        log.info("GET /api/authentication/login");

        try {
            var loginResponse = _authenticationService.login(loginRequest);
            return ResponseEntity.accepted().body(loginResponse);
        } catch (EntityDoesNotExistException ignored) {
            return new ResponseEntity<>(
                    "The user '" + loginRequest.getUsername() + "' does not exist.",
                    HttpStatus.NOT_FOUND
            );
        } catch (WrongUserCredentialsException ignored) {
            return new ResponseEntity<>(
                    "Wrong user credentials.",
                    HttpStatus.BAD_REQUEST
            );
        }
    }

    @PostMapping("/register")
    public ResponseEntity<?> register(@RequestBody RegistrationRequest registrationRequest) {
        try {
            _authenticationService.register(registrationRequest);
            return ResponseEntity.accepted().body("The user is registered.");
        } catch (IllegalStateException ex) {
            return ResponseEntity.badRequest().body("The user could not be registered.");
        }
    }
}
