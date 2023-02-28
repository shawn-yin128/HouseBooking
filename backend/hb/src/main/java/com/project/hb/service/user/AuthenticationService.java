package com.project.hb.service.user;

import com.project.hb.exception.UserNotExistException;
import com.project.hb.model.Token;
import com.project.hb.model.user.Role;
import com.project.hb.model.user.User;
import com.project.hb.utils.JwtUtils;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor(onConstructor_ = {@Autowired})
public class AuthenticationService {
    private AuthenticationManager authenticationManager;
    private JwtUtils jwtUtils;

    public Token authenticate(User user, Role role) throws UserNotExistException {
        Authentication auth = null;
        try {
            auth = authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(user.getUsername(), user.getPassword()));
        } catch (AuthenticationException ex) {
            throw new UserNotExistException("User not exists.");
        }
        if (auth == null || !auth.isAuthenticated() || !auth.getAuthorities().contains(new SimpleGrantedAuthority(role.name()))) {
            throw new UserNotExistException("User not exists.");
        }
        return new Token(jwtUtils.generateToken(user.getUsername()));
    }
}
