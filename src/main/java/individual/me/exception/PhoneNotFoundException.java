package individual.me.exception;

import org.springframework.security.core.AuthenticationException;

public class PhoneNotFoundException extends AuthenticationException {
    public PhoneNotFoundException(String msg) {
        super(msg);
    }
}
