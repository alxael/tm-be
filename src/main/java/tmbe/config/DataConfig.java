package tmbe.config;

import lombok.Data;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;
import tmbe.model.Role;
import tmbe.model.User;
import tmbe.repository.RoleRepository;
import tmbe.repository.UserRepository;

import java.util.HashSet;
import java.util.Set;
import java.util.UUID;

@Data
@Component
public class DataConfig implements ApplicationRunner {
    @Autowired
    UserRepository userRepository;
    @Autowired
    RoleRepository roleRepository;
    @Autowired
    PasswordEncoder encoder;

    public void run(ApplicationArguments args) {
        if (!roleRepository.existsByName("USER")) {
            roleRepository.save(new Role(UUID.randomUUID(), "USER"));
        }
        if (!roleRepository.existsByName("ADMIN")) {
            roleRepository.save(new Role(UUID.randomUUID(), "ADMIN"));
        }

        if (!roleRepository.findAll().isEmpty() && !userRepository.existsByUsername("admin")) {
            Set<Role> roles = new HashSet<>(roleRepository.findAll());
            User adminUser = User.builder()
                    .id(UUID.randomUUID())
                    .username("admin")
                    .email("admin@email.com")
                    .password(encoder.encode("password123"))
                    .roles(roles)
                    .build();
            userRepository.save(adminUser);
        }
    }
}
