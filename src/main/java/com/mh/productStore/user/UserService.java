package com.mh.productStore.user;


import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;




@Service
public class UserService implements UserDetailsService {
    private final UserRepository repository;
    private final PasswordEncoder encoder;
    private static final Logger log = LoggerFactory.getLogger(UserService.class);

    public UserService(UserRepository repository, PasswordEncoder encoder) {
        this.repository = repository;
        this.encoder = encoder;
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        AppUser user = repository.findByUsername(username).orElseThrow(() ->{
                log.warn("User {} not found", username);
                return new UsernameNotFoundException("User not found with username: " + username);
        });

        return User.builder().username(user.getUsername()).password(user.getPassword()).roles(user.getRole()).build();
    }

    public void register(RegisterRequest registerRequest) {
        log.debug("Register request received: {}", registerRequest.getUsername());
        AppUser user = new AppUser();
        user.setUsername(registerRequest.getUsername());
        user.setPassword(encoder.encode(registerRequest.getPassword()));
        user.setRole("USER");
        repository.save(user);
        log.info("User {} registered successfully", user.getUsername());
    }


}
