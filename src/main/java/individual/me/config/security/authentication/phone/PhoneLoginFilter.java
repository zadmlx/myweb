package individual.me.config.security.authentication.phone;

import individual.me.pojo.login.LoginPhone;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.http.server.ServletServerHttpRequest;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.AbstractAuthenticationProcessingFilter;
import org.springframework.security.web.util.matcher.RequestMatcher;

import java.io.IOException;


public class  PhoneLoginFilter extends AbstractAuthenticationProcessingFilter {

    private final MappingJackson2HttpMessageConverter converter;

    protected PhoneLoginFilter(RequestMatcher requiresAuthenticationRequestMatcher, AuthenticationManager authenticationManager,MappingJackson2HttpMessageConverter converter) {
        super(requiresAuthenticationRequestMatcher, authenticationManager);
        this.converter = converter;
    }

    @Override
    public Authentication attemptAuthentication(HttpServletRequest request, HttpServletResponse response) throws AuthenticationException, IOException {
        LoginPhone loginPhone = (LoginPhone) converter.read(LoginPhone.class, new ServletServerHttpRequest(request));
        PhoneAuthenticationToken token = new PhoneAuthenticationToken(loginPhone.getPhoneNumber(), loginPhone.getCode());
        return this.getAuthenticationManager().authenticate(token);
    }
}
