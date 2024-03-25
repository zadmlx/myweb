package individual.me.config.security.authentication.phone;

import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.AbstractAuthenticationProcessingFilter;
import org.springframework.security.web.util.matcher.RequestMatcher;

import java.io.IOException;
import java.util.Map;

public class  PhoneLoginFilter extends AbstractAuthenticationProcessingFilter {

    private static final String PHONE_NUMBER = "phoneNumber";
    private static final String CODE = "code";

    protected PhoneLoginFilter(RequestMatcher requiresAuthenticationRequestMatcher, AuthenticationManager authenticationManager) {
        super(requiresAuthenticationRequestMatcher, authenticationManager);
    }

    @Override
    public Authentication attemptAuthentication(HttpServletRequest request, HttpServletResponse response) throws AuthenticationException, IOException {

        Map<String, String> userInfo = new ObjectMapper().readValue(request.getInputStream(), Map.class);
        String phoneNumber = userInfo.get(PHONE_NUMBER);
        String code = userInfo.get(CODE);

        PhoneAuthenticationToken token = new PhoneAuthenticationToken(phoneNumber,code);
        return this.getAuthenticationManager().authenticate(token);
    }
}
