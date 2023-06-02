package com.tsi.vehicle.jwt;


import io.jsonwebtoken.*;
import io.jsonwebtoken.security.Keys;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.security.core.userdetails.User;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import javax.crypto.SecretKey;
import javax.crypto.spec.SecretKeySpec;
import java.nio.charset.StandardCharsets;
import java.security.Key;
import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.Base64;
import java.util.Collection;
import java.util.Date;
import java.util.UUID;
import java.util.stream.Collectors;

@Component
@RequiredArgsConstructor
@Slf4j
public class JwtTokenProvider {

    private static final String AUTHORITIES_KEY = "roles";

    private SecretKey secretKey;

    @Value("${token.validity:5}")
    private Long tokenValidityInMins;

    @Value("${jwt.secret:asdfSFS34wfsdfsdfSDSD32dfsddDDerQSNCK34SOWEK5354fdgdf4}")
    private String secret;

    public String createToken(Authentication authentication) {
        String userName = authentication.getName();
        Collection<? extends GrantedAuthority> authorities = authentication.getAuthorities();
        Claims claims = Jwts.claims().setSubject(userName);
        if (!authorities.isEmpty()) {
            claims.put(AUTHORITIES_KEY, authorities.stream()
                    .map(GrantedAuthority::getAuthority).collect(Collectors.joining(",")));
        }
        Key hmacKey = new SecretKeySpec(Base64.getDecoder().decode(secret),
                SignatureAlgorithm.HS256.getJcaName());

        Instant now = Instant.now();
        String token = Jwts.builder()
                .claim("name", userName)
                .claim("email", userName)
                .setSubject(userName)
                .setId(UUID.randomUUID().toString())
                .setIssuedAt(Date.from(now))
                .setExpiration(Date.from(now.plus(tokenValidityInMins, ChronoUnit.MINUTES)))
                .signWith(hmacKey)
                .compact();

        log.info("Generated token : {}", token);
        return token;
    }

    public Authentication getAuthentication(String token) {

        Key hmacKey = new SecretKeySpec(Base64.getDecoder().decode(secret),
                SignatureAlgorithm.HS256.getJcaName());

        Claims claims = Jwts.parserBuilder()
                .setSigningKey(hmacKey)
                .build()
                .parseClaimsJws(token)
                .getBody();

        Object authoritiesClaims = claims.get(AUTHORITIES_KEY);
        Collection<? extends GrantedAuthority> grantedAuthorities =
                authoritiesClaims == null ?
                        AuthorityUtils.NO_AUTHORITIES :
                        AuthorityUtils.commaSeparatedStringToAuthorityList(authoritiesClaims.toString());

        User user = new User(claims.getSubject(), "", grantedAuthorities);
        return new UsernamePasswordAuthenticationToken(user, token, grantedAuthorities);
    }

    public boolean validateToken(String token) {
        try {
            Key hmacKey = new SecretKeySpec(Base64.getDecoder().decode(secret),
                    SignatureAlgorithm.HS256.getJcaName());

            Jws<Claims> claims = Jwts.parserBuilder()
                    .setSigningKey(hmacKey)
                    .build()
                    .parseClaimsJws(token);

            log.info("The expiration date: {}", claims.getBody().getExpiration());
            return true;
        } catch (JwtException | IllegalArgumentException e) {
            //log.error("Invalid JWT token :{}", e.getMessage());
            return true;
        }
    }
}