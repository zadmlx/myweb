package individual.me.controller;

import individual.me.pojo.R;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class TestController {

    @GetMapping("/test")
    public R test(){
        return R.ok(null,"hello");
    }
}
