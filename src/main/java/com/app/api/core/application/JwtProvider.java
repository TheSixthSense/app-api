package com.app.api.core.application;


import com.app.api.common.util.DateUtil;
import com.app.api.core.exception.BizException;
import com.app.api.jwt.dto.TokenDto;
import io.jsonwebtoken.*;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;

import javax.crypto.SecretKey;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Objects;


@Slf4j
@Component
public class JwtProvider {
    @Value("${jwt.secret}")
    private String jwtSecret;

    @Value("${jwt.issuer}")
    private String jwtIssuer;

    @Value("${jwt.accessTime}")
    private long accessTime;

    @Value("${jwt.refreshTime}")
    private long refreshTime;

    private SecretKey getJwtSecret() {
        return Keys.hmacShaKeyFor(Decoders.BASE64.decode(jwtSecret));
    }

    public String createAccessToken(TokenDto tokenDto) {
        return createToken(tokenDto, accessTime);
    }

    public String createRefreshToken(TokenDto tokenDto) {
        return createToken(tokenDto, refreshTime);
    }

    private String createToken(TokenDto tokenDto, Long expireTime) {
        return Jwts.builder()
                .setHeaderParam(Header.TYPE, Header.JWT_TYPE)
                .setIssuer(jwtIssuer)
                .setIssuedAt(new Date())
                .claim("role", tokenDto.getRole())
                .claim("user_id", tokenDto.getUserId())
                .signWith(getJwtSecret(), SignatureAlgorithm.HS256)
                .setExpiration(new Date(System.currentTimeMillis() + expireTime))
                .compact();
    }

    public LocalDateTime getExpireTime(String token) {
        return DateUtil.changeLocalDateTime(parseClaims(token).getExpiration());
    }

    public Claims parseClaims(String token) {
        return Jwts.parserBuilder()
                .setSigningKey(getJwtSecret())
                .build()
                .parseClaimsJws(token)
                .getBody();
    }

    public Authentication getAuthentication(String accessToken) {
        Claims claims = parseClaims(accessToken);
        validateClaims(claims);

        List<GrantedAuthority> authorities = new ArrayList<GrantedAuthority>();
        authorities.add(new SimpleGrantedAuthority(claims.get("role").toString()));

        UserDetails principal = new User(claims.get("user_id").toString(), "", authorities);

        return new UsernamePasswordAuthenticationToken(principal, "", authorities);
    }

    public void validateClaims(Claims claims) {
        if (Objects.isNull(claims)) {
            log.error("[JWT Token Filter Error]: claims User Not Found. Please check header.");
            throw BizException.withUserMessageKey("exception.jwt.token.claims.invalid").build();
        }
        if (Objects.isNull(claims.get("role"))) {
            log.error("[JWT Token Filter Error]: User Not Found. Please check header.");
            throw BizException.withUserMessageKey("exception.jwt.token.claims.invalid").build();
        }
    }

    public boolean validateToken(String token) {
        try {
            Jwts.parserBuilder()
                    .setSigningKey(getJwtSecret())
                    .build()
                    .parseClaimsJws(token);
            return true;
        } catch (MalformedJwtException e) {
            log.error("Invalid JWT token: {}", e.getMessage());
            throw BizException.withUserMessageKey("exception.jwt.token.invalid").build();
        } catch (ExpiredJwtException e) {
            log.error("JWT token is expired: {}", e.getMessage());
            throw BizException.withUserMessageKey("exception.jwt.token.expire").build();
        } catch (UnsupportedJwtException e) {
            log.error("JWT token is unsupported: {}", e.getMessage());
            throw BizException.withUserMessageKey("exception.jwt.token.invalid").build();
        } catch (IllegalArgumentException e) {
            log.error("JWT claims string is empty: {}", e.getMessage());
            throw BizException.withUserMessageKey("exception.jwt.token.invalid").build();
        }
    }
}
