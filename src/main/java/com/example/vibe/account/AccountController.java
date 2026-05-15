package com.example.vibe.account;

import com.example.vibe.account.service.AccountService;
import com.example.vibe.account.dto.AccountRequestDto;
import com.example.vibe.account.dto.AccountResponseDto;
import com.example.vibe.account.dto.LoginRequestDto;
import com.example.vibe.infra.security.token.dto.RecoveryJwtTokenDto;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/accounts")
@RequiredArgsConstructor
public class AccountController {

    private final AccountService accountService;

    @PostMapping
    public ResponseEntity<AccountResponseDto> create(@RequestBody @Valid AccountRequestDto accountRequestDto) {
        return ResponseEntity.status(201).body(accountService.create(accountRequestDto));
    }


    @PostMapping("/login")
    public ResponseEntity<RecoveryJwtTokenDto> login(@RequestBody @Valid LoginRequestDto loginRequestDto) {
        RecoveryJwtTokenDto token = accountService.authenticateUser(loginRequestDto);
        return ResponseEntity.ok(token);

    }

}
