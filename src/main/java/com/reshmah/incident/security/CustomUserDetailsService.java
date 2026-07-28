package com.reshmah.incident.security;

import com.reshmah.incident.entity.UserEntity;
import com.reshmah.incident.repository.Users;
import org.jspecify.annotations.NonNull;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
public class CustomUserDetailsService implements UserDetailsService {

    private final Users users;

    public CustomUserDetailsService (Users users){
        this.users = users;
    }

    @Override
    public UserDetails loadUserByUsername(@NonNull String username) throws UsernameNotFoundException {
        UserEntity userEntity = users.findByUserName(username).orElseThrow(() -> new UsernameNotFoundException("User not found!"));
        return User.withUsername(userEntity.getUserName())
                .password(userEntity.getPassword())
                .roles(userEntity.getRole().name())
                .build();
    }
}
