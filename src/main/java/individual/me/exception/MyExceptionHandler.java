package individual.me.exception;

import individual.me.pojo.Result;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class MyExceptionHandler {

    //@ExceptionHandler(Throwable.class)
    public Result handleException(Throwable t){
        return Result.fail(t.getMessage());
    }
}
