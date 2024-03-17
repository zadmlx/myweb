package individual.me.pojo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ArticleVo {
    private Integer id;
    private String abs;
    private String title;
    private String publishDate;
}
