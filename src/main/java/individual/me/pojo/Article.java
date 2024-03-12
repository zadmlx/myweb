package individual.me.pojo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class Article {
    private Integer id;
    private Integer author;
    private String cover;
    private String title;
    private String content;
    private String publishDate;
    private String lastModifyDate;
    private String status;
    private Integer category;

    // 1 deleted
    private int deleted;
}
