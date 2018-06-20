package util;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.JwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.impl.crypto.MacProvider;
import java.security.Key;
import java.util.Date;

public class JWTAuth
{
    private static JWTAuth instance = null;
    private Key privateKey = MacProvider.generateKey();

    protected JWTAuth() { }

    public static JWTAuth getInstance()
    {
        if(instance == null) {
            instance = new JWTAuth();
        }
        return instance;
    }

    /**
     * Generates new JSON web token.
     * @param userId Must be unique, will be used as the subject of the token.
     * @return Newly generated token.
     */
    public String GenerateToken(String userId)
    {
        long nowMillis = System.currentTimeMillis();
        Date now = new Date(nowMillis);
        Date exp = new Date(nowMillis + 3600000); // 1 hour expiration time.

        String token = Jwts.builder()
                .setSubject(userId)
                .setIssuedAt(now)
                .setExpiration(exp)
                .signWith(SignatureAlgorithm.HS512, privateKey)
                .compact();

        return token;
    }

    /**
     * Verifies if the provided token is valid.
     * @param token Token that needs to be verified.
     * @param userId Subject of the token that needs to be verified.
     * @return True if the token is valid.
     */
    public boolean VerifyToken(String token, String userId)
    {
        try
        {
            Claims claims = Jwts.parser().setSigningKey(privateKey).parseClaimsJws(token).getBody();

            // Check subject
            if (!claims.getSubject().equals(userId))
            {
                return false;
            }
        }
        catch (JwtException ex)
        {
            return false;
        }

        return true;
    }
}