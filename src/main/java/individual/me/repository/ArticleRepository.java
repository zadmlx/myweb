package individual.me.repository;

import individual.me.pojo.Article;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

@Mapper
public interface ArticleRepository {

    void insertArticle(@Param("article")Article article);

    void deleteArticle(@Param("id")int id);

    void updateArticle(@Param("article")Article article);

    Article getArticleById(@Param("id") int id);

    List<Article> getAllArticle(@Param("id")int id);
}
