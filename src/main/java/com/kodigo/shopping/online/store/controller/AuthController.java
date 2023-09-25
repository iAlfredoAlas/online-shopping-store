package com.kodigo.shopping.online.store.controller;

import com.kodigo.shopping.online.store.security.dto.AuthenticationRequest;
import com.kodigo.shopping.online.store.security.dto.ResponseDTO;
import com.kodigo.shopping.online.store.security.repository.UserRepository;
import com.kodigo.shopping.online.store.security.service.CustomUserDetailsService;
import com.kodigo.shopping.online.store.security.utils.HttpRequestUtil;
import com.kodigo.shopping.online.store.security.utils.InvalidBearerTokenFormatException;
import com.kodigo.shopping.online.store.security.utils.JwtUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@Slf4j
@Controller
@RequestMapping("/auth")
public class AuthController {

    private final AuthenticationManager authenticationManager;
    private final CustomUserDetailsService userDetailsService;
    private final JwtUtil jwtUtil;
    private final UserRepository userRepository;

    public AuthController(AuthenticationManager authenticationManager, CustomUserDetailsService userDetailsService,
                          JwtUtil jwtUtil, UserRepository userRepository) {
        this.authenticationManager = authenticationManager;
        this.userDetailsService = userDetailsService;
        this.jwtUtil = jwtUtil;
        this.userRepository = userRepository;
    }

    @PostMapping("/authenticate")
    public ResponseEntity<ResponseDTO> login(@RequestBody AuthenticationRequest authenticationRequest) {
        try {
            log.info("Authenticating user...");
            authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(
                    authenticationRequest.getUserName(), authenticationRequest.getPassword()));
            log.info("Authentication completed !!!");
        } catch (AuthenticationException e) {
            log.error("User name or password incorrect...");
            return new ResponseEntity<>(new ResponseDTO("Incorrect username or password", "401", null, e.getMessage()),
                    HttpStatus.UNAUTHORIZED);
        }
        final UserDetails userDetails = userDetailsService.loadUserByUsername(authenticationRequest.getUserName());
        log.info("Generating jwt response...");
        final String jwt = jwtUtil.generateToken(userDetails);
        log.info("Returning jwt");
        return ResponseEntity.ok(new ResponseDTO("Jwt Token", "200", jwt, null));
    }

    @PostMapping("/logout")
    public ResponseEntity<ResponseDTO> disableToken(HttpServletRequest request, HttpServletResponse response) {
        try {
            if (HttpRequestUtil.requestNotContainsAuthentication(request)) {
                log.error("The request does not have a token, the logout cannot be performed.");
                return new ResponseEntity<>(new ResponseDTO("Request without token...", "400", null, null),
                        HttpStatus.BAD_REQUEST);
            } else {
                log.info("Request with token, the logout proceeds.");
                String jwt = HttpRequestUtil.getAuthenticationToken(request);
                jwtUtil.addTokenToBlacklist(jwt);
            }
            ResponseDTO responseDTO = new ResponseDTO("OK", "200", null, null);
            return new ResponseEntity<>(responseDTO, HttpStatus.OK);
        } catch (InvalidBearerTokenFormatException e) {
            log.error("Error, logout failed: {}", e.getMessage());
            return new ResponseEntity<>(new ResponseDTO("Invalid Token format.", "400", null, e.getMessage()),
                    HttpStatus.BAD_REQUEST);
        }
    }

    @PostMapping("/validate")
    public ResponseEntity<ResponseDTO> validateToken(HttpServletRequest request, HttpServletResponse response) {
        try {
            if (HttpRequestUtil.requestNotContainsAuthentication(request)) {
                log.error("The request does not have a token, the validation cannot be performed.");
                return new ResponseEntity<>(new ResponseDTO("Request without token...", "400", null, null),
                        HttpStatus.BAD_REQUEST);
            } else {
                log.info("Request with token, validation proceeds.");
                String jwt = HttpRequestUtil.getAuthenticationToken(request);
                UserDetails userDetails = userDetailsService.loadUserByUsername(jwtUtil.extractUsername(jwt));
                Boolean validToken = jwtUtil.validateToken(jwt, userDetails);
                return new ResponseEntity<>(new ResponseDTO("Token validation result", "200", validToken, null),
                        HttpStatus.OK);
            }
        } catch (InvalidBearerTokenFormatException e) {
            log.error("Error, in token validation: {}", e.getMessage());
            return new ResponseEntity<>(new ResponseDTO("Invalid Token format.", "400", null, e.getMessage()),
                    HttpStatus.BAD_REQUEST);
        }
    }

}
