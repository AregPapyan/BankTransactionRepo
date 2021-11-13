package com.example.banktransaction.config;

import com.example.banktransaction.persistence.user.User;
import com.example.banktransaction.service.user.UserService;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Set;
import java.util.stream.Collectors;

@Service
public class CustomUserDetailService implements UserDetailsService {

    private final UserService userService;

    public CustomUserDetailService(UserService userService) {
        this.userService = userService;
    }


    @Override
    @Transactional
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        User user = userService.findByEmail(username);
        if(user == null){
            throw new UsernameNotFoundException(username);
        }
        Set<String> authoritiesSet = user.getAuthorities().stream().map(authority -> authority.getName().name()).collect(Collectors.toSet());
        return  new CustomUserDetail(user.getId(),user.getEmail(),user.getPassword(),authoritiesSet);

    }
}
