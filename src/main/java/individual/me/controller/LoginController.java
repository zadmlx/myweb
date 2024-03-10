package individual.me.controller;

import individual.me.pojo.LoginUser;
import individual.me.pojo.Result;
import individual.me.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class LoginController {

    @Autowired
    private UserRepository userRepository;


    @Autowired
    private AuthenticationManagerBuilder builder;

    private static final BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();


    @PostMapping("/login")
    public Result login(@RequestBody LoginUser user){
        System.out.println("登录");
        UsernamePasswordAuthenticationToken token = new UsernamePasswordAuthenticationToken(user.getUsername(),user.getPassword());
        Authentication authentication = this.builder.getObject().authenticate(token);
        SecurityContextHolder.getContext().setAuthentication(authentication);
        return Result.ok("注册成功");
    }

    @PostMapping("/register")
    public Result register(@RequestBody LoginUser user){
        System.out.println("注册");
        String encryptedPass = encoder.encode(user.getPassword());
        System.out.println(encryptedPass);
        user.setPassword(encryptedPass);
        userRepository.insertUser(user);
        return Result.ok("注册成功");

    }
}
