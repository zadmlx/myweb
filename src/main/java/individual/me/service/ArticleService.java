package individual.me.service;

import individual.me.pojo.Article;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface ArticleService {

    void insertArticle(Article article);

    void deleteArticle(int id);

    void updateArticle(Article article);

    Article getArticleById(int id);
    List<Article> getAllArticleVo(@Param("id")int id);
}
