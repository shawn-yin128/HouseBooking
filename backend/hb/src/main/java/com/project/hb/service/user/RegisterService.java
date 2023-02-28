package com.project.hb.service.user;

import com.project.hb.exception.UserAlreadyExistException;
import com.project.hb.model.user.Authority;
import com.project.hb.model.user.Role;
import com.project.hb.model.user.User;
import com.project.hb.repository.user.AuthorityRepository;
import com.project.hb.repository.user.UserRepository;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Transactional;

@Service
@AllArgsConstructor(onConstructor_ = {@Autowired})
public class RegisterService {
    private UserRepository userRepository;
    private AuthorityRepository authorityRepository;
    private PasswordEncoder passwordEncoder;

    @Transactional(isolation = Isolation.SERIALIZABLE)
    public void add(User user, Role role) throws UserAlreadyExistException {
        if (userRepository.existsById(user.getUsername())) {
            throw new UserAlreadyExistException("User already exists.");
        }
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        user.setEnabled(true);
        userRepository.save(user);
        authorityRepository.save(new Authority(user.getUsername(), role.name()));
    }
}
