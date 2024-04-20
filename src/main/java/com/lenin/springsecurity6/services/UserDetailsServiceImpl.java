package com.lenin.springsecurity6.services;

import com.lenin.springsecurity6.persistence.entities.UserEntity;
import com.lenin.springsecurity6.persistence.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class UserDetailsServiceImpl implements UserDetailsService {

    @Autowired
    private UserRepository userRepository;


    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {

        Optional<UserEntity> userEntity= Optional.ofNullable(userRepository.findUserEntityByUsername(username)
                .orElseThrow(() -> new UsernameNotFoundException("El usuario " + username + "no existe")));

        List<SimpleGrantedAuthority> authorityList = new ArrayList<>();

        userEntity.get().getRoles()
                .forEach(role->authorityList.add(new SimpleGrantedAuthority("ROLE_"+role.getRoleName())));

        userEntity.get().getRoles().stream()
                .flatMap(role->role.getPermissionList().stream())
                .forEach(permission -> authorityList.add(new SimpleGrantedAuthority(permission.getName())));

        return new User(userEntity.get().getUsername(),
                userEntity.get().getPassword(),
                userEntity.get().isEnabled(),
                userEntity.get().isAccountNoExpired(),
                userEntity.get().isCredentialNoExpired(),
                userEntity.get().isAccountNoLocked(),
                authorityList);
    }
}
