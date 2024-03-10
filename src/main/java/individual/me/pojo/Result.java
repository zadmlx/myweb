package individual.me.pojo;

import lombok.Getter;
import lombok.Setter;
import org.springframework.http.HttpStatus;

@Getter
@Setter
public class Result {
    private Integer statusCode;
    private String message;
    private Object data;

    private Result(){}

    public static Result ok(String message){
        return Result.ok(message,null,200);
    }
    public static Result ok(String message,Object object,Integer statusCode){
        Result result = new Result();
        result.setStatusCode(statusCode);
        result.setData(object);
        result.setMessage(message);
        return result;
    }

    public static Result ok(Object object){
        return Result.ok("success",object,200);
    }
    public static Result fail(String message){
        return fail(HttpStatus.BAD_REQUEST,message);
    }

    public static Result fail(HttpStatus httpStatus, String message){
        Result result = new Result();
        result.setMessage(message);
        result.setStatusCode(httpStatus.value());
        return result;
    }
}
