package individual.me.controller;

import individual.me.config.security.aspect.Any;
import individual.me.log.Log;
import individual.me.pojo.Result;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class TestController {

    @Log(note = "测试日志")
    @Any
    @GetMapping("/test")
    public Result test(){

        return Result.ok("测试完成");
    }
}
