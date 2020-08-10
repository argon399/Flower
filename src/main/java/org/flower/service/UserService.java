package org.flower.service;

import org.flower.repository.UserRepository;
import org.flower.project.team.User;
import org.flower.project.team.UserRole;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.util.Collections;
import java.util.List;
import java.util.Random;
import java.util.UUID;

@Service
public class UserService implements UserDetailsService {
    private UserRepository userRepository;
    private PasswordEncoder passwordEncoder;
    private EmailService emailService;

    @Value("${server.name}")
    private String serverName;

    @Autowired
    public void setEmailService(EmailService emailService) {
        this.emailService = emailService;
    }

    @Autowired
    public void setPasswordEncoder(PasswordEncoder passwordEncoder) {
        this.passwordEncoder = passwordEncoder;
    }

    @Autowired
    public void setUserRepository(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        User user = userRepository.findByUsername(username);

        if (user == null) {
            throw new UsernameNotFoundException("User not found");
        }

        return user;
    }

    public boolean addUser(User user) {
        User userFromDB = userRepository.findByUsername(user.getUsername());

        if(userFromDB != null) {
            return false;
        }

        user.setActive(false);
        user.setRoles(Collections.singleton(UserRole.USER));
        user.setActivationCode(UUID.randomUUID().toString());
        user.setPassword(passwordEncoder.encode(user.getPassword()));

        userRepository.save(user);

        sendActivateMessage(user);

        return true;
    }

    private void sendActivateMessage(User user) {
        if(!StringUtils.isEmpty(user.getEmail())) {
            String message = String.format(
                    "Hello, %s\n" +
                            "Welcome to Flower. Please, visit next link: http://%s/registration/activate/%s",
                    user.getUsername(),
                    serverName,
                    user.getActivationCode()
            );

            emailService.send(user.getEmail(), "Activation code", message);
        }
    }

    public boolean activateUser(String code) {
        User user = userRepository.findByActivationCode(code);

        if(user == null) {
            return false;
        }

        user.setActivationCode(null);
        user.setActive(true);

        userRepository.save(user);

        return true;
    }

    public List<User> findAll() {
        return userRepository.findAll();
    }

    public void saveUser(User user, User userData) {
        user.setUsername(userData.getUsername());
        user.setFio(userData.getFio());
        user.setEmail(userData.getEmail());
        user.setActive(userData.isActive());
        user.setRoles(userData.getRoles());

        userRepository.save(user);
    }

    public void updateProfile(User user, String password, String email, String fio) {
        if(!StringUtils.isEmpty(password)) {
            user.setPassword(passwordEncoder.encode(password));
        }

        if(!StringUtils.isEmpty(email)) {
            user.setEmail(email);
        }
        if(!StringUtils.isEmpty(fio)) {
            user.setFio(fio);
        }

        userRepository.save(user);
    }

    public boolean createNewPassword(String username) {
        User user = userRepository.findByUsername(username);
        if (user == null) {
            return false;
        }

        String newPassword = "";
        Random random = new Random();
        for (int i = 0; i < 8; i++) {
            newPassword += (char)(random.nextInt(25) + 97);
        }

        user.setNewPassword(newPassword);
        user.setPasswordCode(UUID.randomUUID().toString());
        userRepository.save(user);

        sendPasswordMessage(user);

        return true;
    }

    private void sendPasswordMessage(User user) {
        if(!StringUtils.isEmpty(user.getEmail())) {
            String message = String.format(
                    "Hello, %s\n" +
                            "Your new password:%s\n" +
                            "Please, visit next link for active new password: http://%s/password-restore/activate/%s",
                    user.getUsername(),
                    user.getNewPassword(),
                    serverName,
                    user.getPasswordCode()
            );

            emailService.send(user.getEmail(), "Restore password", message);
        }
    }

    public boolean activateNewPassword(String code) {
        User user = userRepository.findByPasswordCode(code);

        if(user == null) {
            return false;
        }

        user.setPasswordCode(null);
        user.setPassword(passwordEncoder.encode(user.getNewPassword()));
        user.setNewPassword(null);

        userRepository.save(user);

        return true;
    }

}
