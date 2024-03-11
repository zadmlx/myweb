package individual.me.controller;

import individual.me.pojo.Result;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RestController
public class TestController {

    @GetMapping("/test")
    @PreAuthorize("@ac.check('admin')")
    public Result test(){
        log.info("测试test方法，验证admin权限通过");
        return Result.ok("ok","s",200);
    }
}
