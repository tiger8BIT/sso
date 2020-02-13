package artezio.vkolodynsky.auth;

import io.jsonwebtoken.*;

import javax.crypto.spec.SecretKeySpec;
import javax.servlet.http.HttpServletRequest;
import javax.xml.bind.DatatypeConverter;
import java.security.Key;
import java.time.LocalDateTime;
import java.util.*;

public class JwtUtil {
    public static String createJWT(Integer userId, Integer sessionId, String username, String password, final String SECRET_KEY, int expirationDays) {
        SignatureAlgorithm signatureAlgorithm = SignatureAlgorithm.HS512;
        byte[] apiKeySecretBytes = DatatypeConverter.parseBase64Binary(SECRET_KEY);
        Key signingKey = new SecretKeySpec(apiKeySecretBytes, signatureAlgorithm.getJcaName());
        long createDate =  new Date().getTime();
        Calendar calendar = Calendar.getInstance();
        calendar.add(Calendar.YEAR, expirationDays);
        Date expirationDate = calendar.getTime();
        Map<String, Object> tokenData = (new TokenData(userId, sessionId, username, LocalDateTime.now())).getMap();
        JwtBuilder jwtBuilder = Jwts.builder()
                .setExpiration(calendar.getTime())
                .setClaims(tokenData)
                .signWith(signatureAlgorithm, signingKey);
        return jwtBuilder.compact();
    }

    public static Optional<Claims> decodeJWT(String jwt, final String SECRET_KEY) {
        try {
            Claims claims = Jwts.parser()
                    .setSigningKey(DatatypeConverter.parseBase64Binary(SECRET_KEY))
                    .parseClaimsJws(jwt).getBody();
            return Optional.of(claims);
        } catch (SignatureException | MalformedJwtException |
                ExpiredJwtException | UnsupportedJwtException | IllegalArgumentException e) {
            return Optional.empty();
        }
    }
}