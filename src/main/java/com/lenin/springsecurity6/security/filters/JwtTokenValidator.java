package com.lenin.springsecurity6.security.filters;

import com.auth0.jwt.interfaces.DecodedJWT;
import com.lenin.springsecurity6.security.utils.JwtUtils;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;
import java.util.Collection;

public class JwtTokenValidator extends OncePerRequestFilter {

    private JwtUtils jwtUtils;

    public JwtTokenValidator(JwtUtils jwtUtils) {
        this.jwtUtils = jwtUtils;
    }


    @Override
    protected void doFilterInternal(HttpServletRequest request,
                                    HttpServletResponse response,
                                    FilterChain filterChain) throws ServletException, IOException {


    String jwtToken=request.getHeader("Authorization");

    if(jwtToken!=null && jwtToken.startsWith("Bearer ")){
        jwtToken=jwtToken.substring(7);
        DecodedJWT decodedJWT=jwtUtils.validateToken(jwtToken);
        if(decodedJWT!=null){
            String userName=jwtUtils.extractUsername(decodedJWT);
            String stringAuthorities=jwtUtils.getSpecificClaim(decodedJWT,"authorities").asString();
            Collection<? extends GrantedAuthority> authorities= AuthorityUtils.commaSeparatedStringToAuthorityList(stringAuthorities);

            SecurityContext context= SecurityContextHolder.getContext();
            Authentication authentication=new UsernamePasswordAuthenticationToken(userName,null,authorities);
            context.setAuthentication(authentication);
            SecurityContextHolder.setContext(context);
        }
    }
    filterChain.doFilter(request, response);

    }
}
