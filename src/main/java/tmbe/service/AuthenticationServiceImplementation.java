package tmbe.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import tmbe.dto.UserForSignInDto;
import tmbe.dto.UserForSignUpDto;
import tmbe.exception.ResourceExistsException;
import tmbe.model.Role;
import tmbe.model.User;
import tmbe.repository.RoleRepository;
import tmbe.repository.UserRepository;
import tmbe.security.jwt.JwtUtilities;

import java.util.HashSet;
import java.util.Set;

@Service
public class AuthenticationServiceImplementation implements AuthenticationService {
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private RoleRepository roleRepository;
    @Autowired
    AuthenticationManager authenticationManager;
    @Autowired
    JwtUtilities jwtUtilities;
    @Autowired
    PasswordEncoder encoder;

    public String signInUser(UserForSignInDto userForSignInDto) {
        Authentication authentication = authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(userForSignInDto.getUsername(), userForSignInDto.getPassword()));

        SecurityContextHolder.getContext().setAuthentication(authentication);
        return jwtUtilities.generateJwtToken(authentication);
    }

    public String signUpUser(UserForSignUpDto userForSignUpDto) {
        if (userRepository.existsByUsername(userForSignUpDto.getUsername())) {
            throw new ResourceExistsException("User with username already exists.");
        }

        if (userRepository.existsByEmail(userForSignUpDto.getEmail())) {
            throw new ResourceExistsException("User with email already exists.");
        }

        Set<Role> roles = new HashSet<>();
        Role userRole = roleRepository.findByName("USER").orElseThrow(() -> new RuntimeException("Error: Role is not found."));
        roles.add(userRole);
        User user = User.builder().username(userForSignUpDto.getUsername()).email(userForSignUpDto.getEmail()).password(encoder.encode(userForSignUpDto.getPassword())).roles(roles).build();

        userRepository.save(user);

        UserForSignInDto userForSignInDto = UserForSignInDto.builder().username(user.getUsername()).password(userForSignUpDto.getPassword()).build();
        return signInUser(userForSignInDto);
    }
}
