package individual.me.config.security;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jws;
import io.jsonwebtoken.security.Keys;
import org.apache.el.parser.Token;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.stereotype.Component;
import io.jsonwebtoken.Jwts;
import javax.crypto.SecretKey;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;
import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

@Component
public class JwtUtil {

    private static final String AUTHORITY = "Authority";
    private static final String USERNAME = "username";

    private static final String MYKEY = "zadmlxzadmlxzadmlxzadmlxzadmlxzadmlx";
    private static final SecretKey SECRET_KEY = Keys.hmacShaKeyFor(MYKEY.getBytes(StandardCharsets.UTF_8));

    public static String createToken(String authority, String username){
        return Jwts.builder().claim(USERNAME, username).claim(AUTHORITY, authority).signWith(SECRET_KEY).compact();
    }

    static {
        String token = Jwts.builder().claim("username", "daixun").signWith(SECRET_KEY).compact();
        Jws<Claims> claimsJws = Jwts.parser().verifyWith(SECRET_KEY).build().parseSignedClaims(token);
        System.out.println(claimsJws.getPayload().get("username"));
    }


    public static String getAuthority(String token){
        try {
            Jws<Claims> claimsJws = Jwts.parser().verifyWith(SECRET_KEY).build().parseSignedClaims(token);
            return claimsJws.getPayload().get(AUTHORITY, String.class);
        }catch (Exception e){
            throw new RuntimeException("token无效");
        }
    }

    /**
     * 踩坑：key是静态生成，每次启动生成的都不一致，导致如果重启的话，获得的私钥就不一样，导致验证出现错误！！！！
     * @param token
     * @return
     */
    public static Authentication getAuthentication(String token){

            System.out.println(token);
            Jws<Claims> claimsJws = Jwts.parser().verifyWith(SECRET_KEY).build().parseSignedClaims(token);
            String username = claimsJws.getPayload().get(USERNAME, String.class);
        System.out.println(username);
            String authority = claimsJws.getPayload().get(AUTHORITY,String.class);
        System.out.println(authority);
            User user = new User(username,"ENCRYPTED", Collections.singleton(new SimpleGrantedAuthority(authority)));
            return new UsernamePasswordAuthenticationToken(user,token);

    }
}
