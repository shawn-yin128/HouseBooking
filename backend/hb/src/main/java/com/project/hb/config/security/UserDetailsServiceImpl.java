package com.project.hb.config.security;

import com.project.hb.model.user.Authority;
import com.project.hb.model.user.User;
import com.project.hb.repository.user.AuthorityRepository;
import com.project.hb.repository.user.UserRepository;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.Collections;

@Service
@AllArgsConstructor(onConstructor_ = {@Autowired})
public class UserDetailsServiceImpl implements UserDetailsService {
    private UserRepository userRepository;
    private AuthorityRepository authorityRepository;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        User user = userRepository.findById(username)
                .orElseThrow(() -> new UsernameNotFoundException("User not found."));
        Authority authority = authorityRepository.findById(user.getUsername())
                .orElseThrow(() -> new UsernameNotFoundException("User not found."));
        return new org.springframework.security.core.userdetails.User(user.getUsername(), user.getPassword(), Collections.singleton(new SimpleGrantedAuthority(authority.getAuthority())));
    }
}
