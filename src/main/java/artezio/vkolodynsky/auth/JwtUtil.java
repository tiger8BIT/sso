package artezio.vkolodynsky.auth;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.JwtBuilder;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;

import javax.crypto.spec.SecretKeySpec;
import javax.servlet.http.HttpServletRequest;
import javax.xml.bind.DatatypeConverter;
import java.security.Key;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

public class JwtUtil {
    public static String createJWT(String userId, String username, String password, final String SECRET_KEY, int expirationDays) {
        SignatureAlgorithm signatureAlgorithm = SignatureAlgorithm.HS512;
        byte[] apiKeySecretBytes = DatatypeConverter.parseBase64Binary(SECRET_KEY);
        Key signingKey = new SecretKeySpec(apiKeySecretBytes, signatureAlgorithm.getJcaName());
        long createDate =  new Date().getTime();
        Calendar calendar = Calendar.getInstance();
        calendar.add(Calendar.YEAR, expirationDays);
        Date expirationDate = calendar.getTime();
        Map<String, Object> tokenData = (new TokenData("user", userId, username, password, createDate, expirationDate)).getMap();
        JwtBuilder jwtBuilder = Jwts.builder()
                .setExpiration(calendar.getTime())
                .setClaims(tokenData)
                .signWith(signatureAlgorithm, signingKey);
        return jwtBuilder.compact();
    }

    public static Claims decodeJWT(String jwt,  final String SECRET_KEY) {
        Claims claims = Jwts.parser()
                .setSigningKey(DatatypeConverter.parseBase64Binary(SECRET_KEY))
                .parseClaimsJws(jwt).getBody();
        return claims;
    }
}