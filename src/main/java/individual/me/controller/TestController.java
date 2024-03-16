package individual.me.controller;

import individual.me.config.aspect.Any;
import individual.me.pojo.Result;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class TestController {

    @Any
    @GetMapping("/test")
    public Result test(){
        System.out.println("测试preAuthorize");
        return Result.ok("ok","s",200);
    }
}
