package individual.me.config.security.authentication;

import individual.me.pojo.user.AuthUser;
import individual.me.pojo.user.User;
import individual.me.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.Collections;

@Component
public class PhoneProvider implements AuthenticationProvider {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private RedisTemplate<String,Object> redisTemplate;

    @Override
    public Authentication authenticate(Authentication authentication) throws AuthenticationException {

        PhoneAuthenticationToken rowToken = (PhoneAuthenticationToken) authentication;
        Object o = redisTemplate.opsForValue().get(rowToken.getPhoneNumber());
        if (((int) o) != rowToken.getCode()){
            throw new BadCredentialsException("验证码过期");
        }
        String phoneNumber = rowToken.getPhoneNumber();
        User user = userRepository.loadUserByPhone(phoneNumber);
        if (user == null) {
            throw new BadCredentialsException("手机号为空");
        }

        AuthUser authUser = new AuthUser(user);

        return new PhoneAuthenticationToken(Collections.singleton(new SimpleGrantedAuthority(authUser.getUser().getAuthority())),authUser,authentication);
    }

    @Override
    public boolean supports(Class<?> authentication) {
        return false;
    }
}
