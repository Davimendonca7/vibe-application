package com.example.vibe.account.service;

import com.example.vibe.account.dto.AccountRequestDto;
import com.example.vibe.account.dto.AccountResponseDto;
import com.example.vibe.account.entity.Account;
import com.example.vibe.account.repository.AccountRepository;
import com.example.vibe.infra.security.SecurityConfig;
import com.example.vibe.infra.security.token.JwtTokenService;
import com.example.vibe.infra.security.token.dto.RecoveryJwtTokenDto;
import com.example.vibe.account.dto.LoginRequestDto;
import com.example.vibe.infra.security.user.UserDetailsImpl;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
@RequiredArgsConstructor
public class AccountService {

    private final AccountRepository accountRepository;
    private final AuthenticationManager authenticationManager;
    private final JwtTokenService jwtTokenService;
    private final SecurityConfig securityConfig;
    private final PasswordEncoder passwordEncoder;
    @Transactional
    public AccountResponseDto create(AccountRequestDto accountRequestDto) {
        Account account = Account.builder()
                .id(UUID.randomUUID())
                .password(passwordEncoder.encode(accountRequestDto.password()))
                .email(accountRequestDto.email())
                .username(accountRequestDto.username())
                .biography(accountRequestDto.biography())
                .build();

        Account acc = accountRepository.save(account);
        return new AccountResponseDto(acc);
    }

    public RecoveryJwtTokenDto authenticateUser(LoginRequestDto loginRequestDto) {
        UsernamePasswordAuthenticationToken usernamePasswordAuthenticationToken =
                new UsernamePasswordAuthenticationToken(loginRequestDto.email(), loginRequestDto.password());

        Authentication  authentication = authenticationManager.authenticate(usernamePasswordAuthenticationToken);

        UserDetailsImpl userDetails = (UserDetailsImpl) authentication.getPrincipal();

        return new RecoveryJwtTokenDto(jwtTokenService.generateToken(userDetails));

    }
}
