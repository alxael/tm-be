package tmbe.service;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import tmbe.exception.ResourceNotFoundException;
import tmbe.model.User;
import tmbe.repository.UserRepository;
import tmbe.security.jwt.JwtUtilities;

import java.util.List;
import java.util.Optional;

@Slf4j
@Service
public class UserServiceImplementation implements UserService {
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private JwtUtilities jwtUtilities;

    @Override
    public User readUserData(String token) {
        String username = jwtUtilities.getUserNameFromJwtToken(token);
        Optional<User> user = userRepository.findByUsername(username);
        if (user.isPresent()) {
            return user.get();
        } else {
            throw new ResourceNotFoundException("User with username not found.");
        }
    }

    @Override
    public List<User> readAllUserData() {
        return userRepository.findAll();
    }

    @Override
    public void deleteUser(String token) {
        String username = jwtUtilities.getUserNameFromJwtToken(token);
        Optional<User> user = userRepository.findByUsername(username);
        if (user.isPresent()) {
            userRepository.delete(user.get());
        } else {
            throw new ResourceNotFoundException("User with username not found.");
        }
    }
}