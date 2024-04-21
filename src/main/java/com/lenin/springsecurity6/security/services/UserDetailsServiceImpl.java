package com.lenin.springsecurity6.security.services;

import com.lenin.springsecurity6.security.persistence.dto.AuthLoginRequest;
import com.lenin.springsecurity6.security.persistence.dto.AuthResponse;
import com.lenin.springsecurity6.security.persistence.entities.UserEntity;
import com.lenin.springsecurity6.security.persistence.repositories.UserRepository;
import com.lenin.springsecurity6.security.utils.JwtUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class UserDetailsServiceImpl implements UserDetailsService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private JwtUtils jwtUtils;
    @Autowired
    private PasswordEncoder passwordEncoder;



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

    public AuthResponse login(AuthLoginRequest authRequest) {
        String username = authRequest.username();
        String password = authRequest.password();

        Authentication authentication = this.authenticate(username, password);
        SecurityContextHolder.getContext().setAuthentication(authentication);

        String accessToken = jwtUtils.generateToken(authentication);
        AuthResponse authResponse = new AuthResponse(username, "User loged succesfully", accessToken, true);
        return authResponse;
    }

    public Authentication authenticate(String username, String password) {
        UserDetails userDetails = this.loadUserByUsername(username);

        if (userDetails == null) {
            throw new BadCredentialsException(String.format("Invalid username or password"));
        }

        if (!passwordEncoder.matches(password, userDetails.getPassword())) {
            throw new BadCredentialsException("Incorrect Password");
        }

        return new UsernamePasswordAuthenticationToken(username, password, userDetails.getAuthorities());
    }
}
