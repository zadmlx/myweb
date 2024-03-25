package individual.me.config.security;

import individual.me.config.aspect.Any;
import individual.me.config.security.authentication.PhoneProvider;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.security.web.DefaultSecurityFilterChain;
import org.springframework.security.web.access.AccessDeniedHandler;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.mvc.method.RequestMappingInfo;
import org.springframework.web.servlet.mvc.method.annotation.RequestMappingHandlerMapping;

import java.util.HashSet;
import java.util.Map;
import java.util.Set;


@Slf4j
@Configuration
@EnableMethodSecurity
public class SecurityConfig implements ApplicationContextAware {

    @Autowired
    private UserDetailsService userDetailsService;

    @Autowired
    private AccessDeniedHandler accessDeniedHandler;

    @Autowired
    private AuthenticationEntryPoint entryPoint;

    @Autowired
    private JwtFilter loginFilter;

    @Autowired
    private PhoneProvider phoneProvider;

    private Set<String> anySet;

    @Bean
    public DefaultSecurityFilterChain defaultSecurityFilterChain(HttpSecurity security) throws Exception {
        security.authorizeHttpRequests(auth->auth.requestMatchers(anySet.toArray(new String[0])).permitAll().requestMatchers("/**").authenticated())
                .userDetailsService(userDetailsService)
                .authenticationProvider(phoneProvider)
                .exceptionHandling(e-> e.accessDeniedHandler(accessDeniedHandler).authenticationEntryPoint(entryPoint))
                .csrf(AbstractHttpConfigurer::disable)
                .cors(cors-> cors.configurationSource(cors()))
                .addFilterBefore(loginFilter, UsernamePasswordAuthenticationFilter.class);
        return security.build();
    }


    @Bean
    public PasswordEncoder encoder(){
        return new BCryptPasswordEncoder();
    }

    @Bean
    public CorsConfigurationSource cors(){
        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        CorsConfiguration config = new CorsConfiguration();
        config.setAllowCredentials(true);
        config.addAllowedOriginPattern("*");
        config.addAllowedHeader("*");
        config.addAllowedMethod("*");
        source.registerCorsConfiguration("/**", config);
        return source;
    }


    @Override
    public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
        anySet = new HashSet<>();
        RequestMappingHandlerMapping mapping = applicationContext.getBean(RequestMappingHandlerMapping.class);
        Map<RequestMappingInfo, HandlerMethod> handlerMethods = mapping.getHandlerMethods();
        handlerMethods.forEach((info,method)->{
            if (method.hasMethodAnnotation(Any.class)){
                if (info.getPathPatternsCondition() != null) {
                    // 注意，使用RESTful风格的时候，getDirectPaths的路径会有问题
                    //anySet.addAll(info.getPathPatternsCondition().getDirectPaths());
                    info.getPathPatternsCondition().getPatterns().forEach(pattern->anySet.add(pattern.getPatternString()));
                }
            }
        });

        anySet.forEach((value)-> log.info("allowed path :{}",value));
    }
}
