package individual.me.service;

import individual.me.pojo.Article;
import individual.me.pojo.ArticleVo;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface ArticleService {

    void insertArticle(Article article);

    void deleteArticle(int id);

    void updateArticle(Article article);

    Article getArticleById(int id);
    List<ArticleVo> getAllArticleVo(@Param("id")int id);
}
