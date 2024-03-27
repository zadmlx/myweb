package individual.me.exception;

import individual.me.pojo.Result;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@Slf4j
@RestControllerAdvice
public class MyExceptionHandler {


    @ExceptionHandler(RuntimeException.class)
    public Result handlerRuntimeException(RuntimeException e) {
        log.error(e.getMessage());
        return Result.fail(e.getMessage());
    }


    @ExceptionHandler(Throwable.class)
    public Result handleException(Throwable t){
        log.error(t.getMessage());
        return Result.fail(t.getMessage());
    }
}
