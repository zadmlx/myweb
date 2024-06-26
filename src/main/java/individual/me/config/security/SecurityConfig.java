package individual.me.config.security;

import individual.me.config.security.aspect.Any;
import individual.me.config.security.authentication.AuthenticationFailureHandlerImpl;
import individual.me.config.security.authentication.AuthenticationSuccessHandlerImpl;
import individual.me.config.security.jwt.JwtFilter;
import individual.me.config.security.authentication.phone.PhoneLoginConfigurer;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.DefaultSecurityFilterChain;
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
    private Set<String> anySet;

    @Bean
    public DefaultSecurityFilterChain defaultSecurityFilterChain(HttpSecurity security) throws Exception {
        MappingJackson2HttpMessageConverter messageConverter = security.getSharedObject(ApplicationContext.class).getBean(MappingJackson2HttpMessageConverter.class);
        security.authorizeHttpRequests(auth->auth.requestMatchers(anySet.toArray(new String[0])).permitAll().requestMatchers("/**").authenticated())
                .userDetailsService(userDetailsService)
                .exceptionHandling(e-> e.accessDeniedHandler(new AccessDeniedHandlerImpl(messageConverter)).authenticationEntryPoint(new AuthenticationEntryPointImpl(messageConverter)))
                .csrf(AbstractHttpConfigurer::disable)
                .formLogin(form-> form.loginPage("/login").successHandler(new AuthenticationSuccessHandlerImpl(messageConverter)).failureHandler(new AuthenticationFailureHandlerImpl(messageConverter)))
                .cors(cors-> cors.configurationSource(cors()))
                .sessionManagement(session->session.sessionCreationPolicy(SessionCreationPolicy.NEVER))
                .addFilterBefore(new JwtFilter(), UsernamePasswordAuthenticationFilter.class)
                .with(new PhoneLoginConfigurer(), Customizer.withDefaults());
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
