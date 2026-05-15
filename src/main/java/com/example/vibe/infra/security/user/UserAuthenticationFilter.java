package com.example.vibe.infra.security.user;

import com.example.vibe.account.entity.Account;
import com.example.vibe.infra.security.SecurityConfig;
import com.example.vibe.account.repository.AccountRepository;
import com.example.vibe.infra.security.token.JwtTokenService;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;
import java.util.Arrays;

@Component
public class UserAuthenticationFilter extends OncePerRequestFilter {

    @Autowired
    private JwtTokenService jwtTokenService;

    @Autowired
    private AccountRepository accountRepository;

    @Override
    protected void doFilterInternal(HttpServletRequest request,
                                    HttpServletResponse response,
                                    FilterChain filterChain
    ) throws ServletException, IOException {
        if(checkIfEndpointIsNotPublic(request)){
            String token = recoveryToken(request);
            if(token != null){
                String subject = jwtTokenService.getSubjectFromToken(token);
                Account account = accountRepository.findByEmail(subject).get();
                UserDetailsImpl userDetails = new UserDetailsImpl(account);

                Authentication authentication =
                        new UsernamePasswordAuthenticationToken(userDetails.getUsername(), null, null );

                SecurityContextHolder.getContext().setAuthentication(authentication);
            }else {
                throw new RuntimeException("O token está ausente.");
            }
        }
        filterChain.doFilter(request,response);
    }

    public String recoveryToken(HttpServletRequest request){
        String authorizationHeader = request.getHeader("Authorization");
        if(authorizationHeader != null && authorizationHeader.startsWith("Bearer ")){
            return authorizationHeader.substring(7);
        }
        return null;
    }

    public boolean checkIfEndpointIsNotPublic(HttpServletRequest request){
        String requestURI = request.getRequestURI();
        return !Arrays.asList(SecurityConfig.endpointsAllowed).contains(requestURI);
    }
}
