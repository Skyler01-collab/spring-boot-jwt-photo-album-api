package com.example.springDemoWithRest.Controller;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import com.example.springDemoWithRest.Payload.auth.*;
import com.example.springDemoWithRest.Model.*;
import com.example.springDemoWithRest.Service.*;
import com.example.springDemoWithRest.util.constants.*;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.extern.slf4j.Slf4j;

@RestController
@RequestMapping("/auth")
@Tag(name = "Auth Controller", description = "Endpoints for user authentication and token generation")
@Slf4j
public class AuthController {

    private final AuthenticationManager authenticationManager;
    private final TokenService tokenService;
    private final AccountService accountService;

    public AuthController(AuthenticationManager authenticationManager, 
                          TokenService tokenService, 
                          AccountService accountService) {
        this.authenticationManager = authenticationManager;
        this.tokenService = tokenService;
        this.accountService = accountService;
    }

    @PostMapping("/token")
    @ResponseStatus(HttpStatus.OK)
    @Operation(summary = "Generate Token", description = "Authenticates user credentials and returns a signed JWT.")
    @ApiResponse(responseCode = "200", description = "Token generated successfully")
    @ApiResponse(responseCode = "400", description = "Bad Request: Authentication failed")
    public ResponseEntity<TokeDTOn> token(@RequestBody UserLoginDTO userLogin) throws AuthenticationException {
        try {
            Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(userLogin.getEmail(), userLogin.getPassword())
            );
            return ResponseEntity.ok(new TokeDTOn(tokenService.generateToken(authentication)));
        } catch (Exception e) {
            log.debug(AccountError.TOKEN_GENERATION_ERROR.toString() + ": " + e.getMessage());
            return new ResponseEntity<>(new TokeDTOn(""), HttpStatus.BAD_REQUEST);
        }
    }

    @PostMapping(value = "/users/add", consumes = "application/json", produces = "text/plain")
    @ResponseStatus(HttpStatus.CREATED)
    @ApiResponse(responseCode = "400", description = "Bad Request: Invalid input or account creation failed")
    @ApiResponse(responseCode = "201", description = "User account created successfully")
    @Operation(summary = "Add a new user account", description = "Creates a new user account with the provided email and password.")
    public ResponseEntity<String> addUser(@Valid @RequestBody AccountDTO accountDTO) {
        try {
            Account account = new Account();
            account.setEmail(accountDTO.getEmail());
            account.setPassword(accountDTO.getPassword());
            account.setAuthorities(Authority.USER.toString());
            accountService.save(account);
            return new ResponseEntity<>(AccountSuccess.ACCOUNT_ADDED.toString(), HttpStatus.CREATED);
        } catch (Exception e) {
            log.debug(AccountError.ADD_ACCOUNT_ERROR.toString() + ": " + e.getMessage());
            return ResponseEntity.badRequest().body("Error creating account: " + e.getMessage());
        }
    }

    @GetMapping(value = "/users", produces = "application/json")
    @Operation(summary = "List of users", description = "Retrieves a list of all user accounts.")
    @ApiResponse(responseCode = "401", description = "Unauthorized: Token Missing")
    @ApiResponse(responseCode = "403", description = "Forbidden: Insufficient Permissions")
    @ApiResponse(responseCode = "200", description = "Users retrieved successfully")
    @SecurityRequirement(name = "skyler-demo-api")
    public ResponseEntity<List<AccountViewDTO>> users() {
        List<AccountViewDTO> accounts = new ArrayList<>();
        for (Account acc : accountService.findAll()) {
            accounts.add(new AccountViewDTO(acc.getId(), acc.getEmail(), acc.getAuthorities()));
        }
        return ResponseEntity.ok(accounts);
    }

    @GetMapping(value = "/profile", produces = "application/json")
    @Operation(summary = "User profile", description = "Retrieves the profile information of the authenticated user.")
    @ApiResponse(responseCode = "200", description = "Profile retrieved successfully")
    @ApiResponse(responseCode = "401", description = "Unauthorized: Token Missing")
    @ApiResponse(responseCode = "404", description = "User Not Found")
    @SecurityRequirement(name = "skyler-demo-api")
    public ResponseEntity<AccountViewDTO> profile(Authentication authentication) {
        String email = authentication.getName();
        Optional<Account> accountOptional = accountService.findByEmail(email);

        if (accountOptional.isPresent()) {
            Account account = accountOptional.get();
            return ResponseEntity.ok(new AccountViewDTO(account.getId(), account.getEmail(), account.getAuthorities()));
        }

        log.debug(AccountError.USER_NOT_FOUND.toString() + ": " + email);
        return new ResponseEntity<>(new AccountViewDTO(), HttpStatus.NOT_FOUND);
    }

    @PutMapping(value = "/users/{user_id}/update-authorities", consumes = "application/json", produces = "application/json")
    @Operation(summary = "Update user authorities", description = "Updates the authorities of a user by their user ID.")
    @ApiResponse(responseCode = "200", description = "Authorities updated successfully")
    @ApiResponse(responseCode = "400", description = "Bad Request: User not found")
    @ApiResponse(responseCode = "401", description = "Unauthorized: Token missing")
    @ApiResponse(responseCode = "403", description = "Forbidden: Token Error")
    @SecurityRequirement(name = "skyler-demo-api")
    public ResponseEntity<AccountViewDTO> updateAuthorities(
            @Valid @RequestBody AuthoritiesDTO authoritiesDTO, 
            @PathVariable("user_id") long userId) {

        Optional<Account> accountOptional = accountService.findById(userId);
        if (accountOptional.isPresent()) {
            Account account = accountOptional.get();
            account.setAuthorities(authoritiesDTO.getAuthorities());
            accountService.save(account);
            return ResponseEntity.ok(new AccountViewDTO(account.getId(), account.getEmail(), account.getAuthorities()));
        }

        log.debug(AccountError.USER_NOT_FOUND.toString() + ": " + userId);
        return new ResponseEntity<>(new AccountViewDTO(), HttpStatus.BAD_REQUEST);
    }

    @PutMapping(value = "/profile/update-password", consumes = "application/json", produces = "application/json")
    @Operation(summary = "Update user password", description = "Updates the password for the authenticated user.")
    @ApiResponse(responseCode = "200", description = "Password updated successfully")
    @ApiResponse(responseCode = "401", description = "Unauthorized: Token Missing")
    @ApiResponse(responseCode = "403", description = "Forbidden: Token Error")
    @ApiResponse(responseCode = "404", description = "User Not Found")
    @SecurityRequirement(name = "skyler-demo-api")
    public ResponseEntity<AccountViewDTO> updatePassword(
            Authentication authentication, 
            @Valid @RequestBody PasswordDTO passwordDTO) {

        String email = authentication.getName();
        Optional<Account> accountOptional = accountService.findByEmail(email);

        if (accountOptional.isPresent()) {
            Account account = accountOptional.get();
            account.setPassword(passwordDTO.getPassword());
            accountService.save(account);
            return ResponseEntity.ok(new AccountViewDTO(account.getId(), account.getEmail(), account.getAuthorities()));
        }

        log.debug(AccountError.USER_NOT_FOUND.toString() + ": " + email);
        return new ResponseEntity<>(new AccountViewDTO(), HttpStatus.NOT_FOUND);
    }

    @DeleteMapping(value = "/profile/delete")
    @ApiResponse(responseCode = "200", description = "Update profile")
    @ApiResponse(responseCode = "401", description = "Token missing")
    @ApiResponse(responseCode = "403", description = "Token Error")
    @Operation(summary = "Delete profile")
    @SecurityRequirement(name = "skyler-demo-api")
    public ResponseEntity<String> delete_profile(Authentication authentication) {
        String email = authentication.getName();
        Optional<Account> optionalAccount = accountService.findByEmail(email);
        if (optionalAccount.isPresent()) {
            accountService.deleteByID(optionalAccount.get().getId());
            return ResponseEntity.ok("User delete");
        }

        return new ResponseEntity<String>("Bad request", HttpStatus.BAD_REQUEST);
    }
}