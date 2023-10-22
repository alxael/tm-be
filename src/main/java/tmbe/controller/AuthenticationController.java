package tmbe.controller;

import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import tmbe.dto.UserForSignInDto;
import tmbe.dto.UserForSignUpDto;
import tmbe.service.AuthenticationServiceImplementation;

@RestController
@RequestMapping("/api/auth")
@Tag(name = "Authentication")
public class AuthenticationController {
    @Autowired
    private AuthenticationServiceImplementation authenticationService;

    @PostMapping("/signin")
    public ResponseEntity<String> authenticateUser(@Valid @RequestBody UserForSignInDto userForSignInDto) {
        String jwt = authenticationService.signInUser(userForSignInDto);
        return new ResponseEntity<>(jwt, HttpStatus.OK);
    }

    @PostMapping("/signup")
    public ResponseEntity<String> createUser(@Valid @RequestBody UserForSignUpDto userForSignUpDto) {
        String jwt = authenticationService.signUpUser(userForSignUpDto);
        return new ResponseEntity<>(jwt, HttpStatus.OK);
    }
}
