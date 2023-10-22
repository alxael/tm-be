package tmbe.controller;

import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import tmbe.model.User;
import tmbe.service.UserServiceImplementation;

import java.util.List;
import java.util.Map;

@Slf4j
@RestController
@RequestMapping("/api/user")
@Tag(name = "User")
@SecurityRequirement(name = "Authentication")
public class UserController {
    @Autowired
    private UserServiceImplementation userService;

    @GetMapping
    public ResponseEntity<User> readUserData(@RequestHeader Map<String, String> headers) {
        String token = headers.get("authorization");
        return new ResponseEntity<>(userService.readUserData(token), HttpStatus.OK);
    }

    @GetMapping("all")
    @PreAuthorize("hasAuthority('ADMIN')")
    @Tag(name = "Admin")
    public ResponseEntity<List<User>> readAllUserData() {
        return new ResponseEntity<>(userService.readAllUserData(), HttpStatus.OK);
    }

    @DeleteMapping
    public ResponseEntity<String> deleteUser(@RequestHeader Map<String, String> headers) {
        String token = headers.get("authorization");
        userService.deleteUser(token);
        return new ResponseEntity<>(HttpStatus.OK);
    }
}
