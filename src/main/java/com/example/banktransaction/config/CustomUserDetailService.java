package com.example.banktransaction.config;

import com.example.banktransaction.persistence.user.User;
import com.example.banktransaction.persistence.user.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Service
public class CustomUserDetailService implements UserDetailsService {

    @Autowired
    private UserRepository userRepository;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        User user = userRepository.getByEmail(username);
        if(user == null){
            throw new UsernameNotFoundException("Invalid Username or Password");
        }
        List<String> authorities =  user.getAuthorities().stream().map(authority -> authority.toString()).collect(Collectors.toList());
        Set<String> authoritiesSet = new HashSet<>(authorities);
        return (UserDetails) new CustomUserDetail(user.getId(),user.getEmail(),user.getPassword(),authoritiesSet);

    }
}
