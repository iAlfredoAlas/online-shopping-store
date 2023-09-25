package com.kodigo.shopping.online.store.security.utils;

import com.kodigo.shopping.online.store.security.repository.UserRepository;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import lombok.Value;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.function.Function;

@Slf4j
@Service
public class JwtUtil {

    @Autowired
    UserRepository userRepository;
    
    @Autowired
    private JwtProperties jwtProperties;

    private UUIDUtil uuidUtil;

    private Map<String, Date> blacklist = new HashMap<>();

    public String extractUsername(String token) {
        return extractClaim(token, Claims::getSubject);
    }

    public Date extractExpiration(String token) {
        return extractClaim(token, Claims::getExpiration);
    }

    public <T> T extractClaim(String token, Function<Claims, T> claimsResolver) {
        final Claims claims = extractAllClaims(token);
        return claimsResolver.apply(claims);
    }

    private Claims extractAllClaims(String token) {
        return Jwts.parser().setSigningKey(jwtProperties.getSecret()).parseClaimsJws(token).getBody();
    }

    private Boolean isTokenExpired(String token) {
        boolean expired = extractExpiration(token).before(new Date());
        log.info("Is token expired: {}", expired);
        return expired;
    }

    public String generateToken(UserDetails userDetails) {
        Map<String, Object> claims = new HashMap<String, Object>();
        uuidUtil = new UUIDUtil();
        claims.put("uuid", uuidUtil.getUuid());
        claims.put("roles", userDetails.getAuthorities());
        claims.put("isAccountNonExpired", userDetails.isAccountNonExpired());
        claims.put("isAccountNonLocked", userDetails.isAccountNonLocked());
        claims.put("isCredentialsNonExpired", userDetails.isCredentialsNonExpired());
        claims.put("isEnabled", userDetails.isEnabled());
        String nombreUsuario = userRepository.findByUserName(userDetails.getUsername()).getUserName();
        claims.put("nombreUsuario", nombreUsuario);;
        return createToken(claims, userDetails.getUsername());
    }

    private String createToken(Map<String, Object> claims, String subject) {

        return Jwts.builder().setClaims(claims).setSubject(subject).setIssuedAt(new Date(System.currentTimeMillis()))
                .setExpiration(new Date(System.currentTimeMillis() + jwtProperties.getLifetime()))
                .signWith(SignatureAlgorithm.HS256, jwtProperties.getSecret()).compact();
    }

    public boolean validateToken(String token, UserDetails userDetails) {
        final String username = extractUsername(token);
        log.info("Validating token ...");
        return (isValidUsername(userDetails, username) && !isTokenExpired(token) && !isTokenOnBlacklist(token));
    }

    public boolean isTokenOnBlacklist(String token) {
        String uuid = extractAllClaims(token).get("uuid", String.class);
        boolean containsKey = blacklist.containsKey(uuid);
        log.info("Is token on black list: {}", containsKey);
        return containsKey;
    }

    public boolean isValidUsername(UserDetails userDetails, String username) {
        boolean equals = username.equals(userDetails.getUsername());
        log.info("Is token username valid: {}", equals);
        return equals;
    }

    public void addTokenToBlacklist(String token) {
        log.info("Adding token: {} to blacklist.", token.substring(0, 8).concat("..."));
        String uuid = extractAllClaims(token).get("uuid", String.class);
        blacklist.put(uuid, extractExpiration(token));
    }


}
