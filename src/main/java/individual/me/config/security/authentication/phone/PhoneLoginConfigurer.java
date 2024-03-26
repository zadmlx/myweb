package individual.me.config.security.authentication.phone;

import individual.me.config.security.jwt.JwtFilter;
import individual.me.config.security.authentication.AuthenticationFailureHandlerImpl;
import individual.me.config.security.authentication.AuthenticationSuccessHandlerImpl;
import individual.me.repository.UserRepository;
import org.springframework.context.ApplicationContext;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;
import org.springframework.security.web.util.matcher.RequestMatcher;


public class PhoneLoginConfigurer extends AbstractHttpConfigurer<PhoneLoginConfigurer,HttpSecurity> {

    @Override
    public void configure(HttpSecurity builder) {

        // 从HttpSecurity中获取到构建手机号认证管理所需的bean，构建好了的filter会调用authenticationManager进行认证，
        // 最后在provider中完成认证并且返回完整的token
        UserRepository userRepository = builder.getSharedObject(ApplicationContext.class).getBean(UserRepository.class);
        StringRedisTemplate redisTemplate = builder.getSharedObject(ApplicationContext.class).getBean(StringRedisTemplate.class);
        builder.authenticationProvider(new PhoneProvider(userRepository,redisTemplate));
        AuthenticationManager authenticationManager = builder.getSharedObject(AuthenticationManager.class);

        // http请求传递到PhoneLoginFilter的时候，其父类AbstractAuthenticationProcessingFilter会检查请求路径是否与定义的登录拦截路径匹配
        RequestMatcher matcher = new AntPathRequestMatcher("/login/phone","POST",true);
        PhoneLoginFilter phoneLoginFilter = getFilter(builder, matcher, authenticationManager);
        builder.addFilterBefore(phoneLoginFilter,JwtFilter.class);
    }

    private static PhoneLoginFilter getFilter(HttpSecurity builder, RequestMatcher matcher, AuthenticationManager authenticationManager) {
        MappingJackson2HttpMessageConverter messageConverter = builder.getSharedObject(ApplicationContext.class).getBean(MappingJackson2HttpMessageConverter.class);
        PhoneLoginFilter phoneLoginFilter = new PhoneLoginFilter(matcher, authenticationManager,messageConverter);
        phoneLoginFilter.setAuthenticationSuccessHandler(new AuthenticationSuccessHandlerImpl(messageConverter));
        phoneLoginFilter.setAuthenticationFailureHandler(new AuthenticationFailureHandlerImpl(messageConverter));
        return phoneLoginFilter;
    }
}
