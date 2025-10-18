package kr.go.iop.ci.sc.config.security;
import javax.crypto.SecretKey;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;

/**
 * 토큰 정보 가져오기
 */
@Component
public class TokenProvider {

//	@Value("${auth.token-secret}")
	@Value("${jwt.secret}")
    private String tokenSecret;

	/**
	 * AuthenticationFilter.doFilter 메소드에서
	 * UsernamePasswordAuthenticationToken 정보를 세팅할 때 호출된다.
	 *
	 * @param token
	 * @return
	 */
	public Claims getClaimsFromToken(String token) {
		return Jwts.parser().setSigningKey(tokenSecret.getBytes()).parseClaimsJws(token.replaceAll("^Bearer", "").trim()).getBody();
	}
}

