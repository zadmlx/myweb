package individual.me.controller;

import individual.me.config.security.JwtUtil;
import individual.me.pojo.user.AuthUser;
import individual.me.pojo.user.LoginUser;
import individual.me.pojo.Result;
import individual.me.pojo.user.User;
import individual.me.service.UserService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
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

    private static final Logger log = LoggerFactory.getLogger(LoginController.class);
    @Autowired
    private UserService userService;


    @Autowired
    private AuthenticationManagerBuilder builder;

    private static final BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();

    @PostMapping("/login")
    public Result login(@RequestBody LoginUser user){
        log.info("准备登录");
        UsernamePasswordAuthenticationToken token = new UsernamePasswordAuthenticationToken(user.getUsername(),user.getPassword());

        // AuthenticationManager无法直接拿到，需要使用它的builder构建之后拿到
        Authentication authentication = this.builder.getObject().authenticate(token);

        AuthUser authUser = (AuthUser) authentication.getPrincipal();
        String authority = authUser.getUser().getAuthority();

        String jwtToken = JwtUtil.createToken(authority, authUser.getUsername());

        SecurityContextHolder.getContext().setAuthentication(authentication);
        return Result.ok("登录成功",jwtToken,200);
    }

    @PostMapping("/register")
    public Result register(@RequestBody User user){
        user.setPassword(encoder.encode(user.getPassword()));
        userService.insertUser(user);
        return Result.ok("注册成功");
    }
}
