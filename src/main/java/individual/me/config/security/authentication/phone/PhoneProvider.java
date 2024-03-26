package individual.me.config.security.authentication.phone;

import individual.me.pojo.user.AuthUser;
import individual.me.pojo.user.User;
import individual.me.repository.UserRepository;
import lombok.AllArgsConstructor;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.authority.SimpleGrantedAuthority;

import java.util.Collections;


@AllArgsConstructor
public class PhoneProvider implements AuthenticationProvider {

    private UserRepository userRepository;

    private StringRedisTemplate redisTemplate;

    @Override
    public Authentication authenticate(Authentication authentication) throws AuthenticationException {

        PhoneAuthenticationToken rowToken = (PhoneAuthenticationToken) authentication;
        /*Object o = redisTemplate.opsForValue().get(rowToken.getPhoneNumber());
        if (o != rowToken.getCode()){
            throw new BadCredentialsException("验证码过期");
        }*/
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
        return PhoneAuthenticationToken.class.isAssignableFrom(authentication);
    }
}
