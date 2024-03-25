package individual.me.config.security.authentication;

import individual.me.pojo.user.AuthUser;
import individual.me.pojo.user.User;
import lombok.Getter;
import org.springframework.security.authentication.AbstractAuthenticationToken;
import org.springframework.security.core.GrantedAuthority;

import java.util.Collection;

@Getter
public class PhoneAuthenticationToken extends AbstractAuthenticationToken {

    private String phoneNumber;
    private int code;

    private AuthUser principle;
    private Object credential;

    public PhoneAuthenticationToken(String phoneNumber, int code) {
        super(null);
        this.phoneNumber = phoneNumber;
        this.code = code;
    }

    public PhoneAuthenticationToken(Collection<? extends GrantedAuthority> authorities, AuthUser principle, Object credential) {
        super(authorities);
        this.principle = principle;
        this.credential = credential;
        this.setAuthenticated(true);
    }

    @Override
    public Object getCredentials() {
        return this.credential;
    }

    @Override
    public Object getPrincipal() {
        return this.principle;
    }
}