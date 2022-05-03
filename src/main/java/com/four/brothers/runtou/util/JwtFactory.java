package com.four.brothers.runtou.util;

import com.four.brothers.runtou.dto.LoginDto;
import com.four.brothers.runtou.dto.UserRole;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Header;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.time.Duration;
import java.util.Date;

import static com.four.brothers.runtou.dto.LoginDto.*;

@Slf4j
@Component
public class JwtFactory {

  @Value("${jwt.secretkey}")
  private String secretKey;

  public String makeJwtToken(LoginUser loginUser) {
    Date now = new Date();

    return Jwts.builder()
      .setHeaderParam(Header.TYPE, Header.JWT_TYPE) // (1)
      .setIssuer("runtou") // (2)
      .setIssuedAt(now) // (3)
      .setExpiration(new Date(now.getTime() + Duration.ofMinutes(30).toMillis())) // (4)
      .claim("accountId", loginUser.getAccountId()) // (5)
      .claim("realName", loginUser.getRealName())
      .claim("nickname", loginUser.getNickname())
      .claim("phoneNumber", loginUser.getPhoneNumber())
      .claim("accountNumber", loginUser.getAccountNumber())
      .claim("role", loginUser.getRole())
      .signWith(SignatureAlgorithm.HS256, secretKey) // (6)
      .compact();
  }

  public Claims parseJwtToken(String authorizationHeader) {
    validationAuthorizationHeader(authorizationHeader); // (1)
    String token = extractToken(authorizationHeader); // (2)

    return Jwts.parser()
      .setSigningKey(secretKey) // (3)
      .parseClaimsJws(token) // (4)
      .getBody();
  }

  /**
   * 유효한 토큰인지 확인하는 메서드
   * @param header
   */
  private void validationAuthorizationHeader(String header) {
    if (header == null || !header.startsWith("Bearer ")) {
      throw new IllegalArgumentException();
    }
  }

  /**
   *
   * @param authorizationHeader
   * @return
   */
  private String extractToken(String authorizationHeader) {
    return authorizationHeader.substring("Bearer ".length());
  }
}
