package individual.me.pojo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Data
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class Article {
    private Integer id;
    private Integer author;
    private String abs;
    private String title;
    private String content;
    private String publishDate;
    private String lastModifyDate;
    private Integer category;
    // 1 deleted
    private int deleted;
}
