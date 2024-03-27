package individual.me.config.security.authentication;

import individual.me.config.security.jwt.JwtUtil;
import individual.me.pojo.Result;
import individual.me.pojo.user.AuthUser;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.http.MediaType;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.http.server.ServletServerHttpResponse;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;

import java.io.IOException;

public class AuthenticationSuccessHandlerImpl implements AuthenticationSuccessHandler {

    private final MappingJackson2HttpMessageConverter messageConverter;

    public AuthenticationSuccessHandlerImpl(MappingJackson2HttpMessageConverter messageConverter) {
        this.messageConverter = messageConverter;
    }

    @Override
    public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response, Authentication authentication) throws IOException {
        AuthUser authUser = (AuthUser) authentication.getPrincipal();
        String token = JwtUtil.createToken(authUser.getUser().getAuthority(), authUser.getUsername(), authUser.getUser().getId());
        SecurityContextHolder.getContext().setAuthentication(authentication);
        Result result = Result.ok("登录成功", token, 200);
        messageConverter.write(result, MediaType.APPLICATION_JSON,new ServletServerHttpResponse(response));
    }
}
