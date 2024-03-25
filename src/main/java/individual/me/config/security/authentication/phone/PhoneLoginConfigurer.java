package individual.me.config.security.authentication.phone;

import com.fasterxml.jackson.databind.ObjectMapper;
import individual.me.config.security.JwtFilter;
import individual.me.config.security.JwtUtil;
import individual.me.pojo.Result;
import individual.me.pojo.user.AuthUser;
import individual.me.repository.UserRepository;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.context.ApplicationContext;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.converter.GenericHttpMessageConverter;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.http.server.ServletServerHttpResponse;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.AuthenticationFailureHandler;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;
import org.springframework.security.web.util.matcher.RequestMatcher;

import java.io.IOException;


public class PhoneLoginConfigurer extends AbstractHttpConfigurer<PhoneLoginConfigurer,HttpSecurity> {

    @Override
    public void configure(HttpSecurity builder) {

        UserRepository userRepository = builder.getSharedObject(ApplicationContext.class).getBean(UserRepository.class);
        StringRedisTemplate redisTemplate = builder.getSharedObject(ApplicationContext.class).getBean(StringRedisTemplate.class);
        builder.authenticationProvider(new PhoneProvider(userRepository,redisTemplate));
        AuthenticationManager authenticationManager = builder.getSharedObject(AuthenticationManager.class);

        RequestMatcher matcher = new AntPathRequestMatcher("/login/phone","POST",true);
        PhoneLoginFilter phoneLoginFilter = getFilter(builder, matcher, authenticationManager);
        builder.addFilterBefore(phoneLoginFilter,JwtFilter.class);
    }

    private static PhoneLoginFilter getFilter(HttpSecurity builder, RequestMatcher matcher, AuthenticationManager authenticationManager) {
        PhoneLoginFilter phoneLoginFilter = new PhoneLoginFilter(matcher, authenticationManager);
        phoneLoginFilter.setAuthenticationSuccessHandler((request, response, authentication) -> {
            AuthUser authUser = (AuthUser) authentication.getPrincipal();
            String token = JwtUtil.createToken(authUser.getUser().getAuthority(), authUser.getUsername(), authUser.getUser().getId());
            SecurityContextHolder.getContext().setAuthentication(authentication);
            Result result = Result.ok("登录成功", token, 200);
            MappingJackson2HttpMessageConverter messageConverter = builder.getSharedObject(ApplicationContext.class).getBean(MappingJackson2HttpMessageConverter.class);
            messageConverter.write(result, MediaType.APPLICATION_JSON,new ServletServerHttpResponse(response));
        });

        phoneLoginFilter.setAuthenticationFailureHandler((request, response, exception) -> {
            Result result = Result.fail(exception.getMessage());
            MappingJackson2HttpMessageConverter messageConverter = builder.getSharedObject(ApplicationContext.class).getBean(MappingJackson2HttpMessageConverter.class);
            messageConverter.write(result, MediaType.APPLICATION_JSON,new ServletServerHttpResponse(response));
        });
        return phoneLoginFilter;
    }
}
