package individual.me.pojo;
import jakarta.annotation.Nullable;
import lombok.Data;

@Data
public class R {

    private String message;
    private Object data;
    private int code;

    private R(){}

    public static R ok(@Nullable String message, Object data){
        R r = new R();
        r.code = 200;
        r.message = message;
        r.data = data;
        return r;
    }

    public static R fail(String message, @Nullable Integer code){
        R r = new R();
        r.code = (code == null) ? 200: code;
        r.message = message;
        return r;
    }
}
