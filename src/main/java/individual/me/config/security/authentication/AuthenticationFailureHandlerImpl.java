package individual.me.config.security.authentication;

import individual.me.pojo.Result;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.http.MediaType;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.http.server.ServletServerHttpResponse;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.AuthenticationFailureHandler;

import java.io.IOException;

public class AuthenticationFailureHandlerImpl  implements AuthenticationFailureHandler {

    private final MappingJackson2HttpMessageConverter messageConverter;

    public AuthenticationFailureHandlerImpl(MappingJackson2HttpMessageConverter messageConverter) {
        this.messageConverter = messageConverter;
    }

    @Override
    public void onAuthenticationFailure(HttpServletRequest request, HttpServletResponse response, AuthenticationException exception) throws IOException {
        Result result = Result.fail(exception.getMessage());
        messageConverter.write(result, MediaType.APPLICATION_JSON,new ServletServerHttpResponse(response));
    }
}
