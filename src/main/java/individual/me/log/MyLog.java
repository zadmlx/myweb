package individual.me.log;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class MyLog {
    private Long id;
    private String method;
    private Long timeCost;
    private String operator;
    private LocalDate createTime;
    private String note;

}
